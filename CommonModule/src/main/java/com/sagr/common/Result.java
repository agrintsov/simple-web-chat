package com.sagr.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> implements IResult<T> {

    private T object;
    private Set<String> errorList;
    private ResultCode resultCode = ResultCode.SUCCESS;
    private boolean failed = false;

    public Result(T resultObj) {
        object = resultObj;
    }

    public Result(ResultCode code) {
        resultCode = code;
        if (!ResultCode.SUCCESS.equals(code)) {
            failed = true;
            addErrorMessage(resultCode.getDescription());
        }
    }

    @Override
    public boolean hasError() {
        return failed || (errorList != null && errorList.size() > 0);
    }

    // For java script
    public boolean isFailed() {
        return failed || (errorList != null && errorList.size() > 0);
    }

    @Override
    public T getResultObject() {
        return object;
    }

    // For java script
    @Override
    public List<String> getErrorList() {
        return errorList == null ? new ArrayList<String>() : new ArrayList<String>(errorList);
    }

    @Override
    public ResultCode getResultCode() {
        return resultCode;
    }

    public void addErrorMessage(String message) {
        if (errorList == null) {
            errorList = new HashSet<String>();
        }
        errorList.add(message);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(resultCode.getDescription()).append("\n");
        if (errorList != null && !errorList.isEmpty()) {
            sb.append("Errors:").append("\n");
            for (String error : errorList) {
                sb.append(error).append("\n");
            }
        }
        return sb.toString();
    }
}
