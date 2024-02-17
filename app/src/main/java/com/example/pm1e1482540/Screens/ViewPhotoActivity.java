package com.example.pm1e1482540.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pm1e1482540.R;

import java.io.ByteArrayInputStream;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class ViewPhotoActivity extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);

    ImageView picture;

    Button regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        picture = (ImageView) findViewById(R.id.imageView2);
        regresar = (Button)   findViewById(R.id.btnRegresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListContacActivity.class);
                startActivity(intent);
            }


        });
        Bitmap retornarFoto = buscarPicture(getIntent().getStringExtra("codigo"));
        picture.setImageBitmap(retornarFoto);
    }

    private Bitmap buscarPicture(String id) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String sql = "SELECT imagen FROM contactos WHERE id ="+ id;
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}