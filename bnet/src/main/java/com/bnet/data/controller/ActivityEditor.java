package com.bnet.data.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.ActivityType;

import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditor extends Activity {

    private Spinner typeSpinner;
    private EditText countryField;
    private EditText startDay;
    private EditText startMonth;
    private EditText startYear;
    private EditText startHour;
    private EditText startMinute;
    private EditText endDay;
    private EditText endMonth;
    private EditText endYear;
    private EditText endHour;
    private EditText endMinute;
    private EditText priceField;
    private EditText descriptionField;
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
            throw new  Exception(String.format(getString(R.string.error_field_empty), text.getHint()));
    }
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-15 15:55:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        typeSpinner = (Spinner)findViewById( R.id.typeSpinner );
        typeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ActivityType.values()));
        countryField = (EditText)findViewById( R.id.countryField );
        startDay=(EditText)findViewById( R.id.startDay );
        startMonth=(EditText)findViewById( R.id.startMonth );
        startYear=(EditText)findViewById( R.id.startYear );
        startHour=(EditText)findViewById( R.id.startHour );
        startMinute=(EditText)findViewById( R.id.startMinute );
        endDay=(EditText)findViewById( R.id.endDay );
        endMonth=(EditText)findViewById( R.id.endMonth );
        endYear=(EditText)findViewById( R.id.endYear );
        endHour=(EditText)findViewById( R.id.endHour );
        endMinute=(EditText)findViewById( R.id.endMinute );
        priceField = (EditText)findViewById( R.id.priceField );
        descriptionField = (EditText)findViewById( R.id.descriptionField );
        initializeBusinessId();
    }

    private void initializeBusinessId() {
        businessIDSpinner=((Spinner) findViewById(R.id.businessSpinner));
        new AsyncTask<Void,Void,List<Business>>()
        {

            @Override
            protected List<Business> doInBackground(Void... params) {
                return RepositoriesFactory.getBusinessesRepository().getAll();
            }

            @Override
            protected void onPostExecute(List<Business> businesses) {
                ArrayList<String> st= new ArrayList<>();

                if(!businesses.isEmpty()) {
                    st.add(getString(R.string.select_business));
                    for(Business item : businesses)
                        st.add(item.getName()+"("+item.getId()+")");
                    businessIDSpinner.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, st));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"The server is unavailable at this time",Toast.LENGTH_SHORT);
                    finish();
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
                    com.bnet.shared.model.entities.Activity acti=new com.bnet.shared.model.entities.Activity();
                            acti.setType(ActivityType.valueOf(typeSpinner.getSelectedItem().toString()));
                    acti.setCountry(getText(countryField));
                    acti.setStart(DateTime.parse(String.format("%02s:%02s %02s/%02s/%02s", getText(startHour), getText(startMinute), getText(startDay), getText(startMonth), getText(startYear))));
                    acti.setEnd(DateTime.parse(String.format("%02s:%02s %02s/%02s/%02s", getText(endHour), getText(endMinute), getText(endDay), getText(endMonth), getText(endYear))));
                    acti.setPrice(Double.parseDouble(getText(priceField)));
                    acti.setDescription(getText(descriptionField));
                    String item=(String)businessIDSpinner.getSelectedItem();
                    acti.setBusinessId(Integer.parseInt(item.substring(item.lastIndexOf('(')+1).replace(")","")));
                    new AsyncTask<com.bnet.shared.model.entities.Activity,Void,Void>()
                    {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            Toast.makeText(getApplicationContext(), R.string.connecting_to_server, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected Void doInBackground(com.bnet.shared.model.entities.Activity... params) {
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
