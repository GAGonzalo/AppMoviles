package com.example.practica01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText passwordTextfield;
    private EditText passwordRepTextfield;
    private EditText emailTextfield;
    private EditText ccTextfield;
    private EditText ccvTextfield;
    private CheckBox terminosCheckBox;
    private Button registrarButton;
    private TextView seekbarText;
    private Switch recargaInicialSwitch;
    private SeekBar seekBar;
    private LinearLayout cargaInicialLayout;
    private RadioGroup tarjetasRG;
    private RadioButton debitoRadioButton;
    private RadioButton creditoRadioButton;
    private Spinner mesSpinner;
    private Spinner añoSpinner;


    private boolean dateWrong=true;
    private boolean pwWrong=true;
    private boolean emailWrong= true;
    private boolean ccSelected = false;
    private boolean ccWrong=true;
    private boolean ccvWrong = true;
    private List<Integer> añosCC;
    private List<Integer> mesesCC;

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
                 pwWrong=true;
            }
            else{
                passwordRepTextfield.setTextColor(Color.BLACK);
                pwWrong=false;
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
            if(charSequence.length()<16 ){
                ccvTextfield.setEnabled(false);
                ccWrong=true;
            }
            else{
                ccvTextfield.setEnabled(true);
                ccWrong=false;
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
        setWidgets();
        setListeners();


    }

    private void setWidgets(){
        recargaInicialSwitch = findViewById(R.id.cargaSwitch);
        seekBar = findViewById(R.id.seekBar);
        passwordRepTextfield = findViewById(R.id.passwordRepTextfield);
        passwordTextfield = findViewById(R.id.passwordTextfield);
        emailTextfield = findViewById(R.id.emailTextfield);
        ccTextfield = findViewById(R.id.ccTextfield);
        ccvTextfield = findViewById(R.id.ccvTextfield);
        cargaInicialLayout = findViewById(R.id.cargaInicialLayout);
        terminosCheckBox = findViewById(R.id.terminosCheckBox);
        registrarButton = findViewById(R.id.registrarButton);
        seekbarText = findViewById(R.id.seekBarText);
        tarjetasRG= findViewById(R.id.tarjetasRadioGroup);
        creditoRadioButton=findViewById(R.id.creditoRadioButton);
        debitoRadioButton=findViewById(R.id.debitoRadioButton);

        añoSpinner= findViewById(R.id.añoSpinner);
        mesSpinner= findViewById(R.id.mesSpinner);
        setFechas();
        añoSpinner.setAdapter(new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,añosCC));
        mesSpinner.setAdapter(new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,mesesCC));
        mesSpinner.setSelection(Adapter.NO_SELECTION,false );
        añoSpinner.setSelection(Adapter.NO_SELECTION,false);

    }
    private void setListeners(){
        passwordTextfield.addTextChangedListener(textWatcherPassword);
        passwordRepTextfield.addTextChangedListener(textWatcherPassword2);


        RadioGroup.OnCheckedChangeListener tarjetasListener = (radioGroup, i) -> {
            switch(i){
                case R.id.creditoRadioButton:
                    ccSelected =true;
                    break;
                case R.id.debitoRadioButton:
                    ccSelected =true;
                    break;
            }
        };
        tarjetasRG.setOnCheckedChangeListener(tarjetasListener);

        ccTextfield.addTextChangedListener(textWatcherCC);

        ccvTextfield.setOnFocusChangeListener((view,b)-> {
            if (ccvTextfield.getText().toString().length() < 3) {
                ccvWrong = true;
                ccvTextfield.setError("Ingrese el CCV correctamente");
            } else {
                ccvWrong = false;
                ccvTextfield.setError(null);
            }

        });

        mesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkFechas(position,añoSpinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        añoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkFechas(mesSpinner.getSelectedItemPosition(),position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        emailTextfield.setOnFocusChangeListener((view, b) -> {
            if(!b){

                String email = emailTextfield.getText().toString();
                if(!email.isEmpty() && email.contains("@")) {
                    String afterArroba = email.substring(email.indexOf('@') + 1, email.indexOf('.'));
                    if (afterArroba.length() < 3) {
                        emailTextfield.setError("Email Incorrecto");
                        emailWrong = true;
                    } else {
                        emailTextfield.setError(null);
                        emailWrong = false;
                    }
                }
                else{
                    emailTextfield.setError("Email Incorrecto");
                }
            }
        });


        recargaInicialSwitch.setOnCheckedChangeListener((CompoundButton cb, boolean isChecked)->{

            if(isChecked) {
                seekbarText.setText("Carga inicial: $" + seekBar.getProgress());
                cargaInicialLayout.setVisibility(View.VISIBLE);
            }
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

            if(!ccvWrong && !ccWrong && !dateWrong && !emailWrong && !pwWrong && ccSelected){
                Toast.makeText(this,"Usuario registrado con éxito",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Verifique que todos los campos sean correctos",Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void setFechas(){
        mesesCC=new ArrayList<>();
        añosCC = new ArrayList<>();
        Date fecha = Calendar.getInstance().getTime();
        Integer año = fecha.getYear()-100;
        for(int i = 0; i<12 ; i++){
            if(i<6){
                añosCC.add(año+Integer.valueOf(i));
            }
            mesesCC.add(Integer.valueOf(i+1));
        }
    }
    private void checkFechas(int mesPosition, int añoPosition){
        String mesString = mesSpinner.getItemAtPosition(mesPosition).toString();
        String añoString = añoSpinner.getItemAtPosition(añoPosition).toString();

        Date currenntTime = Calendar.getInstance().getTime();
        Integer mes = currenntTime.getMonth();
        Integer año = currenntTime.getYear()-100;
        Integer mesInput = 0;
        Integer añoInput = 0;
        if(!mesString.isEmpty() && !añoString.isEmpty()){
            mesInput=Integer.valueOf(mesString);
            añoInput=Integer.valueOf(añoString);
        }
        if(mesInput> 0 && mesInput <= 12){
            if(añoInput > año){
                dateWrong=false;
            }
            else if(mesInput - mes >= 3 ){
                dateWrong = false;
            }
            else {
                dateWrong=true;
            }
        }
        else{
            dateWrong=true;
        }
        if(dateWrong){
            Toast.makeText(MainActivity.this, "Fecha incorrecta", Toast.LENGTH_LONG).show();
        }
    }
}


