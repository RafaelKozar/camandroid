package camera.plataformapiarobo.camerarobo.BancoDeDados;

/**
 * Created by rafael on 14/11/2015.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "robos";
    private static final int VERSAO_BD = 7;


    public BDCore(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table selecionado(_id integer primary key autoincrement, registro text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table selecionado;");
        onCreate(bd);
    }

}