package com.example.oscarmartin_practica2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CuponesValoracionActivity extends AppCompatActivity {

    //Variables
    MultiAutoCompleteTextView cuponesDescuento;
    EditText notasRepartidor;
    RatingBar estrellas;
    Button botonFinal;
    Animation tranlascion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //He dejado estas dos lineas para que se me respetara el padding que puse al main
        //Ademas de que la ventana no se solapa con la barra de estado (hora, bateria, etc..)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupones_valoracion);

        //Referencias
        cuponesDescuento = findViewById(R.id.cuponesDescuento);
        notasRepartidor = findViewById(R.id.notasRepartidor);
        estrellas = findViewById(R.id.estrellas);
        botonFinal = findViewById(R.id.botonConfimrarPago);

        //Animacion
        tranlascion= AnimationUtils.loadAnimation(CuponesValoracionActivity.this, R.anim.animaciontranslateboton2);
        botonFinal.startAnimation(tranlascion);

        //Bara de progreso--> La manejo desde XMl

        // Descuentos (MultiAutoCompleteTextView)
        String[] listaCupones = {"2x1Martes", "EnvioGratis", "DescuentoEstudiante", "FamiliaNumerosa"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(CuponesValoracionActivity.this, android.R.layout.simple_dropdown_item_1line, listaCupones);
        cuponesDescuento.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        cuponesDescuento.setAdapter(adaptador);

        //Notas de repartido
        notasRepartidor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Si se ha dado a la lupa, muestro el toast
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String textoNota = notasRepartidor.getText().toString();
                    Toast.makeText(CuponesValoracionActivity.this, "Nota registrada: " + textoNota, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        //La ratingBar la manejo desde el xml

        //Boton final
        botonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuponesValoracionActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(CuponesValoracionActivity.this, "¡Pedido realizado con exito! En camino...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}