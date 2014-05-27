package com.sagr.simple.chat.message.dao;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.simple.chat.message.common.IMessageDao;
import com.sagr.simple.chat.message.entity.Message;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class MessageDao extends BasicDAO<Message, ObjectId> implements IMessageDao<Message> {

    protected MessageDao(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    @Override
    public IResult<Boolean> saveMessage(Message message) {
        save(message);
        return new Result<Boolean>(true);
    }

    @Override
    public IResult<List<Message>> getLastMessages(int limit, Date fromDate) {
        Query<Message> query = createQuery();
        query.order("-date").offset(0).limit(limit);
        if(fromDate != null) {
            query.field("date").greaterThanOrEq(fromDate);
        }
        List<Message> messages = Lists.reverse(query.asList());
        return new Result<List<Message>>(messages);
    }

    @Override
    public IResult<List<Message>> getNextMessages(ObjectId messageId, int limit) {
        if (!exists(createQuery().field("id").equal(messageId))) {
            return new Result<List<Message>>(ResultCode.FAIL);
        }
        Query<Message> query = createQuery();
        query.order("-id").offset(0).limit(limit);
        query.field("id").greaterThan(messageId);
        List<Message> messages = Lists.reverse(query.asList());
        return new Result<List<Message>>(messages);
    }

}
