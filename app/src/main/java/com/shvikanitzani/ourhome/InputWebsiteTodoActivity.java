package com.shvikanitzani.ourhome;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shvikanitzani.ourhome.model.WebsiteTodo;
import com.shvikanitzani.ourhome.util.MyHttpHandler;


public class InputWebsiteTodoActivity extends ActionBarActivity {

    static EditText editTextSubject=null;
    static EditText getEditTextDetails=null;
    Button submitBtn = null;
    private static MyHttpHandler myHttpHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_website_todo);
        myHttpHandler = new MyHttpHandler(this);
        findViewsById();
        setSubmitButtonListener();
    }
    private void findViewsById()
    {
        editTextSubject = (EditText)findViewById(R.id.edittext_website_todo_subject);
        getEditTextDetails = (EditText)findViewById(R.id.edittext_website_todo_details);
        submitBtn = (Button)findViewById(R.id.button_website_todo_submit);
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

    private  void HandleButtonClick()
    {
        try
        {
            String subject = editTextSubject.getText().toString();
            String details = getEditTextDetails.getText().toString();
            if (subject.isEmpty() || details.isEmpty())
            {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            }
            else
            {
                WebsiteTodo websiteTodo = new WebsiteTodo(subject, details);
                myHttpHandler.createWebsiteTodoItem(websiteTodo);
            }
        }
        catch (Exception e)
        {

        }
        finally {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_website_todo, menu);
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
}
