package com.sagr.simple.chat.message.common;

import com.sagr.common.IResult;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public interface IMessageService<T extends IMessage> {
    /**
     * Simple save message method
     *
     * @param message not empty
     * @param author not empty
     * @return
     */
    IResult<Boolean> saveMessage(String message, String author);

    /**
     * Return messages after date
     *
     * @param limit message number
     * @param fromDate
     * @return
     */
    IResult<List<T>> getLastMessages(int limit, Date fromDate);

    /**
     * Return messages after this
     *
     * @param messageId
     * @param limit message number
     * @return
     */
    IResult<List<T>> getNextMessages(ObjectId messageId, int limit);
}
