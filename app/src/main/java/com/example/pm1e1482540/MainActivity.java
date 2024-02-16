package com.example.pm1e1482540;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    EditText nombre, telefono, nota;
    Spinner pais;
    Button btnguardar, btnlistcontactos;
    Bitmap imagen;

    ImageView imgFoto;
    ImageButton btnFoto;

    static final  int REQUEST_IMAGE = 101;
    static final  int PETICION_ACCESS_CAM = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre= (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNotas);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        Button btnguardar= (Button) findViewById(R.id.btnGuardar);

        pais = (Spinner) findViewById(R.id.txtSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Honduras (504)", "Costa Rica (506)", "Guatemala (502)", "El Salvador (503)"});

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pais.setAdapter(adapter);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Metodo para obtener los permisos requeridos de la aplicacion
                if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},PETICION_ACCESS_CAM);
                }
                else
                {
                    dispatchTakePictureIntent();

                }
            }
        });


    }
}