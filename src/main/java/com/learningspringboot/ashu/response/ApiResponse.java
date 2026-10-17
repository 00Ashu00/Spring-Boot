package com.learningspringboot.ashu.response;

import java.time.LocalDateTime;

public class ApiResponse {
    private LocalDateTime timestamp;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
