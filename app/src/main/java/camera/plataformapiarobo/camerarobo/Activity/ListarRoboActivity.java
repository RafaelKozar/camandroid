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
import android.widget.Button;
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
    private int pacienteTapped;
    private BD bd;
    //192.168.25.63
    private static String url = "http://104.131.163.197:3000/listarpacientesandroid",
            urlVerifica = "http://104.131.163.197:3000/verificarobo",
           urlUpdate = "http://104.131.163.197:3000/mudastat" +
                   "uspaciente";

    private Button bttAtualizar, bttMensagem;

    //private static String url = "http://192.168.1.106:3000/listarpacientesandroid",
    //        urlVerifica = "http://192.168.1.106:3000/verificarobo",
    //        urlUpdate = "http://192.168.1.106:3000/mudastatuspaciente";


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

        bttAtualizar = (Button) findViewById(R.id.btt_atualizar);
        bttMensagem = (Button) findViewById(R.id.btt_mensagem);

        /*bttAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        bttMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarRoboActivity.this, ActivityMensagens.class);
                startActivity(intent);
            }
        }); */
    }

    public void setListView() {
        listView = (ListView) findViewById(R.id.lista_robos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.idRobo = robos.get(position).getIdRobo();
                pacienteTapped = position;
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
            HashMap<String, String> param = new HashMap<>();
            param.put("idRobo", idRobo);
            verificaRobo(param);
            //Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
            //startActivity(it);
        }
    }


    public void verificaRobo(Map param) {
        NetworkConnection networkConnection = NetworkConnection.getInstance(getApplicationContext());

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
                                atualizaPaciente();
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

        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        networkConnection.addRequestQueue(request);
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
                                if(roboJSON.getString("robo").isEmpty() || "true".contentEquals(roboJSON.getString("isTablet"))) continue;
                                Robo robo = new Robo();

                                robo.setNomeRobo(roboJSON.getString("robo"));
                                robo.setIdRobo(roboJSON.getString("idRobo"));

                                robo.setNomePaciente(roboJSON.getString("nome"));
                                robo.setIdPaciente(roboJSON.getString("_id"));

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



    public void atualizaPaciente(){
        NetworkConnection networkConnection = NetworkConnection.getInstance(getApplicationContext());
        HashMap<String, String> param = new HashMap<>();
        param.put("idPaciente", robos.get(pacienteTapped).getIdPaciente());
        CustomRequest request = new CustomRequest(Request.Method.POST, urlUpdate, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = (JSONObject) response.get(0);
                            String resposta = object.getString("resposta");
                            Log.i("resposta", resposta);

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

        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        networkConnection.addRequestQueue(request);
    }

}
