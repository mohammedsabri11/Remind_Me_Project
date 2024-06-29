package com.saudi.remindme.process;

import static com.saudi.remindme.process.Constant.PROCESS_MASSEGE;
import static com.saudi.remindme.process.Constant.PROCESS_STATUS;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saudi.remindme.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Server {
    private static Server instance;
    private final Context mContext;
    private RequestQueue requestQueue;

    private Server(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Server getInstance(Context context) {
        if (instance == null) {
            instance = new Server(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void post(Map<String, String> params, String url, int requestId, IResult resultCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> handleSuccessResponse(requestId, response, resultCallback),
                error -> handleErrorResponse(requestId, error, resultCallback)
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        addToRequestQueue(stringRequest);
    }

    public void get(Map<String, String> params, String baseUrl, int requestId, IResult resultCallback) {
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();

        // Append parameters to the URL query string
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        String urlWithParams = builder.build().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlWithParams,
                response -> handleSuccessResponse(requestId, response, resultCallback),
                error -> handleErrorResponse(requestId, error, resultCallback)
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        addToRequestQueue(stringRequest);
    }


    private void handleSuccessResponse(int requestId, String response, IResult resultCallback) {
        Log.d("Server:", "Response : " + response);
        try {
            JSONObject object = new JSONObject(response);

            if (object.getBoolean(PROCESS_STATUS)) {
                resultCallback.onServerSuccess(requestId, object);
            } else {
                resultCallback.onServerError(requestId, object.getString(PROCESS_MASSEGE));
            }
        } catch (JSONException e) {
            resultCallback.onServerError(requestId, mContext.getResources().getString(R.string.no_server_response) + e.getMessage());
            Log.d("Server", "Error in parsing response: " + e.getMessage());

        }
    }

    private void handleErrorResponse(int requestId, VolleyError error, IResult resultCallback) {
        Log.d("Server:", "Error Response : " + error);
        String errorMessage;
        if (error instanceof NetworkError || error instanceof NoConnectionError || error instanceof TimeoutError) {
            errorMessage = "Network error occurred. Please check your connection.";
        } else if (error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError) {
            errorMessage = "Server error occurred. Please try again later.";
        } else {
            errorMessage = "An error occurred: " + error.getMessage();
        }
        resultCallback.onServerError(requestId, mContext.getResources().getString(R.string.no_server_response) + errorMessage);
        Log.d("Server", "Error in network request: " + error.getMessage());
    }
}