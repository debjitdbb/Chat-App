package com.applozic.mobicomkit.sample.conversations;

import android.app.ActivityManager;
import android.content.Context;

import androidx.test.runner.AndroidJUnit4;

import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.UserDetail;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.conversation.MessageClientService;
import com.applozic.mobicomkit.api.conversation.MobiComConversationService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.api.conversation.service.ConversationService;
import com.applozic.mobicomkit.api.people.UserIntentService;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.sync.SyncUserDetailsResponse;
import com.applozic.mobicommons.json.GsonUtils;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.contact.Contact;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static junit.framework.Assert.assertEquals;
import static androidx.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class MessageServiceTest {

    private Context context;
    private MobiComConversationService mobiComConversationService;

    @Mock
    private MessageClientService messageClientService;

    @Mock
    private MessageDatabaseService messageDatabaseService;

    @Mock
    private AppContactService appContactService;

    @Mock
    private ChannelService channelService;

    @Mock
    private ConversationService conversationService;

    @Before
    public void setup() {
        context = getTargetContext();
        MockitoAnnotations.initMocks(this);
        mobiComConversationService = new MobiComConversationService(context);

        mobiComConversationService.setContactService(appContactService);
        mobiComConversationService.setMessageClientService(messageClientService);
        mobiComConversationService.setMessageDatabaseService(messageDatabaseService);
        mobiComConversationService.setConversationService(conversationService);
    }

    @Test
    public void getLatestMessageList_WithNoArgs() {
        try {
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> messageList = (List<Message>) GsonUtils.getObjectFromJson(MockConstants.getLatestMessageListResponse_WithNoArgs, type);
            when(messageDatabaseService.getMessages(null, null, null)).thenReturn(messageList);
            assertEquals(messageList, mobiComConversationService.getLatestMessagesGroupByPeople());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLatestMessageList_WithWrongArgs() {
        try {
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> messageList = (List<Message>) GsonUtils.getObjectFromJson(MockConstants.getLatestMessageListResponse_WithWrongArgs, type);
            when(messageDatabaseService.getMessages(0011L, "nullnull", 12311)).thenReturn(messageList);
            assertEquals(messageList, mobiComConversationService.getLatestMessagesGroupByPeople(0011L, "nullnull", 12311));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLatestMessageList_WithSearchString() {
        try {
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> messageList = (List<Message>) GsonUtils.getObjectFromJson(MockConstants.getLatestMessageListResponse_WithSearchString, type);
            when(messageDatabaseService.getMessages(null, "h", null)).thenReturn(messageList);
            assertEquals(messageList, mobiComConversationService.getLatestMessagesGroupByPeople(null, "h", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLatestMessageList_ThrowError() {

        final String demoExeptionMessage = "Demo Exception";

        class DemoException extends MockitoException {
            public DemoException(String message) {
                super(message);
            }

            @Override
            public String getMessage() {
                return demoExeptionMessage;
            }
        }

        try {
            when(messageDatabaseService.getMessages(null, null, null)).thenThrow(new DemoException("Demo Exception"));
            mobiComConversationService.getLatestMessagesGroupByPeople();
        }catch (DemoException ex) {
            assertEquals(demoExeptionMessage, ex.getMessage());
        }
    }

    @Test
    public void getMessageList_forOneToOneChat() {
        try {
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> messageList = (List<Message>) GsonUtils.getObjectFromJson(MockConstants.getMessagesForContactResponse, type);
            when(messageDatabaseService.getMessages(null, null, null, null, null)).thenReturn(messageList);
            assertEquals(messageList, mobiComConversationService.getMessages(null,  null, null, null, null, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processUserDetails() {
        try {
            List<UserDetail> userDetails = new ArrayList<UserDetail>();
            userDetails.add(new UserDetail());
            SyncUserDetailsResponse response = new SyncUserDetailsResponse();
            response.setGeneratedAt("GEneratedAtString");
            response.setStatus("success");
            response.setResponse(userDetails);

            when(messageClientService.getUserDetailsList(anyString())).thenReturn(response);
            when(appContactService.getContactById("")).thenReturn(new Contact());
            Mockito.doNothing().when(appContactService).upsert(any(Contact.class));
            mobiComConversationService.processLastSeenAtStatus();

            assertEquals(response.getGeneratedAt(), MobiComUserPreference.getInstance(context).getLastSeenAtSyncTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDeleteSync_withNullArgs_returnEmptyResponse() {
        try {
            assertEquals("", mobiComConversationService.deleteSync(null, null, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSync_withContact_returnSuccess() {
        try {
            Contact contact = new Contact();
            contact.setUserId("testUserId");

            when(messageClientService.syncDeleteConversationThreadFromServer(contact, null)).thenReturn("success");

            assertEquals("success", mobiComConversationService.deleteSync(contact, null, null));

            // Check if method is called...
            Mockito.verify(messageDatabaseService, Mockito.times(1)).deleteConversation(anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSync_withChannel_returnSuccess() {
        try {
            Channel channel = new Channel();
            channel.setKey(000);
            when(messageClientService.syncDeleteConversationThreadFromServer(null, channel)).thenReturn("success");

            assertEquals("success", mobiComConversationService.deleteSync(null, channel, null));

            Mockito.verify(messageDatabaseService, Mockito.times(1)).deleteChannelConversation(anyInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        try {
            wait(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActivityManager manager = (ActivityManager)context. getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.getClass())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testReadConversation() {
        try {
            assertFalse(isMyServiceRunning(UserIntentService.class, context));
            when(appContactService.getContactById(anyString())).thenReturn(null);
            when(messageDatabaseService.updateReadStatusForContact(anyString())).thenReturn(0);
            mobiComConversationService.read(null, null);

            assertTrue(isMyServiceRunning(UserIntentService.class, context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteMsgFromDevice_withMessage() {
        try {
            when(messageDatabaseService.getMessage(anyString())).thenReturn(new Message());

            mobiComConversationService.deleteMessageFromDevice("TestString", "000");
            Mockito.verify(messageDatabaseService, Mockito.times(1)).deleteMessage(any(Message.class), anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteMsgFromDevice_withNullMessage() {
        try {
            when(messageDatabaseService.getMessage(anyString())).thenReturn(null);
            when(messageDatabaseService.deleteMessage(any(Message.class), anyString())).thenReturn(null);
            mobiComConversationService.deleteMessageFromDevice("TestString", "000");
            Mockito.verify(messageDatabaseService, Mockito.times(0)).deleteMessage(any(Message.class), anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteMessage() {
        try {
            Message message = (Message) GsonUtils.getObjectFromJson(MockConstants.Message, Message.class);
            Mockito.when(messageDatabaseService.deleteMessage(any(Message.class), anyString())).thenReturn("checkMe");
            Mockito.when(messageClientService.deleteMessage(any(Message.class), nullable(Contact.class))).thenReturn("success");

            mobiComConversationService.deleteMessage(message, new Contact());

            Mockito.verify(messageDatabaseService, Mockito.times(1)).deleteMessage(any(Message.class), nullable(String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mockSetFilePaths(Message mockMessage, final Message message) {
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ArrayList<String> arrayList = (ArrayList<String>) args[0];
                message.setFilePaths(arrayList);
                return null;
            }
        }).when(mockMessage).setFilePaths(any(List.class));
    }

    @Test
    public void testSetFilePath_withCorrectFilePath() {
        try {
            final Message message = (Message) GsonUtils.getObjectFromJson(MockConstants.MessageWithFilePath, Message.class);
            message.setFilePaths(new ArrayList<String>());
            assertEquals(0, message.getFilePaths().size());
            Message mockMessage = Mockito.mock(Message.class);
            Mockito.when(mockMessage.getFileMetas()).thenReturn(message.getFileMetas());
            Mockito.when(mockMessage.getCreatedAtTime()).thenReturn(message.getCreatedAtTime());

            mockSetFilePaths(mockMessage, message);

            mobiComConversationService.setFilePathifExist(mockMessage);
            assertEquals(1, message.getFilePaths().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetFilePath_withWrongFilePath() {
        try {
            Message message = (Message) GsonUtils.getObjectFromJson(MockConstants.Message, Message.class);
            message.setFilePaths(new ArrayList<String>());
            assertEquals(0, message.getFilePaths().size());

            Message mockMessage = Mockito.mock(Message.class);
            Mockito.when(mockMessage.getFileMetas()).thenReturn(message.getFileMetas());
            Mockito.when(mockMessage.getCreatedAtTime()).thenReturn(message.getCreatedAtTime());

            mockSetFilePaths(mockMessage, message);

            mobiComConversationService.setFilePathifExist(mockMessage);

            assertEquals(0, message.getFilePaths().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
