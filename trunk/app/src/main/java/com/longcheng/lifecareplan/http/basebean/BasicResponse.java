package com.longcheng.lifecareplan.http.basebean;

/**
 *
 */
public class BasicResponse<T> {

//    private int code;
//    private String message;
//    private T results;
//    private boolean error;
//
//    public T getResults() {
//        return results;
//    }
//
//    public void setResults(T results) {
//        this.results = results;
//    }
//
//    public boolean isError() {
//        return error;
//    }
//
//    public void setError(boolean error) {
//        this.error = error;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    private int code;
    private T data;

    public T getResults() {
        return data;
    }

    public void setResults(T data) {
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}