package com.bnet.data.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.DatabaseFactory;
import com.bnet.data.model.entities.Address;
import com.bnet.data.model.entities.Business;

public class BusinessEditor extends Activity {

    EditText nameField,countryField,cityField,streetField,phoneField,emailField,websiteField;
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void assertField(EditText text) throws Exception {
        if(isEmpty(text))
            throw new  Exception("The field "+text.getHint()+ "can't be empty!");
    }
    void initializeSubmit()
    {
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    assertField(nameField);
                    assertField(countryField);
                    assertField(cityField);
                    assertField(streetField);
                    assertField(phoneField);
                    assertField(emailField);
                    assertField(websiteField);

                    DatabaseFactory.getDatabase().addBusiness(new Business(nameField.getText().toString(), new Address(countryField.getText().toString(),cityField.getText().toString(),streetField.getText().toString()),
                            phoneField.getText().toString(),emailField.getText().toString(),websiteField.getText().toString()));
                    Toast.makeText(getApplicationContext(), "TEMP: Business "+nameField.getText().toString()+" was created", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    void initializeFields()
    {
        nameField=(EditText)findViewById(R.id.nameField);
        countryField=(EditText)findViewById(R.id.countryField);
        cityField=(EditText)findViewById(R.id.cityField);
        streetField=(EditText)findViewById(R.id.streetField);
        phoneField=(EditText)findViewById(R.id.phoneField);
        emailField=(EditText)findViewById(R.id.emailField);
        websiteField=(EditText)findViewById(R.id.websiteField);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_editor);
    }
}
