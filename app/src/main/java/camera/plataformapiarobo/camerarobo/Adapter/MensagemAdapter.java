package camera.plataformapiarobo.camerarobo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import camera.plataformapiarobo.camerarobo.Models.Mensagem;
import camera.plataformapiarobo.camerarobo.R;

/**
 * Created by rafael on 12/11/2015.
 */
public class MensagemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Mensagem> mensagems;

    public MensagemAdapter(Context context, List<Mensagem> mensagems){
        this.inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mensagems= mensagems;
    }


    @Override
    public int getCount() {
        return mensagems.size();
    }

    @Override
    public Object getItem(int position) {
        return mensagems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if(mensagems.get(position).getIsSendPaciente()){
            v = inflater.inflate(R.layout.item_mensagens_server, null);
            ((TextView) (v.findViewById(R.id.text_mensagem_paciente))).setText(mensagems.get(position).getMsg());
        }
        else {
            v = inflater.inflate(R.layout.item_mensagens_paciente, null);
            ((TextView) (v.findViewById(R.id.text_mensagem_paciente))).setText(mensagems.get(position).getMsg());
        }
        return v;
    }
}
