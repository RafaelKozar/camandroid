package camera.plataformapiarobo.camerarobo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * Created by Lince on 28/10/2015.
 */
public class MainActivity extends Activity {
    private String url = "http://192.168.25.63:3000";


    //0 parar, 1 frente, 2 direita, 3 esquerda, 4 para atraz
    //private String url = "http://104.131.163.197:3000/";
    private int comando = 4, antComando = 4;
    private String idRobo;
    private TextView tComando;
    private Button bt;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tComando = (TextView) findViewById(R.id.text_comando);
        String teste = "hahaha";
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.on("comando", onComando);
        mSocket.connect();
        mSocket.emit("enviar", "envioar");

    }

    private Emitter.Listener onComando = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            try {
                comando = data.getInt("comando");
                idRobo = data.getString("idRobo");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(comando != antComando){
                antComando = comando;
            }
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
