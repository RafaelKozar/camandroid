package camera.plataformapiarobo.camerarobo.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import camera.plataformapiarobo.camerarobo.Adapter.RobosAdapter;
import camera.plataformapiarobo.camerarobo.BancoDeDados.BD;
import camera.plataformapiarobo.camerarobo.Models.Robo;
import camera.plataformapiarobo.camerarobo.Network.CustomRequest;
import camera.plataformapiarobo.camerarobo.Network.CustomRequest2;
import camera.plataformapiarobo.camerarobo.Network.NetworkConnection;
import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by rafael on 12/11/2015.
 */
public class ListarRoboActivity extends Activity {

    private RobosAdapter adapterRobo;
    private ListView listView;
    private String idRobo;
    private List<Robo> robos;
    private BD bd;
    //192.168.25.63
    private static String url = "http://104.131.163.197:3000/listarrobosandroid",
            urlVerifica = "http://104.131.163.197:3000/verificarobo";

    //private static String url = "http://192.168.25.63:3000/listarrobosandroid",
    //        urlVerifica = "http://192.168.25.63:3000/verificarobo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escholher_robo);

        bd = new BD(this);
        idRobo = bd.buscar();
        if (idRobo != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("idrobo", idRobo);
            verificaRobo(params);
        } else try {
            buscaRobos();
            //makeJsonArryReq();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListView() {
        listView = (ListView) findViewById(R.id.lista_robos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.idRobo = robos.get(position).getId();
                Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
        adapterRobo = new RobosAdapter(getApplicationContext(), robos);
        listView.setAdapter(adapterRobo);
    }

    public void verificaCadastro(){
        bd = new BD(this);
        String result = bd.buscar();
        if(result != null){
            MainActivity.idRobo = result;
            Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
            startActivity(it);
        }
    }


    public void verificaRobo(Map param) {
        CustomRequest request = new CustomRequest(Request.Method.POST, urlVerifica, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = (JSONObject) response.get(0);
                            String resposta = object.getString("resposta");
                            if ("SIM".contentEquals(resposta)) {
                                MainActivity.idRobo = idRobo;
                                Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
                                startActivity(it);
                            } else
                                buscaRobos();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });
    }

    private void makeJsonArryReq() {
        NetworkConnection networkConnection = NetworkConnection.getInstance(getApplicationContext());
        JsonArrayRequest req = new JsonArrayRequest("http://api.androidhive.info/volley/person_array.json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("eee", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("iii", "Error: " + error.getMessage());

            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(8000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        networkConnection.addRequestQueue(req);
    }


    public void buscaRobos() throws JSONException {
        NetworkConnection networkConnection = NetworkConnection.getInstance(getApplicationContext());
        CustomRequest request = new CustomRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        robos = new ArrayList<Robo>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject roboJSON = response.getJSONObject(i);
                                Robo robo = new Robo();
                                robo.setNome(roboJSON.getString("nome"));
                                robo.setId(roboJSON.getString("_id"));
                                robos.add(robo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setListView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        networkConnection.addRequestQueue(request);
    }
}
