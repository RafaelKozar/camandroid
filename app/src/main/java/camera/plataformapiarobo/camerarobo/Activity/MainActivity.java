package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import camera.plataformapiarobo.camerarobo.Models.Mensagem;
import camera.plataformapiarobo.camerarobo.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.xwalk.core.XWalkView;

import org.xwalk.core.internal.XWalkSettings;


/**
 * Created by Lince on 28/10/2015.
 */
public class MainActivity extends Activity {
    //private String url = "http://192.168.25.63:3000";
    //private String camera = "http://192.168.25.63:3000/camera";
    //private String camera = "http://104.131.163.197:3000/camera";

    //0 parar, 1 frente, 2 direita, 3 esquerda, 4 para atraz
    private String url = "http://104.131.163.197:3000/";
    //private String url = "http://192.168.1.106:3000/";
    private int comando = 4, antComando = 4;
    public static String  idRobo, idPaciente;
    private TextView tComando;
    private Button bt;
    private XWalkView mXWalkView;
    private Button bttAtualizar, bttMensagem;
    FragmentMensagens fragmentMensagens;

    public static Socket mSocket;
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

        String camera = "http://104.131.163.197:3000/camera/" + idRobo;
        //String camera = "http://104.131.163.197:3000/camera4";
        fragmentMensagens = new FragmentMensagens();

        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.on(idRobo, onComando);
        mSocket.on(idRobo + "mensagem", onMenssagem);
        mSocket.connect();
        mSocket.emit("enviar", "envioar");


        mXWalkView = (XWalkView) findViewById(R.id.xwalkWebView);
        mXWalkView.load(camera, null);

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

    private Emitter.Listener onMenssagem = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //JSONObject data = (JSONObject) args[0];
            String data = (String) args[0];

            final Mensagem msg = new Mensagem();
            msg.setMsg(data);
            msg.setIsSendPaciente(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentMensagens.mensagens.add(msg);
                    fragmentMensagens.setListView();
                }
            });
        }
    };


    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Erro no recebimento dos sockets", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
