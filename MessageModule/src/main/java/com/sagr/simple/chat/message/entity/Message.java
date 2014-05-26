package com.sagr.simple.chat.message.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.sagr.common.DateFormatter;
import com.sagr.simple.chat.message.common.IMessage;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * Created by Sasha on 26.05.14.
 */
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public ObjectId getId() {
        return id;
    }
}
