package com.sagr.common;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * Created by Sasha on 24.05.14.
 */
@JsonDeserialize(using = ResultCodeDeserializer.class)
public enum ResultCode {

    SUCCESS(0, "Operation was successful"),
    FAIL(1, "Operation was failed"),


    USER_HAS_BEEN_REGISTERED(2, "User with the same name has been registered already"),
    USER_NOT_FOUND(3, "User is not found"),
    NOT_SIGNED_IN(4, "User is not signed in"),


    LAST_ERROR(10000, "");


    private int code;
    private String description;


    private ResultCode(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ResultCode getResultByCode(int code){
        for (ResultCode resultCode : values()) {
            if(resultCode.getCode() == code){
                return resultCode;
            }
        }
        return null;
    }

}