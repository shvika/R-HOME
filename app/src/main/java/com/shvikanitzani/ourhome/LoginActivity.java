package com.shvikanitzani.ourhome;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shvikanitzani.ourhome.util.MyHttpHandler;


public class LoginActivity extends ActionBarActivity {
    static Button submitBtn = null;
    static EditText editTextUsername=null;
    static EditText editTextPassword=null;
    static MyHttpHandler myHttpHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        if (myHttpHandler == null)
            myHttpHandler = new MyHttpHandler(this);
        setSubmitButtonListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findViewsById()
    {
        editTextUsername = (EditText)findViewById(R.id.edit_text_login_username);
        editTextPassword = (EditText)findViewById(R.id.edit_text_login_password);
        submitBtn = (Button)findViewById(R.id.login_submit_button);
    }

    private  void setSubmitButtonListener()
    {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleButtonClick();
            }
        });
    }

    private void HandleButtonClick() {

        String userName = editTextUsername.getText().toString();
        String passWord = editTextPassword.getText().toString();

        Toast.makeText(this, userName + passWord,
                Toast.LENGTH_LONG).show();
        try
        {
            myHttpHandler.performLogin(userName, passWord);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Login excepcion", Toast.LENGTH_LONG ).show();
        }
        finally {
           // startMainNavigationActivity();
        }
    }


}
