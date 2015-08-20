package com.shvikanitzani.ourhome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shvikanitzani.ourhome.util.MyHttpHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainMenuActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //private CharSequence mTitle;
    private String[] mDrawerLabels=null;


    private static MyHttpHandler myHttpHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        Resources res = getResources();
        mDrawerLabels = res.getStringArray(R.array.navigation_drawer_main_options);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

       myHttpHandler = new MyHttpHandler(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, NavigationMainFragment.newInstance(position + 1))
                .commit();
        Toast.makeText(this, "onNavigationDrawerItemSelected", Toast.LENGTH_LONG).show();

        HandleDrawerEventSelection(position);

    }

    private void HandleDrawerEventSelection(int position)
    {
        switch (position)
        {
            case 0:
                try {
                    myHttpHandler.getWebsiteTodosList();
                }catch (Exception e)
                {
                    Log.e("Drawer", "option get website todos failed");
                }
                break;
            case 1:
                try {
                    //myHttpHandler.createWebsiteTodoItem(new WebsiteTodo("Android App subject", "Android App details"));
                    final Intent intentInputWebsiteTodoActivity = new Intent(this, InputWebsiteTodoActivity.class);
                    startActivity(intentInputWebsiteTodoActivity);
                }catch (Exception e)
                {
                    Log.e("Drawer", "option new website todo failed");
                }
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    public void onSectionAttached(int number) {
       // mTitle = mDrawerLabels[number - 1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Website Todos button pressed",
                Toast.LENGTH_LONG).show();
        MyHttpHandler httpHandler = new MyHttpHandler(this);
        httpHandler.getWebsiteTodosList();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class NavigationMainFragment extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static NavigationMainFragment newInstance(int sectionNumber) {
            NavigationMainFragment fragment = new NavigationMainFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public NavigationMainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation_main, container, false);


            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((MainMenuActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }
    }

}
