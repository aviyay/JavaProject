package com.bnet.data.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;

public class BusinessEditor extends Activity {

    EditText nameField,countryField,cityField,streetField,phoneField,emailField,websiteField;
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private String getText(EditText editText) throws Exception {
        assertField(editText);
        return editText.getText().toString();
    }
    private void assertField(EditText text) throws Exception {
        if(isEmpty(text))
            throw new  Exception(String.format(getString(R.string.error_field_empty), text.getHint()));
    }
    void initializeSubmit()
    {
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Business bis=new Business();
                    bis.setName(getText(nameField));
                    bis.setAddress(new Address(getText(countryField),getText(cityField),getText(streetField)));
                    bis.setPhone(getText(phoneField));
                    bis.setEmail(getText(emailField));
                    bis.setLinkToWebsite(getText(websiteField));
                    new AsyncTask<Business,Void,Void>()
                    {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            Toast.makeText(getApplicationContext(), R.string.connecting_to_server, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected Void doInBackground(Business... params) {
                            RepositoriesFactory.getBusinessesRepository().addAndReturnAssignedId(params[0]);
                            return null;

                        }
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(getApplicationContext(), "TEMP: Business was added", Toast.LENGTH_SHORT).show();
                            finish();


                        }
                    }.execute(bis);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_header)+ex.getMessage(), Toast.LENGTH_SHORT).show();

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
        initializeFields();
        initializeSubmit();
    }
}
