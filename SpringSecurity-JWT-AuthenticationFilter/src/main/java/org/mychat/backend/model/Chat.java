package org.mychat.backend.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chat {
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("seen")
    private boolean seen;
    @JsonProperty("msg")
    private String msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Chat [date=" + date + ", from=" + from + ", msg=" + msg + ", seen=" + seen + ", to=" + to + "]";
    }

   
}
