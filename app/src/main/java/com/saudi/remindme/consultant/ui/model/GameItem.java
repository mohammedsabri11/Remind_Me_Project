package com.saudi.remindme.consultant.ui.model;

import java.io.Serializable;

public class GameItem implements Serializable {

    private final String id;
    private final String name;

    private final String description;
    private final String link;


    public GameItem(String id, String name, String description, String link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }


}
