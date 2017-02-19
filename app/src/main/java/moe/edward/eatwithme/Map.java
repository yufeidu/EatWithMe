package moe.edward.eatwithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ortiz.touch.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Map extends AppCompatActivity {
    int[] pos = new int[2];
    int mode;
    boolean set = false;
    ImageView pin;
    Button button;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TouchImageView imageView = (TouchImageView)findViewById(R.id.image);
//        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        imageView.startAnimation(zoom);
        pin = (ImageView)findViewById(R.id.imagePin_Map);
        button = ((Button)findViewById(R.id.button_Map));
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",0);
        if(mode==1){
            button.setVisibility(View.INVISIBLE);
            int[] pos = intent.getIntArrayExtra("entry");
            Bitmap myimage = BitmapFactory.decodeResource(getResources(),R.drawable.dougl);
            Bitmap tempMap = Bitmap.createBitmap(myimage.getWidth(), myimage.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(tempMap);
            canvas.drawBitmap(myimage,0,0,null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect((float)pos[0]-50,(float)pos[1]-50,(float)pos[0]+50,(float)pos[1]+50,paint);
            Log.d("Rect","\n"+pos[0]+" "+pos[1]+"\n");
            imageView.setImageBitmap(tempMap);
        }else{
            button.setVisibility(View.VISIBLE);
            text=intent.getStringExtra("data");
        }
    }

    public void setPos(int x, int y, int xscreen, int yscreen){
        if(mode == 2) {
            pos[0] = x;
            pos[1] = y;
            set = true;
            button.setEnabled(true);

            RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(pin.getLayoutParams());
            marginLayoutParams.setMargins(xscreen, yscreen, 0, 0);
            pin.setLayoutParams(marginLayoutParams);
            pin.setVisibility(View.VISIBLE);
        }
    }

    public void hidePin(){
        pin.setVisibility(View.INVISIBLE);
        button.setEnabled(false);
    }

    public void submit(View v){
        new PostData().execute();
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
                urlConnection.setRequestMethod("PUT");
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("topic",text)
                                                        .appendQueryParameter("uID","0")
                                                        .appendQueryParameter("lID",pos[0]+"")
                                                        .appendQueryParameter("mlID",pos[1]+"");
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
        }
    }
}
