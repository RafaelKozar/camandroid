package camera.plataformapiarobo.camerarobo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by rafael on 12/11/2015.
 */
public class ListarRoboActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_lista);
    }
}
