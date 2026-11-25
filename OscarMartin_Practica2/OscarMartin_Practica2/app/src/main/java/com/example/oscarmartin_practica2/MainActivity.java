package com.example.oscarmartin_practica2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Variables
    TextView titulo;
    EditText correo;
    EditText contra;
    AutoCompleteTextView ciudad;
    Switch recordar;
    Button boton;
    Animation animacionesVarias;
    Animation tranlascion;
    String correoValido="prueba@gmail.com";
    String contraValida="1234";


    //HE PODIDO MANEJAR QUE CUANDO SE CLICKE CUALQUIER EDITTEXT EDITABLE, SE PONGA SU LINEA INFERIOR DE COLOR VERDE DE LA SIGUIENTE MANERA
    //Dentro de la carpeta values/themes/ , he encontrado dos archivos, uno para el modo claro y otro para el oscuro
    //A ambos les he añadido esta propiedad dentro de la primera etiqueta de style (que sirve para definir un conjunto de propiedades de apariencia,
    // con los siguientes atributos: name: es el nombre que le das al estilo,parent: indica de qué tema hereda sus propiedades
    //<item name="colorControlActivated">@color/blue</item> -> permite controlar el color azul para controles activados o seleccionados o reciben el foco
    //item: permite configurar una propiedad concreta
    // como el nombre indica color para controles (componente de la interfaz) y activado , ese nombre no se puede cambiar
    // porque Android busca exactamente ese nombre para saber qué propiedad debe modificar, es decir son son propiedades predefinidas que Android reconoce

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //He dejado estas dos lineas para que se me respetara el padding que puse al main
        //Ademas de que la ventana no se solapa con la barra de estado (hora, bateria, etc..)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias
        titulo=findViewById(R.id.titulo);
        correo=findViewById(R.id.correoCliente);
        contra=findViewById(R.id.contraCliente);
        ciudad=findViewById(R.id.ciudadCliente);
        recordar=findViewById(R.id.recordarDireccion);
        boton=findViewById(R.id.botonEntrarCarta);

        //Animaciones
        animacionesVarias= AnimationUtils.loadAnimation(MainActivity.this, R.anim.animacionesvariastexto);
        titulo.startAnimation(animacionesVarias);

        tranlascion= AnimationUtils.loadAnimation(MainActivity.this, R.anim.animaciontranslateboton1);
        boton.startAnimation(tranlascion);

        //Correo
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().contains("@")) {
                    //Si no se cumple la validacion se añade al campo el icono error que aparece al final del campo
                    // con el mensaje que he puesto
                    correo.setError("Introduce un correo valido");
                } else {
                    //Con null hacemos que ya no se muestre el error
                    correo.setError(null);
                }
            }
        });

        //Ciudades
        ArrayAdapter<CharSequence> ciudades = ArrayAdapter.createFromResource(MainActivity.this, R.array.ciudades, android.R.layout.simple_dropdown_item_1line);
        ciudad.setAdapter(ciudades);

        //Switch recordar
        recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordar.isChecked()) {
                    //Cambiar el color del boton cuando se pulsa a azul
                    //setThumbTintList-> permite cambiar el color del switch
                    //ColorStateList->representa los colores que puede utilizar un componente segun su estado
                    //Lo hago desde java para que solo se ponga azul cuando se pulse en el, asi en el layout por defecto sale normal
                    recordar.setThumbTintList(ColorStateList.valueOf(getColor(R.color.blue)));
                    Toast.makeText(MainActivity.this, "Dirección guardada", Toast.LENGTH_SHORT).show();
                }else{
                    //Quito ese color azul puesto y lo dejo por defecto con null
                    recordar.setThumbTintList(null);

                }
            }
        });

        //Boton
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoCorreo = correo.getText().toString().trim();
                String textoContra = contra.getText().toString().trim();

                //Si los campos estan vacios
                if (textoCorreo.isEmpty() || textoContra.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completa los datos de acceso", Toast.LENGTH_SHORT).show();

                //Si no se pone el usuario y contraseña correcta
                }else if(!textoCorreo.equals(correoValido) || !textoContra.equals(contraValida)){
                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ConfiguracionPizzaActivity.class);
                    intent.putExtra("CORREO", textoCorreo);
                    startActivity(intent);
                }
            }
        });

    }

}