package com.example.proyectooxigen.rules;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class validaciones {

    private boolean valid=true;

    public boolean checkField(EditText text)
    {
        if(text.getText().toString().isEmpty()){
            text.setError("Error");
            valid=false;
        }else{ valid=true;}

        return valid;

    }


    public boolean checkSpinner(Spinner sp, String mensaje)
    {
        if(sp.getSelectedItemPosition()==0){
            TextView errorText=(TextView)sp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(mensaje);
            valid=false;
        }else {valid=true;}

        return valid;

    }
}
