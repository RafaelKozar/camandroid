package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.net.URISyntaxException;
import java.util.List;

import camera.plataformapiarobo.camerarobo.R;
import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Lince on 30/11/2015.
 */
public class ActivityMensagens extends Activity {

    private List<String> mensagens;
    private String conection = "http://104.131.163.197:3000/";
    private EditText myMsg;
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

    }
}
