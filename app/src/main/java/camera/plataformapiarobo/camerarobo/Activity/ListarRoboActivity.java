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

import java.util.ArrayList;
import java.util.List;

import camera.plataformapiarobo.camerarobo.Adapter.RobosAdapter;
import camera.plataformapiarobo.camerarobo.Models.Robo;
import camera.plataformapiarobo.camerarobo.Network.CustomRequest;
import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by rafael on 12/11/2015.
 */
public class ListarRoboActivity extends Activity {

    private RobosAdapter adapterRobo;
    private ListView listView;
    private List<Robo> robos;

    private static String url = "http://104.131.163.197:3000/listarrobos";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escholher_robo);
        listView = (ListView) findViewById(R.id.lista_robos);

        teste();
        adapterRobo = new RobosAdapter(getApplicationContext(), robos);
        listView.setAdapter(adapterRobo);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ListarRoboActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }

    public void teste(){
        Robo robo = new Robo();
        Robo robo2 = new Robo();
        robo.setNome("Robo01");
        robo2.setNome("Robo02");
        robos = new ArrayList<Robo>();
        robos.add(robo);
        robos.add(robo2);
    }

    public void buscaRobos(){
        CustomRequest request = new CustomRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //robos = gson
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
