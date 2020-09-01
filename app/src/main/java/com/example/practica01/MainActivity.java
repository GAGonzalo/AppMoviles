package com.example.practica01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText passwordTextfield;
    private EditText passwordRepTextfield;
    private EditText emailTextfield;
    private EditText ccTextfield;
    private EditText ccvTextfield;
    private EditText mesTextfield;
    private EditText añoTextfield;
    private CheckBox terminosCheckBox;
    private Button registrarButton;
    private TextView seekbarText;
    private Switch recargaInicialSwitch;
    private SeekBar seekBar;
    private LinearLayout cargaInicialLayout;

    private boolean dateWrong=true;

    /* -------- TEXT WATCHERS --------- */
    private TextWatcher textWatcherPassword = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()>0){
                passwordRepTextfield.setEnabled(true);
            }
            else{
                passwordRepTextfield.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher textWatcherPassword2 = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!charSequence.toString().equals(passwordTextfield.getText().toString())){
                 passwordRepTextfield.setTextColor(Color.RED);
                 passwordRepTextfield.setError("Contraseña incorrecta");
            }
            else{
                passwordRepTextfield.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher textWatcherCC= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()<16 || charSequence.length()>16){
                ccvTextfield.setEnabled(false);
            }
            else{
                ccvTextfield.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    /* -------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recargaInicialSwitch = findViewById(R.id.cargaSwitch);
        seekBar = findViewById(R.id.seekBar);
        passwordRepTextfield = findViewById(R.id.passwordRepTextfield);
        passwordTextfield = findViewById(R.id.passwordTextfield);
        emailTextfield = findViewById(R.id.emailTextfield);
        ccTextfield = findViewById(R.id.ccTextfield);
        ccvTextfield = findViewById(R.id.ccvTextfield);
        mesTextfield = findViewById(R.id.mesTextfield);
        añoTextfield = findViewById(R.id.añoTextfield);
        cargaInicialLayout = findViewById(R.id.cargaInicialLayout);
        terminosCheckBox = findViewById(R.id.terminosCheckBox);
        registrarButton = findViewById(R.id.registrarButton);
        seekbarText = findViewById(R.id.seekBarText);


        passwordTextfield.addTextChangedListener(textWatcherPassword);
        passwordRepTextfield.addTextChangedListener(textWatcherPassword2);
        ccTextfield.addTextChangedListener(textWatcherCC);

        emailTextfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String email = emailTextfield.getText().toString();
                    String afterArroba = email.substring(email.indexOf('@')+1,email.indexOf('.'));
                    if(afterArroba.length()<3){
                        emailTextfield.setError("Email Incorrecto");
                    }
                    else{
                        emailTextfield.setError(null);

                    }
                }
            }
        });
        añoTextfield.setOnFocusChangeListener((view, b) -> {
            String mesString = mesTextfield.getText().toString();
            String añoString = añoTextfield.getText().toString();

            Date currenntTime = Calendar.getInstance().getTime();
            Integer mes = currenntTime.getMonth();
            Integer año = currenntTime.getYear()-100;
            Integer mesInput = 0;
            Integer añoInput = 0;
            if(!mesString.isEmpty() && !añoString.isEmpty()){
                mesInput=Integer.valueOf(mesString);
                añoInput=Integer.valueOf(añoString);
            }
            if (!b){
                System.out.println("Mes input: "+ mesInput);
                System.out.println("Mes: "+mes);
                System.out.println("Año input: "+ añoInput);
                System.out.println("Año: "+año);
                if(mesInput> 0 && mesInput <= 12){
                    if(añoInput > año){
                        System.out.println("1");
                        dateWrong=false;
                    }
                    else if (añoInput < año){
                        System.out.println("2");
                        dateWrong = true;
                    }
                    else if( mesInput - mes >= 3 ){
                        System.out.println("3");
                        dateWrong = false;
                    }
                    else {
                        System.out.println("4");
                        dateWrong=true;
                    }
                }
                else{
                    dateWrong=true;
                }
                if(dateWrong){
                    mesTextfield.setError("Fecha incorrecta");
                    añoTextfield.setError("Fecha incorrecta");
                }
                else{
                    mesTextfield.setError(null);
                    añoTextfield.setError(null);
                }

            }
        });

        recargaInicialSwitch.setOnCheckedChangeListener((CompoundButton cb, boolean isChecked)->{

            if(isChecked)
                cargaInicialLayout.setVisibility(View.VISIBLE);
            else
                cargaInicialLayout.setVisibility(View.GONE);

        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarText.setText("Carga inicial: $"+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        terminosCheckBox.setOnCheckedChangeListener((CompoundButton cb, boolean isChecked)->{
            if(isChecked){
                registrarButton.setEnabled(true);
            }
            else{
                registrarButton.setEnabled(false);
            }
        });
        registrarButton.setOnClickListener((View v)->{
            Toast.makeText(this,"Usuario registrado con éxito",Toast.LENGTH_SHORT).show();
        });
    }
}
