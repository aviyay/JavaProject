package com.bnet.data.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Activity for creating new Activities
 */
public class ActivityEditor extends Activity {

    private EditText countryField;
    private EditText priceField;
    private EditText descriptionField;

    /**
     * DateTime variable represent the current startDate for the corresponding button
     */
    private DateTime startDate=new DateTime();
    /**
     * DateTime variable represent the current endDate for the corresponding button
     */
    private DateTime endDate=new DateTime();

    private Button setStartDateBtn;
    private Button setEndDateBtn;

    private Spinner typeSpinner;
    private Spinner businessIDSpinner;

    /**
     * Checks if the EditText View text is empty
     * @param etText the editText view
     * @return whether the EditText is empty or not
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    /**
     * Get String of Text from EditText View
     * @param editText The EditText View
     * @return The string inside the text in the EditText
     * @throws Exception when the Text inside the EditText is empty
     */
    private String getText(EditText editText) throws Exception {
        assertField(editText);
        return editText.getText().toString();
    }

    /**
     * Assert that the EditTest is not empty
     * @param text the EditText View
     * @throws Exception when the Text inside the EditText is empty
     */
    private void assertField(EditText text) throws Exception {
        if(isEmpty(text))
            throw new  Exception(String.format(getString(R.string.error_field_empty), text.getHint()));
    }

    /**
     * Find the view from the layout and initialize the local variables
     */
    private void findViews() {
        typeSpinner = (Spinner)findViewById( R.id.typeSpinner );
        typeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ActivityType.values()));
        countryField = (EditText)findViewById( R.id.countryField );
        priceField = (EditText)findViewById( R.id.priceField );
        descriptionField = (EditText)findViewById( R.id.descriptionField );
        initializeDateButtons();
        initializeBusinessId();
        initializeSubmit();
    }

    /**
     * Initialize the Dates Buttons, and the corresponding local DateTime variables
     */
    private void initializeDateButtons() {
        Calendar cal = Calendar.getInstance();
        startDate.setYear(cal.get(Calendar.YEAR));
        startDate.setMonth(cal.get(Calendar.MONTH)+1);
        startDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
        startDate.setHour(8);
        startDate.setMinute(0);
        endDate=DateTime.parse(startDate.toString());
        endDate.setDay(endDate.getDay()+1);

        setStartDateBtn=(Button)findViewById(R.id.setStartDateBtn);
        setEndDateBtn=(Button)findViewById(R.id.setEndDateBtn);

        setStartDateBtn.setText(String.format("%s\n%s",getString(R.string.start_date), startDate.toString()));
        setEndDateBtn.setText(String.format("%s\n%s",getString(R.string.end_date), endDate.toString()));
        setStartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivityEditor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setYear(year);
                        startDate.setMonth(month+1);
                        startDate.setDay(dayOfMonth);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(ActivityEditor.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                startDate.setHour(hourOfDay);
                                startDate.setMinute(minute);
                                setStartDateBtn.setText(String.format("%s\n%s",getString(R.string.start_date), startDate.toString()));
                            }
                        }, startDate.getHour(), startDate.getMinute(), true);
                        timePickerDialog.show();
                    }
                }, startDate.getYear(), startDate.getMonth(), startDate.getDay());
                datePickerDialog.show();
            }
        });
        setEndDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ActivityEditor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setYear(year);
                        endDate.setMonth(month+1);
                        endDate.setDay(dayOfMonth);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(ActivityEditor.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                endDate.setHour(hourOfDay);
                                endDate.setMinute(minute);
                                setEndDateBtn.setText(String.format("%s\n%s",getString(R.string.end_date), endDate.toString()));
                            }
                        }, endDate.getHour(), endDate.getMinute(), true);
                        timePickerDialog.show();
                    }
                }, startDate.getYear(), startDate.getMonth(), startDate.getDay());
                datePickerDialog.show();
            }
        });
    }

    /**
     * Initialize the Business ID Spinner, Including going to the DS and getting the list of available businesses
     */
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
                    Toast.makeText(getApplicationContext(), R.string.server_unavailable,Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }.execute();
    }

    /**
     * Initialize the Submit Button, which includes sending the submitted data into the server
     */
    void initializeSubmit(){
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    com.bnet.shared.model.entities.Activity activity=new com.bnet.shared.model.entities.Activity();
                    activity.setType(ActivityType.valueOf(typeSpinner.getSelectedItem().toString()));
                    activity.setCountry(getText(countryField));
                    activity.setStart(startDate);
                    activity.setEnd(endDate);
                    activity.setPrice(Double.parseDouble(getText(priceField)));
                    activity.setDescription(getText(descriptionField));
                    String item=(String)businessIDSpinner.getSelectedItem();
                    activity.setBusinessId(Integer.parseInt(item.substring(item.lastIndexOf('(')+1).replace(")","")));
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
                            Toast.makeText(getApplicationContext(), R.string.activity_was_added, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.execute(activity);

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

    }

}
