package com.shvikanitzani.ourhome.util;

//import android.content.res.Resources;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.shvikanitzani.ourhome.MainMenuActivity;
import com.shvikanitzani.ourhome.R;
import com.shvikanitzani.ourhome.WebsiteTodosListActivity;
import com.shvikanitzani.ourhome.model.WebsiteTodo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 15/05/2015.
 */
public class MyHttpHandler {
    private Context ctx;
    private Resources res;


    private MyHttpHandler(){

    }

    public MyHttpHandler(Context ctx)
    {
        this.ctx = ctx;
        res = ctx.getResources();
    }

    public void getWebsiteTodosList ()
    {
        String url = res.getString(R.string.url_shvika_nitzani_web_api_get_todos);
        new AsyncHttpGetWebsiteTodos(url).execute();//android throws exception on trying to do networking from main ('UI') thread.
    }

    public void createWebsiteTodoItem(WebsiteTodo todo)
    {

        new AsyncHttpPostWebsiteTodo(todo).execute();
    }

    public void performLogin(String username, String password)
    {
        String url = res.getString(R.string.url_shvika_nitzani_login);
        new AsyncHttpGetLogin(url, username, password).execute();
    }

    /*private  String myHttpGet(String urlString)
    {
        String responseString = null;
        HttpURLConnection httpUrlConnection = null;
        try
        {
            URL url = createUrlFromString(urlString);
            httpUrlConnection = httpUrlOpenConnection(url);
            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedInputStream in = new BufferedInputStream(inputStream);
            responseString = readBufferedInputString(in);
            int resposnseCode = httpUrlConnection.getResponseCode();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (httpUrlConnection != null)
                httpUrlConnection.disconnect();
        }
        return responseString;
    }*/




