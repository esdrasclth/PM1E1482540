package com.example.pm1e1482540.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm1e1482540.MainActivity;
import com.example.pm1e1482540.R;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class UpdateContactActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    EditText nombre, telefono, notas;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        conexion= new SQLiteConexion(this, Transacciones.DBName, null, 1);

        Intent i = getIntent();
        id  = getIntent().getExtras().getString("ID");
        String name = getIntent().getExtras().getString("Nombre");
        String fono  = getIntent().getExtras().getString("Telefono");
        String notes  = getIntent().getExtras().getString("Nota");
        Button btnUpdate = (Button) findViewById(R.id.btnActualizar);

        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        notas = (EditText) findViewById(R.id.txtNotas);

        nombre.setText(name);
        telefono.setText(fono);
        notas.setText(notes);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarContacto();
            }
        });
    }

    private void actualizarContacto() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String []  params = {id};

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombre.getText().toString());
        valores.put(Transacciones.telefonos, telefono.getText().toString());
        valores.put(Transacciones.notas, notas.getText().toString());

        db.update(Transacciones.TableContactos, valores, Transacciones.id +"=?", params);
        Toast.makeText(getApplicationContext(),"Dato actualizados", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ListContacActivity.class);
        startActivity(i);
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}