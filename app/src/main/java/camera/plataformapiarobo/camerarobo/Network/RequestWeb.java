package camera.plataformapiarobo.camerarobo.Network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import javax.security.auth.callback.Callback;

/**
 * Created by Lince on 12/11/2015.
 */
public class RequestWeb {
    private static String url = "http://104.131.163.197:3000/";

    public static void RequestPost(String metodo, Context ctx, final Callback callback ) {
        NetworkConnection networkConnection = NetworkConnection.getInstance(ctx);

        CustomRequest request = new CustomRequest(com.android.volley.Request.Method.POST, url + "/" + metodo,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //callback(response);
                callback(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
    }

}
