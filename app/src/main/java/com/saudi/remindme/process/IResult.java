package com.saudi.remindme.process;

import org.json.JSONObject;

public interface IResult {
    void onServerSuccess(int requestId, JSONObject jsonObject);

    void onServerError(int requestId, String error);
}
