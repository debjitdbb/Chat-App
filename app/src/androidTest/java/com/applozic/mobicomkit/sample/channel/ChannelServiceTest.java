package com.applozic.mobicomkit.sample.channel;

import android.content.Context;
import androidx.test.runner.AndroidJUnit4;

import com.applozic.mobicomkit.api.account.user.UserService;
import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.database.ChannelDatabaseService;
import com.applozic.mobicomkit.channel.service.ChannelClientService;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.feed.AlResponse;
import com.applozic.mobicomkit.feed.ApiResponse;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicommons.json.GsonUtils;
import com.applozic.mobicommons.people.channel.Channel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ChannelServiceTest {

    private Context context;
    private ChannelService channelService;

    @Mock
    private ChannelClientService channelClientService;
    @Mock
    private ChannelDatabaseService channelDatabaseService;
    @Mock
    private UserService userService;
    @Mock
    private AppContactService appContactService;

    public ChannelServiceTest() {
    }


    @Before
    public void setUp() {
        context = getTargetContext();
        MockitoAnnotations.initMocks(this);
        channelService = ChannelService.getInstance(context);

        channelService.setChannelClientService(channelClientService);
        channelService.setChannelDatabaseService(channelDatabaseService);
        channelService.setUserService(userService);
        channelService.setContactService(appContactService);
    }

    @Test
    public void createChannel_withNullInfo() {
        assertTrue(channelService.createChannel(null) == null);
    }

    @Test
    public void createChannel_withSuccess() {
        ChannelInfo channelInfo = new ChannelInfo();

        try {
            ChannelFeedApiResponse apiResponse = (ChannelFeedApiResponse) GsonUtils
                    .getObjectFromJson(MockConstants
                            .channelSuccessResponse, ChannelFeedApiResponse.class);

            when(channelClientService.createChannelWithResponse(channelInfo)).thenReturn(apiResponse);

            AlResponse response = channelService.createChannel(channelInfo);

            assertTrue(response.isSuccess() && (response.getResponse() instanceof Channel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createChannel_withError() {
        ChannelInfo channelInfo = new ChannelInfo();

        try {
            ChannelFeedApiResponse apiResponse = (ChannelFeedApiResponse) GsonUtils
                    .getObjectFromJson(MockConstants
                            .channelErrorResponse, ChannelFeedApiResponse.class);

            when(channelClientService.createChannelWithResponse(channelInfo)).thenReturn(apiResponse);

            AlResponse response = channelService.createChannel(channelInfo);

            assertFalse(response.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeMemberFromChannel_withEmptyArgs() {
        assertEquals(null, channelService.removeMemberFromChannelProcess(0, ""));
    }

    @Test
    public void removeMemberFromChannel_withNullArgs() {
        assertEquals("", channelService.removeMemberFromChannelProcess((Integer) null, null));
    }

    @Test
    public void removeMemberFromChannel_withSuccess() {
        ApiResponse response = (ApiResponse) GsonUtils.getObjectFromJson(MockConstants
                .removeMemberSuccessResponse, ApiResponse.class);
        when(channelClientService.removeMemberFromChannel(8905836, "reytum7")).thenReturn
                (response);
        String status = channelService.removeMemberFromChannelProcess(8905836, "reytum7");
        assertEquals(AlResponse.SUCCESS, status);
    }

    @Test
    public void removeMemberFromChannel_withError() {
        ApiResponse response = (ApiResponse) GsonUtils.getObjectFromJson(MockConstants
                .removeMemberErrorResponse, ApiResponse.class);
        when(channelClientService.removeMemberFromChannel(8905836, "reytum700")).thenReturn
                (response);
        assertEquals(AlResponse.ERROR, channelService.removeMemberFromChannelProcess(8905836,
                "reytum700"));
    }

    @Test
    public void addMemberToChannel_withNullArgs() {
        assertEquals("", channelService.addMemberToChannelProcess((Integer) null, null));
    }

    @Test
    public void addMemberToChannel_withEmptyArgs() {
        assertEquals(null, channelService.addMemberToChannelProcess(0, ""));
    }

    @Test
    public void addMemberToChannel_withSuccess() {
        ApiResponse response = (ApiResponse) GsonUtils.getObjectFromJson(MockConstants
                .addMemberSuccessResponse, ApiResponse.class);
        when(channelClientService.addMemberToChannel(8905836, "reytum6")).thenReturn(response);
        assertEquals(AlResponse.SUCCESS, channelService.addMemberToChannelProcess(8905836,
                "reytum6"));
    }

    @Test
    public void addMemberToChannel_withError() {
        ApiResponse response = (ApiResponse) GsonUtils.getObjectFromJson(MockConstants
                .addMemberErrorResponse, ApiResponse.class);
        when(channelClientService.addMemberToChannel(8905836, "reytum6")).thenReturn(response);
        assertEquals(AlResponse.ERROR, channelService.addMemberToChannelProcess(8905836,
                "reytum6"));
    }

    @Test
    public void createGroupOfTwo_withNullArgs() {
        assertEquals(null, channelService.createGroupOfTwoWithResponse(null));
    }
}