package com.sagr.simple.chat.wrappers;

import com.sagr.common.DateFormatter;
import com.sagr.simple.chat.message.common.IMessage;

/**
 * Created by Sasha on 26.05.14.
 */
public class MessageWrapper {

    private IMessage message;
    private boolean myMessage;

    public MessageWrapper(IMessage message, boolean myMessage) {
        this.message = message;
        this.myMessage = myMessage;
    }

    public String getDate() {
        return DateFormatter.getDateWithTimeFormat(message.getDate());
    }

    public String getContent() {
        return message.getContent();
    }

    public String getAuthorName() {
        return message.getAuthorName();
    }

    public String getId() {
        return message.getId().toString();
    }

    public boolean isMyMessage() {
        return myMessage;
    }
}