    private URL createUrlFromString(String urlString)
    {
        URL url = null;
        try {
             url = new URL(urlString);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        return  url;
    }

    private  HttpURLConnection httpUrlOpenConnection (URL url) {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpURLConnection;
    }

    private String readBufferedInputString(BufferedInputStream stream)
    {
        int numRead;
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outString = new ByteArrayOutputStream();
        try{
            while ((numRead = stream.read(buffer)) != -1) {
                outString.write(buffer, 0, numRead);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new String(outString.toByteArray());
    }

    //html parsing
    /*private ArrayList<WebsiteTodo> parseWebsiteTodosResponse(String html)
    {
        ArrayList<WebsiteTodo> todos = new ArrayList<WebsiteTodo>();
        String tagHtmlTable = res.getString(R.string.html_tag_table);
        String tagHtmlTableRow   = res.getString(R.string.html_tag_table_row);


        Document doc   = Jsoup.parse(html);
        Element  table = doc.select(tagHtmlTable).first();
        Elements tableRows = table.select(tagHtmlTableRow);

        for (Element tableRow : tableRows)
        {
            Elements rowData = tableRow.select(res.getString(R.string.html_tag_table_data));
            if (rowData.size() > 0)
            {
                String title = rowData.get(2).text();
                String details = rowData.get(3).text();
                WebsiteTodo todo = new WebsiteTodo(title, details);
                todos.add(todo);
            }
        }
        return  todos;
    }*/


    private void StartWebsiteTodosListActivity(ArrayList<WebsiteTodo> todos)
    {
        final Intent intentStartTodosListActivity = new Intent(ctx, WebsiteTodosListActivity.class);
        intentStartTodosListActivity.putExtra(res.getString(R.string.key_todos), todos);
        ctx.startActivity(intentStartTodosListActivity);
    }
    private void startMainNavigationActivity()
    {
        final Intent intentStarMainMenuActivity = new Intent(ctx, MainMenuActivity.class);
        ctx.startActivity(intentStarMainMenuActivity);
    }




    abstract class AsyncHttpGet extends AsyncTask<Void, Void, String>{

        private  String url;
        protected int responseCode;

        abstract void readResponse(HttpURLConnection httpURLConnection);

        public AsyncHttpGet(String url)
        {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String response = myHttpGet(url);
            return response;
        }

        private  String myHttpGet(String urlString)
        {
            String responseString = null;
            HttpURLConnection httpUrlConnection = null;
            try
            {
                URL url = createUrlFromString(urlString);
                httpUrlConnection = httpUrlOpenConnection(url);
                responseCode = httpUrlConnection.getResponseCode();
                readResponse(httpUrlConnection);

                /*String cookie = httpUrlConnection.getRequestProperty(HttpNames.HEADER_FIELD_SET_COOKIE);
                if (cookie != null)
                {
                    Thread.sleep(500);
                }*/
                /*InputStream inputStream = httpUrlConnection.getInputStream();
                BufferedInputStream in = new BufferedInputStream(inputStream);
                responseString = readBufferedInputString(in);*/
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (httpUrlConnection != null)
                    httpUrlConnection.disconnect();
            }
            return responseString;
        }
    }
    class AsyncHttpGetWebsiteTodos extends AsyncHttpGet {

        ArrayList<WebsiteTodo> todos = null;
        @Override
        void readResponse(HttpURLConnection httpURLConnection) {
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedInputStream in = new BufferedInputStream(inputStream);
                String s = readBufferedInputString(in);
                todos = parseWebsiteTodosJsonResponse(s);
            }
            catch (Exception e)
            {
                Log.e("Exception", "Exception during reading todos list response");
            }
        }

        public AsyncHttpGetWebsiteTodos(String url) {
            super(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //ArrayList<WebsiteTodo> todos = parseWebsiteTodosJsonResponse(s); //getting json
                StartWebsiteTodosListActivity(todos);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //Json returned from Asp.NET Web API.
        private ArrayList<WebsiteTodo> parseWebsiteTodosJsonResponse(String jsonResponseString)
        {
            ArrayList<WebsiteTodo> todoList = new ArrayList<>();

            try{
                JSONArray rootArray = new JSONArray(jsonResponseString);
                for (int i=0; i<rootArray.length(); i++)
                {
                    JSONObject todoItem = rootArray.getJSONObject(i);
                    String subject = todoItem.getString("subject");
                    String details = todoItem.getString("details");

                    WebsiteTodo todo = new WebsiteTodo(subject, details);
                    todoList.add(todo);
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return  todoList;
        }
    }

    class AsyncHttpGetLogin extends AsyncHttpGet
    {
        private String username = null;
        private String password = null;
        private  String loginCookie = null;
        private String requestVerificationToken = null;

        @Override
        void readResponse(HttpURLConnection httpURLConnection) {
            try {
                loginCookie = httpURLConnection.getHeaderField(HttpNames.HEADER_FIELD_SET_COOKIE);
                String []tokens = loginCookie.split(HttpNames.COOKIE_DATA_SEPARATOR);
                requestVerificationToken = tokens[0];
            }
            catch (Exception e)
            {

            }
        }

        public AsyncHttpGetLogin (String url, String username, String password)
        {
            super(url);
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                 new AsyncHttpPostLogin(username, password, requestVerificationToken).execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    abstract class AsyncHttpPost extends AsyncTask<Void, Void, String>{

        private  HttpURLConnection httpUrlConnection = null;
        private  final static String POST_COMMAND = "POST";
        private  String messageBody = null;

        @Override
        protected String doInBackground(Void... params)
        {
            String response = httpPost();
            return response;
        }

        public abstract String getUrl();
        public abstract ArrayList<String>  getHeaderPropertyNames();
        public abstract ArrayList<String>  getHeaderPropertyValues();
        public abstract String getMessageBody();
        abstract void readResponse(HttpURLConnection httpURLConnection);


        private  String httpPost( )
        {
            String responseString = null;

            try
            {
                String urlAsString = getUrl();
                URL url = createUrlFromString(urlAsString);
                httpUrlConnection = httpUrlOpenConnection(url);

                httpUrlConnection.setRequestMethod(POST_COMMAND);
                setMessageHeaderProperties();
                messageBody = getMessageBody();
               // httpUrlConnection.setRequestProperty(MSG_BODY_LENGTH, "" + Integer.toString(messageBody.getBytes().length));

                httpUrlConnection.setChunkedStreamingMode(0); //performance recommendation
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);

                //Send request

                DataOutputStream wr = new DataOutputStream( httpUrlConnection.getOutputStream ());
                wr.writeBytes(messageBody);
                wr.flush();
                wr.close();
                //response
                readResponse(httpUrlConnection);
                /*InputStream inputStream = httpUrlConnection.getInputStream();
                BufferedInputStream in = new BufferedInputStream(inputStream);
                responseString = readBufferedInputString(in);*/


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (httpUrlConnection != null)
                    httpUrlConnection.disconnect();
            }
            return responseString;
        }

        private void setMessageHeaderProperties()
        {
            ArrayList<String> headerPropertyNames = getHeaderPropertyNames();
            ArrayList<String> headerPropertyValues = getHeaderPropertyValues();
            if (headerPropertyNames != null && headerPropertyValues != null) {
                int namesArraySize = headerPropertyNames.size();
                int valuesArraySize = headerPropertyValues.size();
                assert (namesArraySize == valuesArraySize);

                for (int i = 0; i < namesArraySize; i++) {
                    httpUrlConnection.addRequestProperty(headerPropertyNames.get(i), headerPropertyValues.get(i));
                }
            }
        }

    }

    class AsyncHttpPostWebsiteTodo extends AsyncHttpPost {

        private String messageBody = null;

        public AsyncHttpPostWebsiteTodo(WebsiteTodo todoItem) {
            messageBody = "{\"Subject\":" + "\"" + todoItem.title + "\"," + "\"Details\":" + "\"" + todoItem.details +"\"}";
        }

        @Override
        public String getUrl() {
            return  res.getString(R.string.url_shvika_nitzani_web_api_get_todos);
        }

        @Override
        public ArrayList<String> getHeaderPropertyNames() {
            ArrayList<String> names = new ArrayList<>();
            names.add("content-type");
            names.add("Content-Length");
            return  names;
        }

        @Override
        public ArrayList<String> getHeaderPropertyValues() {
            ArrayList<String> values = new ArrayList<>();
            values.add("application/json");
            values.add("" + Integer.toString(messageBody.getBytes().length));

            return  values;
        }

        @Override
        public String getMessageBody() {
            return messageBody;
        }

        @Override
        void readResponse(HttpURLConnection httpURLConnection) {

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                Log.i("http execution","AsyncHttpPostWebsiteTodo onPostExecute");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    class AsyncHttpPostLogin extends AsyncHttpPost {
        private String requestVerificationToken = null;
        private List<String> headerCookies = null;
        private String username = null;
        private String password = null;
        private Map<String, List<String>> headerProperties =null;
        private List<String> property = null;
        private int returnCode;
        private String messageBody = null;
        private String responseBody = null;

        public AsyncHttpPostLogin(String username, String password,String requestVerificationToken)
        {
            this.username = username;
            this.password = password;
            this.requestVerificationToken = requestVerificationToken;
            messageBody =  /*"__RequestVerificationToken=" + requestVerificationToken +*/
                            "UserName=" + username +
                            "&Password=" + password +
                            "&RememberMe=false";
        }


        @Override
        public String getUrl() {
            return  res.getString(R.string.url_shvika_nitzani_login);
        }

        @Override
        public ArrayList<String> getHeaderPropertyNames() {
            ArrayList<String> names = new ArrayList<>();
            names.add(HttpNames.HEADER_FIELD_COOKIE);
            names.add(HttpNames.HEADER_FIELD_CONTENT_LENGTH);
            names.add(HttpNames.HEADER_FIELD_CONTENT_TYPE);
            return  names;
        }

        @Override
        public ArrayList<String> getHeaderPropertyValues() {
            ArrayList<String> values = new ArrayList<>();
            values.add(requestVerificationToken);
            values.add("" + Integer.toString(messageBody.getBytes().length));
            values.add(HttpNames.CONTENT_TYPE_APP_WWW_FORM_URLENCODED);
            return  values;
        }

        @Override
        public String getMessageBody() {
            return  messageBody;
        }

        @Override
        void readResponse(HttpURLConnection httpURLConnection) {
            try
            {
                headerProperties = httpURLConnection.getHeaderFields();
                returnCode = httpURLConnection.getResponseCode();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedInputStream in = new BufferedInputStream(inputStream);
                responseBody = readBufferedInputString(in);
            }
            catch (Exception e){
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();

            }
            // headerCookies = headerProperties.get
            /*String []tokens = loginCookie.split(HttpNames.COOKIE_DATA_SEPERATOR);
            requestVerificationToken = tokens[0];*/
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                startMainNavigationActivity();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
