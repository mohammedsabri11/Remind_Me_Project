package com.saudi.remindme.consultant.ui.interfaces;

import com.saudi.remindme.admin.ui.model.PatientItem;

public interface OnOngoingQueryListener {
    void onChat(PatientItem patient);

    void onProfile(PatientItem patient);
}
