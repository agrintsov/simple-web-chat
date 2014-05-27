package com.sagr.simple.chat.message.common;

import org.bson.types.ObjectId;

import java.util.Date;

public interface IMessage {

    Date getDate();

    String getDateStr();

    void setDate(Date date);

    String getContent();

    void setContent(String content);

    String getAuthorName();

    void setAuthorName(String authorName);

    ObjectId getId();

}
