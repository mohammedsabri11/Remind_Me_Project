package com.saudi.remindme.user.ui.model;

public class QueryItem {
    private final String id;

    private final String text;
    private final String queryDate;
    private final String reply;
    private final String replyDate;

    public QueryItem(String id, String text, String queryDate, String reply, String replyDate) {
        this.id = id;
        this.text = text;
        this.reply = reply;
        this.replyDate = replyDate;
        this.queryDate = queryDate;
    }

    public String getId() {
        return id;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public String getText() {
        return text;
    }

    public String getReply() {
        return reply;
    }

    public String getReplyDate() {
        return replyDate;
    }
}
