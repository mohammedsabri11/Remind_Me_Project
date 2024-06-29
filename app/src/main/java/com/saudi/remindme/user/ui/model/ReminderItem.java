package com.saudi.remindme.user.ui.model;

public class ReminderItem {
    private final String id;
    private final String dateTime;
    private final String reminderDetails;

    public ReminderItem(String id, String dateTime, String reminderDetails) {
        this.id = id;
        this.dateTime = dateTime;
        this.reminderDetails = reminderDetails;
    }

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getReminderDetails() {
        return reminderDetails;
    }

    @Override
    public String toString() {
        return dateTime;
    }
}
