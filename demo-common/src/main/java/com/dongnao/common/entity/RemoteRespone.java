package com.dongnao.common.entity;

import java.io.Serializable;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class RemoteRespone implements Serializable {
    private String requestId;
    private Object responeValue;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResponeValue() {
        return responeValue;
    }

    public void setResponeValue(Object responeValue) {
        this.responeValue = responeValue;
    }
}
