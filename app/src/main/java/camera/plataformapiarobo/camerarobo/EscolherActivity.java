package camera.plataformapiarobo.camerarobo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by rafael on 11/11/2015.
 */
public class EscolherActivity extends Activity {
    private ImageButton bt_novo, bt_listar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher);

        bt_novo = (ImageButton) findViewById(R.id.btt_novo_robo);
        bt_listar = (ImageButton) findViewById(R.id.btt_listar_robo);

        bt_novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EscolherActivity.this, NovoRoboActivity.class);
                startActivity(it);
                finish();
            }
        });

        bt_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EscolherActivity.this, NovoRoboActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
