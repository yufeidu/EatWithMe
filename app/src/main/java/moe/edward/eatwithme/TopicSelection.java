package moe.edward.eatwithme;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static android.support.v7.appcompat.R.styleable.View;

public class TopicSelection extends AppCompatActivity {
    URL urlGet;
    String jsonString;
    LinearLayout layout;
    int selected, mode;
    ArrayList<Button> topics;
    RadioGroup group;
    boolean hasText = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        layout = (LinearLayout)findViewById(R.id.linearLayout_Inside_Topic);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //updateEntryList(layout);
        //new GetData().execute();
        //topics = new ArrayList<Button>();
        EditText text = (EditText)findViewById(R.id.editText_topic);
        group = (RadioGroup) findViewById(R.id.radioGroup_Topic);
        text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if(s.toString().equals("")){
                    ((Button) findViewById(R.id.button_topic)).setEnabled(false);
                    hasText = false;
                }else{
                    if(group.getCheckedRadioButtonId()!=-1) {
                        ((Button) findViewById(R.id.button_topic)).setEnabled(true);
                    }
                    hasText = true;
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio1_Topic){
                    mode = 2;
                }else{
                    mode = 1;
                }
                if(hasText) {
                    ((Button) findViewById(R.id.button_topic)).setEnabled(true);
                }

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        new GetData().execute();
    }

    @Override
    protected void onPause(){
        super.onPause();
        layout.removeAllViews();
    }

    //map data structure: {text,X,Y}
    private void updateEntryList(LinearLayout view, ArrayList<String[]> map) {
        for(String[] entry : map){
            Button button = new Button(this);
            //topics.add(button);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(entry[0]);
            if(Integer.parseInt(entry[2])==1){
                button.setTextColor(Color.GREEN);
            }else{
                button.setTextColor(Color.RED);
            }
            final int id = Integer.parseInt(entry[1]);
            button.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    selected = id;
                    new PostData().execute();

                }
            });
            button.setTransformationMethod(null);
            view.addView(button);
        }
    }



    public void onClickButton(View e){
        String text = ((EditText)findViewById(R.id.editText_topic)).getText().toString();
        Intent intent = new Intent(TopicSelection.this, Map.class);
        intent.putExtra("mode", 2);
        intent.putExtra("type",mode);
        intent.putExtra("data", text);
        startActivity(intent);
    }


    //Cite: http://www.codexpedia.com/android/asynctask-and-httpurlconnection-sample-in-android/
    private class GetData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String returnStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://54.200.211.247:8787/generator");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                returnStr = buffer.toString();
                return returnStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jsonString = s;
            ArrayList<String[]> result = new ArrayList<String[]>();
            try {
                JSONObject json = new JSONObject(jsonString);
                int counter = 0;
                while (!json.isNull("item_" + counter)) {
                    JSONArray array = json.getJSONArray("item_" + counter);
                    result.add(new String[]{(String) array.get(1), Integer.toString((Integer) array.get(0)),Integer.toString((Integer) array.get(2))});
                    Log.d("item_"+counter,(String)array.get(1));
                    counter++;
                }
                updateEntryList(layout,result);
            }catch(Exception e){
                Log.d("ERROR",e.toString());
            }
            Log.i("json", s);
        }
    }

    //Cite: http://www.codexpedia.com/android/asynctask-and-httpurlconnection-sample-in-android/
    private class PostData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String returnStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://54.200.211.247:8787/generator");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("pID",selected+"");
                String param = builder.build().getEncodedQuery();
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(param);
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                returnStr = buffer.toString();
                return returnStr;
            } catch (Exception e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int lID, mID;
            jsonString = s;
            ArrayList<String[]> result = new ArrayList<String[]>();
            try {
                JSONObject json = new JSONObject(jsonString);
                lID = json.getInt("lID");
                mID = json.getInt("mID");
                Intent intent = new Intent(TopicSelection.this, Map.class);
                intent.putExtra("mode",1);
                intent.putExtra("entry", new int[]{lID, mID});
                startActivity(intent);
                Log.d("lID",lID+"");
            }catch(Exception e){
                Log.d("ERROR",e.toString());
            }



            Log.i("json", s);
        }
    }
}
