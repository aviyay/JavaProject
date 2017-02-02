package com.bnet.data.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bnet.data.R;
import com.bnet.data.model.Updater;
import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.backend.RepositoriesFactory;
import com.bnet.data.model.entities.Account;

public class MainActivity extends Activity {

    EditText usernameField;
    EditText passwordField;

    /**
     * Starting the service after the activity was started
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startService(new Intent(this, Updater.class));
    }
    /**
     * Checks if the EditText View text is empty
     * @param etText the editText view
     * @return whether the EditText is empty or not
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    /**
     * Initialize Buttons
     */
    private void initializeButtons() {
        initializeRegisterButton();
        initializeSignInButton();
    }
    /**
     * Initialize the Register Button
     */
    private void initializeRegisterButton() {
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setView(R.layout.dialog_register);
                builder.setMessage("Register to BNet");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(R.string.register_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String username=((EditText)((AlertDialog) dialog).findViewById(R.id.usernameField)).getText().toString();
                        String password=((EditText)((AlertDialog) dialog).findViewById(R.id.passwordField)).getText().toString();
                        String confirmPassword=((EditText)((AlertDialog) dialog).findViewById(R.id.confirmPasswordField)).getText().toString();
                        if(!password.equals(confirmPassword))
                        {
                            Toast.makeText(getApplicationContext(),"Passwords doesn't match!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new AsyncTask<String,Void,Boolean>()
                        {
                            @Override
                            protected void onPostExecute(Boolean aBoolean) {
                                super.onPostExecute(aBoolean);
                                if(aBoolean) {
                                    Toast.makeText(getApplicationContext(), "Account registered successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), R.string.username_is_taken_msg, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            protected Boolean doInBackground(String... params) {
                                AccountsRepository repository = RepositoriesFactory.getAccountsRepository();
                                if(repository.getOrNull(params[0])!=null)
                                    return false;
                                repository.add(new Account(params[0], params[1]));
                                return true;
                            }

                        }.execute(username,password);
                    }
                });
                builder.create().show();
            }
        });
    }
    /**
     * Initialize the SignIn Button
     */
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

    /**
     * Make sure all of the Fields are valid
     * @return True if all of the fields are not empty
     */
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
        if(isRememberMe())
            goToMenu();
        findViews();
    }

    /**
     * Checks if the user is already log in
     * @return whether the user is already log in
     */
    private boolean isRememberMe() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return !sharedPreferences.getString("userLogIn","").equals("");
    }

    /**
     * Find the view from the layout and initialize the local variables
     */
    private void findViews() {
        usernameField=(EditText)findViewById(R.id.usernameField);
        passwordField=(EditText)findViewById(R.id.passwordField);
        initializeButtons();
    }

    /**
     * Go to Activity Menu
     */
    private void goToMenu() {
        Intent k = new Intent(this, Menu.class);
        startActivity(k);
    }

    /**
     * Sign in into the given account
     * @param item The account to sign in to
     */
    private void doSignIn(Account item) {
        if(((CheckBox)findViewById(R.id.checkBox)).isChecked()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userLogIn", item.getUsername());
            editor.apply();
        }
            Toast.makeText(getApplicationContext(), "Signed in - "+ item.getUsername(), Toast.LENGTH_SHORT).show();
        goToMenu();
    }

}
