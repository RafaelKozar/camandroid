package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import camera.plataformapiarobo.camerarobo.Adapter.MensagemAdapter;
import camera.plataformapiarobo.camerarobo.Models.Mensagem;
import camera.plataformapiarobo.camerarobo.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Lince on 30/11/2015.
 */
public class ActivityMensagens extends Activity {

    public static List<Mensagem> mensagens = new ArrayList<Mensagem>();
    private MensagemAdapter adapter;
    //private String conection = "http://104.131.163.197:3000/";
    private String conection = "http://192.168.1.106:3000/";
    private EditText myMsg;
    private String msgText;
    private ListView listView;
    private ImageButton bttEnviar;


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(conection);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(MainActivity.idRobo + "mensagem", onMenssagem);

        myMsg = (EditText) findViewById(R.id.campo_mensagem);
        bttEnviar = (ImageButton) findViewById(R.id.btt_enviar);
        listView = (ListView) findViewById(R.id.list_msgs);

        bttEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgText = myMsg.getText().toString();
                if (!msgText.isEmpty()) {
                    Mensagem msg = new Mensagem();
                    msg.setIsSendPaciente(true);
                    msg.setMsg(msgText);
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("mensagem", msgText);
                    param.put("idPaciente", MainActivity.idPaciente);
                    mensagens.add(msg);
                    mSocket.emit("msgPaciente", param);
                    setListView();
                    //adapter.notifyDataSetChanged();
                }
            }
        });
        if(mensagens.size() > 0) setListView();
    }

    private void setListView(){
        adapter = new MensagemAdapter(getApplicationContext(), mensagens);
        listView.setAdapter(adapter);
    }

    private Emitter.Listener onMenssagem = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            try {
                Mensagem msg = new Mensagem();
                msg.setMsg(data.getString("mensagem"));
                msg.setIsSendPaciente(false);
                mensagens.add(msg);
            } catch (JSONException e) {
                e.printStackTrace();
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
                            "Erro no recebimento dos sockets", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
