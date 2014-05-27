package com.sagr.simple.chat.message.common;

import com.sagr.common.IResult;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public interface IMessageDao<T extends IMessage> {
    IResult<Boolean> saveMessage(T message);
    IResult<List<T>> getLastMessages(int limit, Date fromDate);
    IResult<List<T>> getNextMessages(ObjectId messageId, int limit);
}