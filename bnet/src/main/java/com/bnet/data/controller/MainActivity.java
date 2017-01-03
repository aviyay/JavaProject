package com.bnet.data.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.Updater;
import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.data.model.datasource.PhpAccountsRepository;
import com.bnet.data.model.entities.Account;

import java.util.List;

public class MainActivity extends Activity {

    EditText usernameField;
    EditText passwordField;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        startService(new Intent(this, Updater.class));
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void initializeButtons()
    {
        initializeRegisterButton();
        initializeSignInButton();
    }

    private void initializeRegisterButton() {
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFields())
                    return;
                new AsyncTask<String,Void,Boolean>()
                {
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if(aBoolean)
                            Toast.makeText(getApplicationContext(), "TEMP: Account registered successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), R.string.username_is_taken_msg, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    protected Boolean doInBackground(String... params) {
                        AccountsRepository repository = RepositoriesFactory.getAccountsRepository();
                        if(repository.getOrNull(params[0])!=null)
                            return false;
                        repository.add(new Account(params[0].toString(), params[1]));
                        return true;
                    }

                }.execute(usernameField.getText().toString(),passwordField.getText().toString());

            }
        });
    }

    private void initializeSignInButton() {
       findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFields())
                    return;
                new AsyncTask<String,Void,Account>()
                {
                    @Override
                    protected Account doInBackground(String... params) {
                        Account account = RepositoriesFactory.getAccountsRepository().getOrNull(params[0]);
                        if (account != null)
                            if (account.getPassword().equals(params[1])) {
                                return account;
                            }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Account account) {
                        super.onPostExecute(account);
                        if(account!=null)
                            doSignIn(account);
                        else
                            Toast.makeText(getApplicationContext(), R.string.password_or_username_incorrect,Toast.LENGTH_SHORT).show();

                    }
                }.execute(usernameField.getText().toString(),passwordField.getText().toString());
            }
        });
    }

    private boolean validateFields() {
        if(isEmpty(usernameField)||isEmpty(passwordField)) {
            Toast.makeText(getApplicationContext(), R.string.empty_fields_msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        if(!sharedPreferences.getString("userLogIn","").equals(""))
            goToMenu();
        usernameField=(EditText)findViewById(R.id.usernameField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        initializeButtons();
    }

    private void goToMenu() {
        Intent k = new Intent(this, Menu.class);
        startActivity(k);
    }

    private void doSignIn(Account item) {
        if(((CheckBox)findViewById(R.id.checkBox)).isChecked()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userLogIn", item.getUsername());
            editor.commit();
        }
            Toast.makeText(getApplicationContext(), "TEMP: Signed in - "+ item.getUsername(), Toast.LENGTH_SHORT).show();
        goToMenu();
    }

}
