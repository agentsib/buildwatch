package com.crowflying.buildwatch.jenkins;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.crowflying.buildwatch.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by agentsib on 16.09.14.
 */
public class GetBuildInfoCommand extends JenkinsRetrievalCommand<Bundle> {

    private final String apiEndpoint;

    public GetBuildInfoCommand(Context context, String buildUrl) {
        super(context);
        this.apiEndpoint = String.format("%s/api/json", buildUrl);
    }

    @Override
    protected Bundle parseResponse(Response resp) throws ParserException {

        if (!resp.isFailure()) {
            try {
                JSONObject json = new JSONObject(resp.getResponseBody());

                if (json.has("fullDisplayName") && json.has("result")) {
                    Bundle result = new Bundle();
                    result.putString(context.getString(R.string.extra_build_name), json.getString("fullDisplayName"));
                    result.putString(context.getString(R.string.extra_build_status), json.getString("result"));
                    return result;
                }
            } catch (JSONException e) {
            }
        }

        return null;
    }

    @Override
    protected String getUrl() {
        return this.apiEndpoint;
    }

    @Override
    protected String getMethod() {
        return "GET";
    }
}
