package com.sagr.simple.chat.message.common;

import com.sagr.common.IResult;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public interface IMessageService<T extends IMessage> {
    /**
     * Save message to storage.
     *
     * @param message message which will be stored to storage. Can't be empty
     * @param author  author of message. Can't be empty
     */
    IResult<Boolean> saveMessage(String message, String author);

    /**
     * Return messages after some date
     *
     * @param limit    number of messages to return
     * @param fromDate date after which messages will be taken
     * @return List of message after date which is in interest
     */
    IResult<List<T>> getLastMessages(int limit, Date fromDate);

    /**
     * Return messages which were written after exact message
     *
     * @param messageId message id after which messages should be taken
     * @param limit     number of messages to return
     * @return List of message which were written after message of messageId
     */
    IResult<List<T>> getNextMessages(ObjectId messageId, int limit);
}
