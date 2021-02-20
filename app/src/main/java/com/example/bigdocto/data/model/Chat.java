package com.example.bigdocto.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

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

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                ", seen=" + seen +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getMsg() {
        return msg;
    }
}
