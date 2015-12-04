package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import camera.plataformapiarobo.camerarobo.Adapter.MensagemAdapter;
import camera.plataformapiarobo.camerarobo.Models.Mensagem;
import camera.plataformapiarobo.camerarobo.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by rafael on 03/12/2015.
 */
public class FragmentMensagens extends Fragment {

    public static List<Mensagem> mensagens = new ArrayList<Mensagem>();
    public static MensagemAdapter adapter;
    private String conection = "http://104.131.163.197:3000/";
    //private String conection = "http://192.168.1.106:3000/";
    private EditText myMsg;
    private String msgText;
    public static ListView listView;
    private ImageButton bttEnviar;
    public static View rootView;

    /*private Socket mSocket;
    {
        try {
            mSocket = IO.socket(conection);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mensagens, container, false);

        mensagens = new ArrayList<Mensagem>();

        myMsg = (EditText) rootView.findViewById(R.id.campo_mensagem);
        bttEnviar = (ImageButton) rootView.findViewById(R.id.btt_enviar);
        listView = (ListView) rootView.findViewById(R.id.list_msgs);

        bttEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgText = myMsg.getText().toString();
                if (!msgText.isEmpty()) {
                    Mensagem msg = new Mensagem();
                    msg.setIsSendPaciente(true);
                    msg.setMsg(msgText);
                    //HashMap<String, String> param = new HashMap<String, String>();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("mensagem", msgText);
                        jsonObject.put("idPaciente", MainActivity.idPaciente);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mensagens.add(msg);
                    MainActivity.mSocket.emit("msgPaciente", jsonObject);
                    setListView();
                    myMsg.setText("");
                }
            }
        });
        return rootView;
    }


    public static void setListView(){
        adapter = new MensagemAdapter(rootView.getContext(), mensagens);
        listView.setAdapter(adapter);
    }

    /*private Emitter.Listener onMenssagem = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //JSONObject data = (JSONObject) args[0];
            String data = (String) args[0];

            Mensagem msg = new Mensagem();
            msg.setMsg(data);
            msg.setIsSendPaciente(false);
            mensagens.add(msg);
            setListView();
        }
    }; */


}
