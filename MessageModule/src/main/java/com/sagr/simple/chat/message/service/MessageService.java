package com.sagr.simple.chat.message.service;

import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.simple.chat.message.common.IMessage;
import com.sagr.simple.chat.message.common.IMessageDao;
import com.sagr.simple.chat.message.common.IMessageService;
import com.sagr.simple.chat.message.entity.Message;
import org.bson.types.ObjectId;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sasha on 26.05.14.
 */
public class MessageService implements IMessageService<IMessage> {

    @Resource(name = "messageDao")
    private IMessageDao<IMessage> messageDao;

    @Override
    public IResult<Boolean> saveMessage(String message, String author) {
        if(message.isEmpty()) {
            return new Result<Boolean>(false);
        }
        if(author.isEmpty()) {
            return new Result<Boolean>(false);
        }
        Message m = new Message(message, author);
        return messageDao.saveMessage(m);
    }

    @Override
    public IResult<List<IMessage>> getLastMessages(int limit) {
        return messageDao.getLastMessages(limit);
    }

    @Override
    public IResult<List<IMessage>> getMessagesAfterThis(ObjectId messageId, int limit) {
        return messageDao.getMessagesAfterThis(messageId, limit);
    }
}
