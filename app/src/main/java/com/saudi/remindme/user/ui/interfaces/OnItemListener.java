package com.saudi.remindme.user.ui.interfaces;

import com.saudi.remindme.admin.ui.model.ConsultantItem;

public interface OnItemListener {
    void onChatClick(ConsultantItem consultant);

    void onEvaluationClick(ConsultantItem consultant);
}
