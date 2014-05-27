package com.sagr.common;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(using = ResultCodeDeserializer.class)
public enum ResultCode {

    SUCCESS(0, "Operation was successful"),
    FAIL(1, "Operation was failed"),


    USER_HAS_BEEN_REGISTERED(2, "User with the same name has been registered already"),
    USER_NOT_FOUND(3, "User is not found"),
    NOT_SIGNED_IN(4, "User is not signed in"),
    USER_NAME_IS_EMPTY(4, "User's name is empty"),

    MESSAGE_SIZE_LIMIT(101, "Message have exceeded size limit"),
    MESSAGE_IS_EMPTY(102, "Message is empty"),
    MESSAGE_AUTHOR_IS_EMPTY(103, "Message's author is empty"),

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

}