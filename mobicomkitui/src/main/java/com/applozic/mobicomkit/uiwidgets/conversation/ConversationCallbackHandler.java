package com.applozic.mobicomkit.uiwidgets.conversation;

import android.content.Context;

import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.broadcast.AlEventManager;
import com.applozic.mobicomkit.listners.AlCallback;
import com.applozic.mobicomkit.listners.ApplozicUIListener;
import com.applozic.mobicommons.json.JsonMarker;

public class ConversationCallbackHandler implements ApplozicUIListener {

    private static final String Al_CONVERSATION_CALLBACK = "Al_CONVERSATION_CALLBACK";
    private Context context;
    private AlCallback callback;

    public ConversationCallbackHandler(Context context, AlCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void registerUICallback() {
        AlEventManager.getInstance().registerUIListener(Al_CONVERSATION_CALLBACK, this);
    }

    public void unregisterUICallbacks() {
        AlEventManager.getInstance().unregisterUIListener(Al_CONVERSATION_CALLBACK);
    }

    @Override
    public void onMessageSent(Message message) {

    }

    @Override
    public void onMessageReceived(Message message) {

    }

    @Override
    public void onLoadMore(boolean loadMore) {

    }

    @Override
    public void onMessageSync(Message message, String key) {

    }

    @Override
    public void onMessageDeleted(String messageKey, String userId) {

    }

    @Override
    public void onMessageDelivered(Message message, String userId) {

    }

    @Override
    public void onAllMessagesDelivered(String userId) {

    }

    @Override
    public void onAllMessagesRead(String userId) {

    }

    @Override
    public void onConversationDeleted(String userId, Integer channelKey, String response) {

    }

    @Override
    public void onUpdateTypingStatus(String userId, String isTyping) {

    }

    @Override
    public void onUpdateLastSeen(String userId) {

    }

    @Override
    public void onMqttDisconnected() {
        CallbackEvent callbackEvent = new CallbackEvent();
        callbackEvent.setAction(CallbackEvent.EVENT_MQTT_DISCONNECTED);
        callback.onError(callbackEvent);
    }

    @Override
    public void onMqttConnected() {
        CallbackEvent callbackEvent = new CallbackEvent();
        callbackEvent.setAction(CallbackEvent.EVENT_MQTT_CONNECTED);
        callback.onSuccess(callbackEvent);
    }

    @Override
    public void onUserOnline() {

    }

    @Override
    public void onUserOffline() {

    }

    @Override
    public void onChannelUpdated() {

    }

    @Override
    public void onConversationRead(String userId, boolean isGroup) {

    }

    @Override
    public void onUserDetailUpdated(String userId) {

    }

    @Override
    public void onMessageMetadataUpdated(String keyString) {

    }

    @Override
    public void onUserMute(boolean mute, String userId) {

    }

    public static class CallbackEvent extends JsonMarker {
        public static final String EVENT_MQTT_DISCONNECTED = "EVENT_MQTT_DISCONNECTED";
        public static final String EVENT_MQTT_CONNECTED = "EVENT_MQTT_CONNECTED";
        private String action;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
