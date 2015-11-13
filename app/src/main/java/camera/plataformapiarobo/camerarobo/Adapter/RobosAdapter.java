package camera.plataformapiarobo.camerarobo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import camera.plataformapiarobo.camerarobo.Models.Robo;
import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by rafael on 12/11/2015.
 */
public class RobosAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Robo> robos;

    public RobosAdapter(Context context, List<Robo> robos){
        this.inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.robos = robos;
    }


    @Override
    public int getCount() {
        return robos.size();
    }

    @Override
    public Object getItem(int position) {
        return robos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_robo, null);
        ((TextView) (v.findViewById(R.id.nome_robo))).setText(robos.get(position).getNome());
        return v;
    }
}
