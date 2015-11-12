package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

import javax.security.auth.callback.Callback;

import camera.plataformapiarobo.camerarobo.Models.Robo;
import camera.plataformapiarobo.camerarobo.Network.CustomRequest;
import camera.plataformapiarobo.camerarobo.Network.RequestWeb;
import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by rafael on 12/11/2015.
 */
public class ListarRoboActivity extends Activity {

    private List<Robo> robos;
    private static String url = "http://104.131.163.197:3000/listarrobos";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_lista);
    }

    public void buscaRobos(){
        CustomRequest request = new CustomRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        robos = gson
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
