package com.bnet.data.controller;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.data.model.entities.ActivityType;

import com.bnet.data.model.entities.Business;
import com.bnet.data.model.entities.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditor extends Activity {

    private Spinner typeSpinner;
    private EditText countryField;
    private EditText startDateField;
    private EditText startTimeField;
    private EditText endDateField;
    private EditText endTimeField;
    private EditText priceField;
    private EditText descriptionField;
    private EditText businessIDField;
    private Spinner businessIDSpinner;
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private String getText(EditText editText) throws Exception {
        assertField(editText);
        return editText.getText().toString();
    }
    private void assertField(EditText text) throws Exception {
        if(isEmpty(text))
            throw new  Exception("The field "+text.getHint()+ " can't be empty!");
    }
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-15 15:55:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        typeSpinner = (Spinner)findViewById( R.id.typeSpinner );
        typeSpinner.setAdapter(new ArrayAdapter<ActivityType>(this, android.R.layout.simple_spinner_item, ActivityType.values()));

        countryField = (EditText)findViewById( R.id.countryField );
        startDateField = (EditText)findViewById( R.id.startDateField );
        startTimeField = (EditText)findViewById( R.id.startTimeField );
        endDateField = (EditText)findViewById( R.id.endDateField );
        endTimeField = (EditText)findViewById( R.id.endTimeField );
        priceField = (EditText)findViewById( R.id.priceField );
        descriptionField = (EditText)findViewById( R.id.descriptionField );
        initializeBusinessId();
    }

    private void initializeBusinessId() {
        businessIDField = (EditText)findViewById( R.id.businessIDField );
        businessIDField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(businessIDSpinner.getVisibility()== View.VISIBLE) {
                    if(businessIDSpinner.getSelectedItem()!=null) {
                        String item = businessIDSpinner.getSelectedItem().toString();
                        if (item.substring(item.indexOf('(') + 1).replace(")", "").equals(s.toString()))
                            return;
                    }

                        businessIDSpinner.setSelection(0);
                }
            }
        });
        businessIDSpinner=((Spinner) findViewById(R.id.businessSpinner));
        new AsyncTask<Void,Void,List<Business>>()
        {

            @Override
            protected List<Business> doInBackground(Void... params) {
                return RepositoriesFactory.getBusinessesRepository().getAll();
            }

            @Override
            protected void onPostExecute(List<Business> businesses) {
                ArrayList<String> st=new ArrayList<String>();
                st.add("Select Business");
                for(Business item : businesses)
                    st.add(item.getName()+"("+item.getId()+")");
                if(st.size()>1) {
                    businessIDSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, st));
                    businessIDSpinner.setVisibility(View.VISIBLE);
                    businessIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0)
                                return;
                            String item=businessIDSpinner.getSelectedItem().toString();
                            businessIDField.setText(item.substring(item.indexOf('(')+1).replace(")",""));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        }.execute();
    }

    void initializeSubmit()
    {
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    com.bnet.data.model.entities.Activity acti=new com.bnet.data.model.entities.Activity();
                            acti.setType(ActivityType.valueOf(typeSpinner.getSelectedItem().toString()));
                    acti.setCountry(getText(countryField));
                    acti.setStart(DateTime.parse(getText(startTimeField)+' '+getText(startDateField)));
                    acti.setEnd(DateTime.parse(getText(endTimeField)+' '+getText(endDateField)));
                    acti.setPrice(Double.parseDouble(getText(priceField)));
                    acti.setDescription(getText(descriptionField));
                    acti.setBusinessId(Integer.parseInt(getText(businessIDField)));
                    new AsyncTask<com.bnet.data.model.entities.Activity,Void,Void>()
                    {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            Toast.makeText(getApplicationContext(), R.string.connecting_to_server, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected Void doInBackground(com.bnet.data.model.entities.Activity... params) {
                            RepositoriesFactory.getActivitiesRepository().addAndReturnAssignedId(params[0]);
                            return null;

                        }
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(getApplicationContext(), "TEMP: Activity was added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.execute(acti);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_header)+ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_editor);
        findViews();
        initializeSubmit();
    }

}
