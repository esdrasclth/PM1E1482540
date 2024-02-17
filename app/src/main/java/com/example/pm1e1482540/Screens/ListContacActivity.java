package com.example.pm1e1482540.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.Manifest;
import com.example.pm1e1482540.R;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Models.Contactos;

public class ListContacActivity extends AppCompatActivity {

    SQLiteConexion conexion;

    Button btnver, btncompartir, btneliminar, btnactualizar, btnllamar;
    Button btnverImagen;
    Button btnEnviar;

    EditText buscar;

    ListView listaContactos;

    ArrayList<Contactos> ArrayLista;
    ArrayList<String> ArrayContactos;
    Contactos Contacto;


    private EditText busqueda;
    private String Dato;
    private String Pais;
    private String Nombre;
    private String Telefono;
    private String Nota;
    private Boolean SelectedRow = false;


    private static final int REQUEST_CALL = 1;
    private AlertDialog.Builder EliminarItem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contac);

        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        buscar = (EditText) findViewById(R.id.txtBuscar);
        listaContactos = (ListView) findViewById(R.id.listView);
        busqueda = (EditText) findViewById(R.id.txtBuscar);
        btneliminar = (Button) findViewById(R.id.btnEliminar);
        btnllamar = (Button) findViewById(R.id.btnLlamar);
        btnactualizar = (Button) findViewById(R.id.btnActualizar);
        btnverImagen = (Button) findViewById(R.id.btnVer);
        btnEnviar = (Button) findViewById(R.id.btnCompartir);


        obtenerListaContactos();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArrayContactos);
        listaContactos.setAdapter(adp);

        buscar = (EditText) findViewById(R.id.txtBuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adp.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listaContactos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Dato = "" + ArrayLista.get(position).getId();
                Nombre = "" + ArrayLista.get(position).getNombres();
                Telefono = "+" + ArrayLista.get(position).getTelefonos();
                Nota = "" + ArrayLista.get(position).getNotas();
                SelectedRow = true;
            }
        });

        //Boton de eliminar
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedRow == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListContacActivity.this);
                    builder.setMessage("Desea eliminar a " + Nombre);
                    builder.setTitle("Eliminar");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eliminarContacto();
                            Intent intent = new Intent(ListContacActivity.this, ListContacActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(ListContacActivity.this, "Seleccione un contacto de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON LLAMAR
        btnllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedRow==true){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ListContacActivity.this);
                    builder.setMessage("llamar a "+ Nombre);
                    builder.setTitle("Llamar");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            realizarllamada();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(ListContacActivity.this, "Seleccione un contacto de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON DE MOSTRAR Imagen

        btnverImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), ViewPhotoActivity.class);
                    startActivity(intent);
                    intent.putExtra("codigo", Dato + "");
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Intent intent = new Intent(getApplicationContext(), ViewPhotoActivity.class);
                    intent.putExtra("codigo", "1");
                    startActivity(intent);
                }
            }
        });

        //BOTON COMPARTIR CONTACTO
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirContacto();
            }
        });

        //BOTON ACTUALIZAR

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedRow==true){
                    Intent i = new Intent(getApplicationContext(), UpdateContactActivity.class);
                    i.putExtra("ID", Dato);
                    i.putExtra("Nombre", Nombre);
                    i.putExtra("Telefono", Telefono);
                    i.putExtra("Nota", Nota);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(ListContacActivity.this, "Seleccione un contacto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void realizarllamada() {
        String number = Telefono;
        if (SelectedRow==true){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(this, "Seleccione un contacto de la Lista", Toast.LENGTH_SHORT).show();
        }
    }

    private void compartirContacto() {
        String contactoEnviado = "El numero de: "+Nombre+" es "+Telefono;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contactoEnviado);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

   private void obtenerListaContactos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos listviewContactos = null;
        ArrayLista = new ArrayList<Contactos>();

        Cursor cursor = db.rawQuery(Transacciones.SelectAllsContactos, null);

        while (cursor.moveToNext()) {
            listviewContactos = new Contactos();
            listviewContactos.setId(cursor.getInt(0));
            listviewContactos.setNombres(cursor.getString(1));
            listviewContactos.setTelefonos(cursor.getString(2));
            listviewContactos.setNotas(cursor.getString(3));
            ArrayLista.add(listviewContactos);
        }
        cursor.close();
        FillList();
    }

    private void FillList () {
        ArrayContactos = new ArrayList<String>();

        for (int i = 0; i < ArrayLista.size(); i++) {

            ArrayContactos.add(ArrayLista.get(i).getNombres() + " | "
                    + ArrayLista.get(i).getTelefonos());
        }
    }

    private void eliminarContacto() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {Dato};
        String wherecond = Transacciones.id + "=?";
        db.delete(Transacciones.TableContactos, wherecond, params);
        Toast.makeText(getApplicationContext(), "Dato eliminado correctamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                realizarllamada();
            }else{
                Toast.makeText(this, "Permisos No otorgados", Toast.LENGTH_SHORT).show();
            }
        }
    }

}