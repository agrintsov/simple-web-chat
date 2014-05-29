package com.sagr.simple.chat.message.service;

import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.simple.chat.message.common.IMessage;
import com.sagr.simple.chat.message.common.IMessageDao;
import com.sagr.simple.chat.message.common.IMessageService;
import com.sagr.simple.chat.message.entity.Message;
import org.bson.types.ObjectId;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class MessageService implements IMessageService<IMessage> {

    @Resource(name = "messageDao")
    private IMessageDao<IMessage> messageDao;

    @Override
    public IResult<Boolean> saveMessage(String message, String author) {
        if (message == null || message.isEmpty()) {
            return new Result<Boolean>(ResultCode.MESSAGE_IS_EMPTY);
        }
        if (author == null || author.isEmpty()) {
            return new Result<Boolean>(ResultCode.MESSAGE_AUTHOR_IS_EMPTY);
        }
        Message m = new Message(message, author);
        return messageDao.saveMessage(m);
    }

    @Override
    public IResult<List<IMessage>> getLastMessages(int limit, Date fromDate) {
        return messageDao.getLastMessages(limit, fromDate);
    }

    @Override
    public IResult<List<IMessage>> getNextMessages(ObjectId messageId, int limit) {
        return messageDao.getNextMessages(messageId, limit);
    }
}
