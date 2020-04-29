package com.gago.appagregarcontacto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edNombre, edTelefono, edEmail;
    Spinner spinerTipoTelefono;
    int tipoTelefono;
    Button btGuardar, btLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //referenciamos las vista
        edNombre = findViewById(R.id.idEdNombre);
        edTelefono = findViewById(R.id.idEdTelefono);
        edEmail = findViewById(R.id.idEdEmail);

        spinerTipoTelefono = findViewById(R.id.idSpinnerTipoTelefono);

        btGuardar = findViewById(R.id.idBtGuardar);
        btLimpiar = findViewById(R.id.idBtLimpiar);
        //creamos el adaptador para el spinner usando la lista que creamos en la carpeta values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                , R.array.spinner_tipos_telefono, R.layout.support_simple_spinner_dropdown_item);
        spinerTipoTelefono.setAdapter(adapter);
        //configuramos el listener para cuando se selecciona un item del spinner
        spinerTipoTelefono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //aqui obtenemos la posicion en el spiner para saber cual opcion escogio el usuario
                switch (parent.getSelectedItemPosition()) {
                    case 0:
                        //aqui guardamos la constante que se refiere al tipo de telefono para casa
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
                        break;
                    case 1:
                        //aqui para tipo movil
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
                        break;
                    case 2:
                        //aqui para tipo trabajo
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //seteamos para que el listener de los botones este en la clase mainActivity
        btGuardar.setOnClickListener(this);
        btLimpiar.setOnClickListener(this);
    }

    //sobrescribimos el metodo onclick para manejar los botones
    @Override
    public void onClick(View v) {
        //aqui vemos dependiendo de cual boton presiono hacemos una accion
        switch (v.getId()) {
            case R.id.idBtGuardar:
                // creamos un intent inplisito para una accion de insercion
                Intent intent = new Intent(Intent.ACTION_INSERT);
                //ponemos el tipo como tipo de contacto
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                //ingresamos los datos al intent. usamos como identificador de esos datos unas constantes
                //que identifican cada tipo de dato
                intent.putExtra(ContactsContract.Intents.Insert.NAME, edNombre.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, edTelefono.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, tipoTelefono);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, edEmail.getText().toString());

                //comprobamos si existe una aplicacion que pueda recibir el intent e iniciamos el activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Contacto guardado", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.idBtLimpiar:
                limpiarCampo();
                break;
        }
    }

    /**
     * metodo solo para limpiar los campos
     */
    private void limpiarCampo() {
        edNombre.setText("");
        edEmail.setText("");
        edTelefono.setText("");
    }
}
