package camera.plataformapiarobo.camerarobo.Network;

/**
 * Created by rafael on 14/11/2015.
 */

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class CustomRequest2 extends Request<String> {

    private Listener<String> listener;
    private Map<String, String> params;


    public CustomRequest2(int method, String url, Map<String, String> params,
                         Listener<String> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers)),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(String response) {
        // TODO Auto-generated method stub
        listener.onResponse(response.toString());
    }
}