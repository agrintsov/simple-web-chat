package com.sagr.simple.chat.message.common;

import com.sagr.common.IResult;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Sasha on 26.05.14.
 */
public interface IMessageDao<T extends IMessage> {
    IResult<Boolean> saveMessage(T message);
    IResult<List<T>> getLastMessages(int limit);
    IResult<List<T>> getMessagesAfterThis(ObjectId messageId, int limit);
    void removeMessage(ObjectId id);
}