package com.saudi.remindme.admin.ui.model;

import androidx.annotation.NonNull;

public class PatientItem extends User {


    private String healthStatusDetails;
    private String careProviderName;


    public PatientItem(String id, String name, String gender, String healthStatusDetails, String careProviderName, boolean isActive) {
        super(id, name, gender, isActive);
        this.healthStatusDetails = healthStatusDetails;
        this.careProviderName = careProviderName;

    }

    public PatientItem(String id, String name, String dob, String gender, String phone, String healthStatusDetails) {
        super(id, name, dob, gender, phone);
        this.healthStatusDetails = healthStatusDetails;


    }

    public PatientItem(String id, String name) {
        super(id, name);


    }

    public String getHealthStatusDetails() {
        return healthStatusDetails;
    }

    public String getCareProviderName() {
        return careProviderName;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

}
