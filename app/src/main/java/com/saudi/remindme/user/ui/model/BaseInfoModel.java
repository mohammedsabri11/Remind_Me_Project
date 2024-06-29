package com.saudi.remindme.user.ui.model;

import java.util.List;

public class BaseInfoModel {
    private final String title;
    private final List<InfoModel> infoList;

    public BaseInfoModel(String title, List<InfoModel> infoList) {
        this.title = title;
        this.infoList = infoList;
    }

    public List<InfoModel> getInfoList() {
        return infoList;
    }

    public String getTitle() {
        return title;
    }


}
