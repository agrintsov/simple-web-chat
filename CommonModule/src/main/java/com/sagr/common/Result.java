package com.sagr.common;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.*;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> implements IResult<T> {
    private T object;
    private Set<String> errorList;
    private ResultCode resultCode = ResultCode.SUCCESS;
    private ObjectId resultObjectId = null;
    private boolean failed = false;

    public Result(){
        // default constructor
    }

    public Result(T resultObj){
        object = resultObj;
    }

    // failed result
    public Result(String errorMsg){
        resultCode = ResultCode.FAIL;
        failed = true;
        addErrorMessage(errorMsg);
    }

    public Result(ResultCode code) {
        resultCode = code;
        if(!ResultCode.SUCCESS.equals(code)) {
            failed = true;
            addErrorMessage(resultCode.getDescription());
        }
    }

    @Override
    public boolean hasError() {
        return failed || (errorList != null && errorList.size() > 0);
    }

    public boolean isFailed() {
        return failed || (errorList != null && errorList.size() > 0);
    }

    @Override
    public T getResultObject() {
        return object;
    }

    @Override
    public List<String> getErrorList() {
        return errorList == null ? new ArrayList<String>() : new ArrayList<String>(errorList);
    }

    @Override
    public ObjectId getResultObjectId() {
        return resultObjectId;
    }

    @Override
    public ResultCode getResultCode() {
        return resultCode;
    }

    @JsonIgnore
    public void setAsFailed() {
        if(ResultCode.SUCCESS.equals(resultCode)) {
            resultCode = ResultCode.FAIL;
        }
        failed = true;
    }

    @JsonIgnore
    public void setAsFailed(ResultCode resultCode) {
        this.resultCode = resultCode;
        failed = true;
    }

    @JsonIgnore
    public void setAsFailed(String errorMessage) {
        addErrorMessage(errorMessage);
        setAsFailed();
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Result<T> setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        failed = !ResultCode.SUCCESS.equals(resultCode);
        if(failed) {
            addErrorMessage(resultCode.getDescription());
        }
        return this;
    }

    public void setResultObjectId(ObjectId resultObjectId) {
        this.resultObjectId = resultObjectId;
    }

    public void setFailed(boolean isFailed) {
        this.failed = isFailed;
    }

    public void addErrorMessage(String message){
        if (errorList == null){
            errorList = new HashSet<String>();
        }
        errorList.add(message);
    }

    public void addErrorMessages(List<String> messages){
        if (errorList == null){
            errorList = new HashSet<String>();
        }
        if (messages != null){
            errorList.addAll(messages);
        }
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(resultCode.getDescription()).append("\n");
        if (errorList != null && !errorList.isEmpty()){
            sb.append("Errors:").append("\n");
            for (String error : errorList){
                sb.append(error).append("\n");
            }
        }
        return sb.toString();
    }
}
