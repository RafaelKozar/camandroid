package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

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

    private static String url = "http://104.131.163.197:3000/listarrobos",
            urlVerifica = "http://104.131.163.197:3000/verificarobo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escholher_robo);
        bd = new BD(this);
        idRobo = bd.buscar();
        if(idRobo != null){
            Map<String, String> params = new HashMap<String, String>();
            params.put("idrobo", idRobo);
            verificaRobo(params);
        }
        else try {
            buscaRobos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListView(){
        listView = (ListView) findViewById(R.id.lista_robos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
        adapterRobo = new RobosAdapter(getApplicationContext(), robos);
        listView.setAdapter(adapterRobo);
    }

    public void verificaRobo(Map param){
        CustomRequest request = new CustomRequest(Request.Method.POST, urlVerifica, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = (JSONObject) response.get(0);
                            String resposta = object.getString("resposta");
                            if("SIM".contentEquals(resposta)) {
                                MainActivity.idRobo = idRobo;
                                Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
                                startActivity(it);
                            }
                            else
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


    public void buscaRobos() throws JSONException{
        CustomRequest request = new CustomRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        robos = new ArrayList<Robo>();
                        for(int i = 0; i < response.length(); i++){
                            JSONObject roboJSON = null;
                            Robo robo = new Robo();
                            try {
                                roboJSON = (JSONObject) response.get(i);
                                robo.setNome(roboJSON.getString("nome"));
                                robo.setId(roboJSON.getString("id"));
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

            }
        });

    }
}
