package com.sagr.common;

import java.util.List;

public interface IResult<T> {

    boolean hasError();

    T getResultObject();

    // For java script
    List<String> getErrorList();

    ResultCode getResultCode();

    void addErrorMessage(String message);

}