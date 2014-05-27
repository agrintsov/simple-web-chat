package com.sagr.common;

import org.bson.types.ObjectId;

import java.util.List;

public interface IResult<T> {

    boolean hasError();

    T getResultObject();

    List<String> getErrorList();

    void setResultObjectId(ObjectId id);

    ObjectId getResultObjectId();

    ResultCode getResultCode();

    void setObject(T object);

    void setAsFailed();

    void addErrorMessage(String message);

    void addErrorMessages(List<String> messages);
}