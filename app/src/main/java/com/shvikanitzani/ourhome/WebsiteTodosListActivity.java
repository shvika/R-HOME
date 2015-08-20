package com.shvikanitzani.ourhome;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shvikanitzani.ourhome.adapters.WebsiteTodosListAdapter;
import com.shvikanitzani.ourhome.model.WebsiteTodo;

import java.util.ArrayList;


public class WebsiteTodosListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_todos_list);

        try
        {


            String keyString = getResources().getString(R.string.key_todos);
            Intent i = getIntent();
            Bundle bundle = i.getExtras();
            ArrayList<WebsiteTodo> todosArray = (ArrayList<WebsiteTodo>) bundle.get(keyString);
            startAdapter(todosArray);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

        }
    }

   /* private  void startDummyAdapter()
    {
        String [] strings = {"item 1", "item 2", "item 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        getListView().setAdapter(adapter);
    }*/

    private void startAdapter( ArrayList<WebsiteTodo> todosArray)
    {

        // Construct the data source
        /*ArrayList<WebsiteTodo> todosArray = new ArrayList<WebsiteTodo>();
        for (int i=0; i<10; i++)
        {
            WebsiteTodo todo = new WebsiteTodo("dummy todo ."+i, "dummy todo details No. "+ i);
            todosArray.add(todo);
        }*/
        // Create the adapter to convert the array to views
        WebsiteTodosListAdapter adapter = new WebsiteTodosListAdapter(this, todosArray);
        // Attach the adapter to a ListView
        ListView listView = getListView();
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_website_todos, menu);
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
