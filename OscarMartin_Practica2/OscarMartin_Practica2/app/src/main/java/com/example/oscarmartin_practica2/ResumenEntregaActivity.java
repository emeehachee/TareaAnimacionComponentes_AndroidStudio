package com.example.oscarmartin_practica2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class ResumenEntregaActivity extends AppCompatActivity {

    //Variables
    EditText desglose;
    ToggleButton toggleButtonRecogida;
    TextView totalPagar;
    View lineaEntrega;
    ImageView imagen;
    Button botonAplicarDescuentosYPagar;
    Animation animacion;
    double envioACasa= 2.00;
    double precioEntrega=0.00;
    double subtotalRecibido=0.00;
    double precioTotal=0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //He dejado estas dos lineas para que se me respetara el padding que puse al main
        //Ademas de que la ventana no se solapa con la barra de estado (hora, bateria, etc..)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_entrega);

        //Referencias
        desglose=findViewById(R.id.desglose);
        toggleButtonRecogida=findViewById(R.id.toggleButtonEntrega);
        lineaEntrega = findViewById(R.id.lineaEntrega);
        totalPagar=findViewById(R.id.totalPagar);
        imagen=findViewById(R.id.imagenGoogle);
        botonAplicarDescuentosYPagar=findViewById(R.id.botonAplicarDescuentoYPagar);

        //Animaciones
        animacion= AnimationUtils.loadAnimation(ResumenEntregaActivity.this, R.anim.animacionesvariasimagen);
        imagen.startAnimation(animacion);

        //Bara de progreso--> La manejo desde XMl

        //desloge
        Intent intent = getIntent();
        String masa = intent.getStringExtra("MASA");
        String tamanio = intent.getStringExtra("TAMANIO");
        String queso = intent.getStringExtra("EXTRAQUESO");
        String bacon = intent.getStringExtra("BACONCRISPY");
        String cantidad = intent.getStringExtra("CANTIDAD");
        String subtotal = intent.getStringExtra("SUBTOTAL");

        //Me puedo permitir esto porque esta informacion extra tenia un double y no un string
        subtotalRecibido = intent.getDoubleExtra("SUBTOTAL_VALOR", 0.0);

        //Igual que en la ventan anterior, actualizar el subtotal
        //Aqui para mostarar ya por pantalla el subtotalPasado en el intent
        //no se hace despues de las referencias porque saldira 0.00 ya que aun no hemos recogido ningun valor del intent
        actualizarSubtotal();

        //Añado los datos al multiLineEditText
        desglose.append("\n");
        desglose.append("\n" + masa);
        desglose.append("\n" + tamanio);
        desglose.append("\n" + queso);
        desglose.append("\n" + bacon);
        desglose.append("\n" + cantidad);
        desglose.append("\n----------------------------------\n");
        desglose.append(subtotal);


        //ToogleButton (Modo de entrega)
        //El texto que se pone cuando esta activo o no lo manejo desde xml
        toggleButtonRecogida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    precioEntrega = envioACasa;
                    //Esto es la linea muy fina que es un View,
                    //setBackgroundColor-> permite cambiar la linea de color (A azul)
                    //y tener el efecto que aparecia en el video de prueba
                    lineaEntrega.setBackgroundColor(getColor(R.color.blue));
                } else {
                    precioEntrega = 0.00;
                    //Aqui cambio la linea (View) a color negro
                    lineaEntrega.setBackgroundColor(getColor(R.color.black));
                }

                //llamo al metodo para actualizar el subtotal en base a lo que se ha elegido
                actualizarSubtotal();
            }
        });

        //ImageView
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://google.com";
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        botonAplicarDescuentosYPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ResumenEntregaActivity.this, CuponesValoracionActivity.class);
                startActivity(intent);
            }
        });

    }

    //Mismo metodo que la ventana anterior para actualizar el subtotal (la variable de java y el textView de la ventana)
    private void actualizarSubtotal() {
        //Calculo la sumaTotal de lo que ha elegido el usario
        //es decir lo que ya estabas sumando si quiere el envio a casa o no
        precioTotal = subtotalRecibido + precioEntrega;
        String textoSubtotal = String.format("%.2f", precioTotal).replace('.', ',');
        totalPagar.setText("Total a pagar: " + textoSubtotal + " €");
    }
}