package com.example.oscarmartin_practica2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionPizzaActivity extends AppCompatActivity {

    //Variables
    TextView correoCliente;
    ImageView imagenPizza;
    Spinner masaPizza;
    RadioGroup tamanioGrupo;
    RadioButton mediana;
    RadioButton familiar;
    CheckBox extraQueso;
    CheckBox baconCrispy;
    ImageView imagenQueso;
    ImageView imagenBacon;
    TextView textoCantidadPizza;
    SeekBar cantidadPizza;
    TextView subtotal;
    Button botonContinuar;
    Animation opacidad;
    Animation rotacion;
    double precioMediana=8.00;
    double precioFamiliar=12.00;
    double precioExtraQueso=1.50;
    double precioBaconCrispy=1.50;
    double precioTamanioSeleccionado=precioMediana;
    double precioIngredientes=0.00;
    int numeroPizza=1;
    double sumaTotal=0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //He dejado estas dos lineas para que se me respetara el padding que puse al main
        //Ademas de que la ventana no se solapa con la barra de estado (hora, bateria, etc..)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_pizza);

        //Referencias
        correoCliente = findViewById(R.id.textocorreoCliente);
        imagenPizza = findViewById(R.id.imagenPizza);
        masaPizza = findViewById(R.id.spinnerMasa);
        tamanioGrupo = findViewById(R.id.tamanioGrupo);
        mediana = findViewById(R.id.mediana);
        familiar = findViewById(R.id.familiar);
        extraQueso = findViewById(R.id.extraQueso);
        baconCrispy = findViewById(R.id.baconCrispy);
        imagenQueso = findViewById(R.id.imagenQueso);
        imagenBacon = findViewById(R.id.imagenBacon);
        textoCantidadPizza = findViewById(R.id.textCantidadPizza);
        cantidadPizza = findViewById(R.id.barraCantidadPizza);
        subtotal = findViewById(R.id.subtotal);
        botonContinuar = findViewById(R.id.botonContinuarResumen);

        //Animaciones
        rotacion = AnimationUtils.loadAnimation(ConfiguracionPizzaActivity.this, R.anim.animacionrotateimagen);
        imagenPizza.startAnimation(rotacion);

        opacidad = AnimationUtils.loadAnimation(ConfiguracionPizzaActivity.this, R.anim.animacionalphatexto);
        correoCliente.startAnimation(opacidad);


        //Bara de progreso--> La manejo desde XMl

        //Cliente correo
        Intent intent = getIntent();
        String correo = intent.getStringExtra("CORREO");
        correoCliente.append(correo);

        // Actualiza el subtotal nada más iniciar la ventana para mostrar el precio del tamaño seleccionado por defecto (Mediana)
        actualizarSubtotal();

        //Seleccion de masa
        masaPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    imagenPizza.setImageResource(R.drawable.pizza1);
                }else if(position==1){
                    imagenPizza.setImageResource(R.drawable.pizza2);
                }else if(position==2){
                    imagenPizza.setImageResource(R.drawable.pizza3);
                }

                imagenPizza.startAnimation(rotacion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Seleccion tamaño Pizza
        //Que el boton de mediana ya salga con el color azul lo manejo desde xml
        //tuve que usar app: en vez de android:
        tamanioGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.mediana) {
                    //Cambiar el color del boton cuando se pulsa a azul
                    //setButtonTintList-> permite cambiar el color del boton
                    //ColorStateList->representa los colores que puede utilizar un componente segun su estado
                    mediana.setButtonTintList(ColorStateList.valueOf(getColor(R.color.blue)));

                    //No dejar dos colores azules, si uno esta azul el otro en gris
                    familiar.setButtonTintList(ColorStateList.valueOf(getColor(R.color.gray)));
                    precioTamanioSeleccionado=precioMediana;
                } else if (checkedId == R.id.familiar) {
                    familiar.setButtonTintList(ColorStateList.valueOf(getColor(R.color.blue)));
                    mediana.setButtonTintList(ColorStateList.valueOf(getColor(R.color.gray)));
                    precioTamanioSeleccionado=precioFamiliar;
                }

                //llamo al metodo para actualizar el subtotal en base a lo que se ha elegido
                actualizarSubtotal();
            }
        });

        //Seleccion Ingredientes Extra
        //el icono del tic blanco lo gestiono desde las propiedades xml del checkBox
        //tuve que usar app: en vez de android:
        extraQueso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    extraQueso.setButtonTintList(ColorStateList.valueOf(getColor(R.color.blue)));
                    //setVisibility, para mostrar o poner invisible(gone) al elemento
                    //se usa View porque todos los elementos visuales heredan de View
                    imagenQueso.setVisibility(View.VISIBLE);
                    precioIngredientes= precioIngredientes+precioExtraQueso;
                }else{
                    extraQueso.setButtonTintList(ColorStateList.valueOf(getColor(R.color.gray)));
                    imagenQueso.setVisibility(View.GONE);
                    precioIngredientes= precioIngredientes-precioExtraQueso;
                }

                //llamo al metodo para actualizar el subtotal en base a lo que se ha elegido
                actualizarSubtotal();
            }
        });

        baconCrispy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    baconCrispy.setButtonTintList(ColorStateList.valueOf(getColor(R.color.blue)));
                    imagenBacon.setVisibility(View.VISIBLE);
                    precioIngredientes= precioIngredientes+precioBaconCrispy;
                }else{
                    baconCrispy.setButtonTintList(ColorStateList.valueOf(getColor(R.color.gray)));
                    imagenBacon.setVisibility(View.GONE);
                    precioIngredientes= precioIngredientes-precioBaconCrispy;
                }

                //llamo al metodo para actualizar el subtotal en base a lo que se ha elegido
                actualizarSubtotal();
            }
        });

        //Seleccion cantidad de Pizzas
        cantidadPizza.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //Para evitar que se puedan pedir 0 pizzas, se pone 1 como cantidad minima
                if (progress< 1) {
                    progress= 1;
                    seekBar.setProgress(1);
                }

                //Operador ternario para saber si se pone pizza o pizzas
                String palabra =(progress==1) ? " pizza" : " pizzas";

                textoCantidadPizza.setText("Cantidad: " + progress+palabra );

                numeroPizza=progress;

                //llamo al metodo para actualizar el subtotal en base a lo que se ha elegido
                actualizarSubtotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //boton para continuar
        botonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfiguracionPizzaActivity.this, ResumenEntregaActivity.class);

                //Preparo los textos para los putExtra y asi lo pasa como String
                String masaTexto = "• Masa: " + masaPizza.getSelectedItem().toString();

                String tamanioTexto ="";
                if(mediana.isChecked()){
                    //String.format para pasar el precio con dos decimales
                    tamanioTexto="• Tamaño: Mediana ("+ String.format("%.2f", precioMediana)+ "€)";
                }else if(familiar.isChecked()){
                    tamanioTexto="• Tamaño: Familiar (" +String.format("%.2f", precioFamiliar) + "€)";
                }

                String quesoTexto ="";
                if(extraQueso.isChecked()){
                    quesoTexto="• Extra Queso: Si (+" +  String.format("%.2f", precioExtraQueso) + "€)";
                }else{
                    quesoTexto="• Extra Queso: No";
                }

                String baconTexto ="";
                if(baconCrispy.isChecked()){
                    baconTexto="• Extra Bacon: Si (+" +String.format("%.2f", precioBaconCrispy) + "€)";
                }else{
                    baconTexto="• Extra Bacon: No";
                }

                String cantidadTexto = "• Cantidad: " + numeroPizza + " pizza(s)";

                String subtotalTexto = "Subtotal: " +  String.format("%.2f", sumaTotal).replace('.', ',') + " €";

                //Añado todos los putExtra el intent
                intent.putExtra("MASA", masaTexto);
                intent.putExtra("TAMANIO", tamanioTexto);
                intent.putExtra("EXTRAQUESO", quesoTexto);
                intent.putExtra("BACONCRISPY", baconTexto);
                intent.putExtra("CANTIDAD", cantidadTexto);
                intent.putExtra("SUBTOTAL", subtotalTexto);

                //Este putExtra paso el valor como tal, es decir un double , para poder realizar operaciones con el en la siguiente ventana
                intent.putExtra("SUBTOTAL_VALOR", sumaTotal);

                startActivity(intent);
            }
        });
    }

    //Metodo para actualizar el subtotal (la variable de java y el textView de la ventana)
    //Se ejectura cada vez que el usuario añade algo nuevo, quita algo ya seleccionado, o cambia alguna opcion,
    //para que el textView y la variable esten en un estado consistente y no muestra datos erronos
    //private para que solo se puede usar en la misma clase
    private void actualizarSubtotal() {
        //Calculo la sumaTotal de lo que ha elegido el usario
        sumaTotal =(precioTamanioSeleccionado+precioIngredientes)*numeroPizza;

        //Variable no declarada arriba ya que solo "vive" (de un solo uso temporal) en el metodo
        //Lo seteo en la textView
        //Lo paso a String para poder cambiar el punto por la coma y se ponga con dos deciamles
        String textoSubtotal= String.format("%.2f", sumaTotal).replace('.', ',');
        subtotal.setText("Subtotal: "+textoSubtotal+ " €");
    }

}