package org.zerock.guestbook.common;

public enum CommonConstant {

    DEFAULT_YES("Y"), DEFAULT_NO("N"),

    SEARCH_TYPE_TITLE("t"), SEARCH_TYPE_CONTENT("c"), SEARCH_TYPE_WRITER("w");

    private final String value;

    public String getValue(){
        return value;
    }

    CommonConstant(String value){
        this.value = value;
    }

}
