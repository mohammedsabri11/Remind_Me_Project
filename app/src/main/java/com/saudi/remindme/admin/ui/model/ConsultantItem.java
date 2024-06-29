package com.saudi.remindme.admin.ui.model;

public class ConsultantItem extends User {

    private String certificateImageUrl;
    private String shortDescription;
    private String speciality;
    private String experience;
    private String evaluation;

    public ConsultantItem(String id, String name, String gender, String profile, String certificateImageUrl, boolean isActive) {
        super(id, name, gender, profile, isActive);
        this.certificateImageUrl = certificateImageUrl;
    }

    public ConsultantItem(String id, String name, String shortDescription, String gender, String speciality, String experience, String evaluation, String profile) {
        super(id, name, gender, profile);
        this.shortDescription = shortDescription;
        this.speciality = speciality;
        this.experience = experience;
        this.evaluation = evaluation;
    }

    //(userId, name, shortDescription, gender, speciality, experience, evaluationCount, profileUrl);
    public String getCertificateImageUrl() {
        return certificateImageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getExperience() {
        return experience;
    }

    public String getEvaluation() {
        return evaluation;
    }
}
