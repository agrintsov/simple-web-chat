package com.sagr.simple.chat.message.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.sagr.common.DateFormatter;
import com.sagr.simple.chat.message.common.IMessage;
import org.bson.types.ObjectId;

import java.util.Date;

@Entity(value = "messages", noClassnameStored = true)
public class Message implements IMessage {

    @Id
    private ObjectId id;
    private Date date;
    private String content;
    private String authorName;

    public Message() {
    }

    public Message(String content, String authorName) {
        this.content = content;
        this.authorName = authorName;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public String getDateStr() {
        return DateFormatter.getDateWithTimeFormat(date);
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public ObjectId getId() {
        return id;
    }
}
