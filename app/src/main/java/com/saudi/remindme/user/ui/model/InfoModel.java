package com.saudi.remindme.user.ui.model;

public class InfoModel {
    private final String id;
    private final String title;

    private final String instructions;
//
    //appointmentDetails


    public InfoModel(String id, String title, String instructions) {
        this.id = id;
        this.title = title;

        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getInstructions() {
        return instructions;
    }
}
