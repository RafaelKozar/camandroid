package camera.plataformapiarobo.camerarobo.BancoDeDados;

/**
 * Created by rafael on 14/11/2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserir(String id) {
        ContentValues valores = new ContentValues();
        valores.put("registro", id);
        bd.insert("selecionaod", null, valores);
    }


    public String buscar() {

        String[] colunas = new String[]{"_id", "registro"};

        Cursor cursor = bd.query("selecionado", colunas, null, null, null, null, "registro ASC");

        int i = 0;
        String[] retorno = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(1);
        } else return null;

    }
}