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

        edNombre = findViewById(R.id.idEdNombre);
        edTelefono = findViewById(R.id.idEdTelefono);
        edEmail = findViewById(R.id.idEdEmail);

        spinerTipoTelefono = findViewById(R.id.idSpinnerTipoTelefono);

        btGuardar = findViewById(R.id.idBtGuardar);
        btLimpiar = findViewById(R.id.idBtLimpiar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                , R.array.spinner_tipos_telefono, R.layout.support_simple_spinner_dropdown_item);
        spinerTipoTelefono.setAdapter(adapter);
        spinerTipoTelefono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getSelectedItemPosition()) {
                    case 0:
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
                        break;
                    case 1:
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
                        break;
                    case 2:
                        tipoTelefono = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btGuardar.setOnClickListener(this);
        btLimpiar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idBtGuardar:
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, edNombre.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, edTelefono.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, tipoTelefono);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, edEmail.getText().toString());

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

    private void limpiarCampo() {
        edNombre.setText("");
        edEmail.setText("");
        edTelefono.setText("");
    }
}
