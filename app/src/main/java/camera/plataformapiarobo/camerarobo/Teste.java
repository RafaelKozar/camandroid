package camera.plataformapiarobo.camerarobo;

/**
 * Created by Lince on 03/11/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.emitter.Emitter;
import io.socket.client.IO;
import io.socket.client.Socket;


/**
 * Created by Lince on 28/10/2015.
 */
public class Teste extends Activity {
    //private String url = "http://192.168.2.105:3000/";
    private String url = "http://104.131.163.197:3000/";
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
        setContentView(R.layout.activity_teste);

        bt = (Button) findViewById(R.id.enviar);

        String teste = "hahaha";
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        //mSocket.emit("enviar", "teste");

        mSocket.on("comando", onComando);
        mSocket.connect();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("enviar", "hahhuahua");
            }
        });
    }

    private Emitter.Listener onComando = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.i("rere", "haha");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "error", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
