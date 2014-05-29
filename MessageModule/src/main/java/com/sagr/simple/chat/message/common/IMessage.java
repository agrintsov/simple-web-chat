package com.sagr.simple.chat.message.common;

import org.bson.types.ObjectId;

import java.util.Date;

public interface IMessage {

    Date getDate();

    // formatted date for jsp
    String getDateStr();

    String getContent();

    String getAuthorName();

    ObjectId getId();

}
