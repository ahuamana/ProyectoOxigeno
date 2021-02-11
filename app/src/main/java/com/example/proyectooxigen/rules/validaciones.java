package com.example.proyectooxigen.rules;

import android.widget.EditText;

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
}
