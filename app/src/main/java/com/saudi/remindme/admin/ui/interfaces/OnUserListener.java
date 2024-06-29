package com.saudi.remindme.admin.ui.interfaces;

public interface OnUserListener {
    void onDeActivate(String name, String userID);

    void onActivate(String name, String userID);
}
