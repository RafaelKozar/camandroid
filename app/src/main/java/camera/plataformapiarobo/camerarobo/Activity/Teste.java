package camera.plataformapiarobo.camerarobo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import org.xwalk.core.XWalkView;

import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by Lince on 16/11/2015.
 */
public class Teste extends Activity {
    private String url = "http://104.131.163.197:3000/camera5";
    private XWalkView mXWalkView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);

        mXWalkView = (XWalkView) findViewById(R.id.pag);
        mXWalkView.load(url, null);

    }
}
