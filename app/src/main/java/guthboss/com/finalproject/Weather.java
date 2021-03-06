package guthboss.com.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends AppCompatActivity {
    private final String ACTIVITY_NAME = "Weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Button button = (Button) findViewById(R.id.weatherBack);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Weather.this, HouseSettingMain.class);
                startActivityForResult(intent, 5);
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        TextView minTemp = (TextView) findViewById(R.id.minTemp);
        minTemp.setVisibility(View.INVISIBLE);

        TextView maxTemp = (TextView) findViewById(R.id.maxTemp);
        maxTemp.setVisibility(View.INVISIBLE);

        TextView currentTemp = (TextView) findViewById(R.id.currentTemp);
        currentTemp.setVisibility(View.INVISIBLE);

        ImageView weather = (ImageView) findViewById(R.id.imageView3);
        weather.setVisibility(View.INVISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        forecast.execute();

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private String weatherIcon;
        private Bitmap weather;
        private final String URL_STRING = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        private final String IMG_BASE_URL = "http://openweathermap.org/img/w/";

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            InputStream in = null;
            try{
                URL url = new URL(URL_STRING);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                in = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                String name = "";

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();

                    if (name.equals("temperature")) {
                        minTemp = parser.getAttributeValue(null,"min");
                        Log.i(ACTIVITY_NAME, "minTemp fetched: " + minTemp);
                        publishProgress(25);
                        maxTemp = parser.getAttributeValue(null, "max");
                        Log.i(ACTIVITY_NAME, "maxTemp fetched: " + maxTemp);
                        publishProgress(50);

                        try {
                            Thread.sleep(2000);
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        currentTemp = parser.getAttributeValue(null, "value");
                        Log.i(ACTIVITY_NAME, "currentTemp fetched: " + currentTemp);
                        publishProgress(75);
                    } else if (name.equals("weather")) {
                        weatherIcon = parser.getAttributeValue(null, "icon");
                        Log.i(ACTIVITY_NAME, "weatherIcon fetched: " + weatherIcon);
                    }
                }

            } catch (Exception e){
                Log.e(ACTIVITY_NAME, "broke");
            }


            String fileName = weatherIcon + ".png";
            Log.i(ACTIVITY_NAME, fileName);

            if(fileExistance(fileName)){
                Log.i(ACTIVITY_NAME, "Getting file from local");
                FileInputStream fis = null;
                try {

                    fis = new FileInputStream(getBaseContext().getFileStreamPath(fileName));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                weather = BitmapFactory.decodeStream(fis);
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                publishProgress(100);

            } else {
                Log.i(ACTIVITY_NAME, "Getting file from internet");
                Bitmap image = HTTPUtils.getImage(IMG_BASE_URL + fileName);
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    Log.e(ACTIVITY_NAME, "File not found");
                }
                weather = image;
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                publishProgress(100);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView minTempView = (TextView) findViewById(R.id.minTemp);
            minTempView.setText("Min: " + minTemp + "°C");
            minTempView.setVisibility(View.VISIBLE);

            TextView maxTempView = (TextView) findViewById(R.id.maxTemp);
            maxTempView.setText("Max: " + maxTemp + "°C");
            maxTempView.setVisibility(View.VISIBLE);

            TextView currentTempView = (TextView) findViewById(R.id.currentTemp);
            currentTempView.setText("Current Temperature: " + currentTemp +"°C");
            currentTempView.setVisibility(View.VISIBLE);

            ImageView weatherView = (ImageView) findViewById(R.id.imageView3);
            weatherView.setImageBitmap(weather);
            weatherView.setVisibility(View.VISIBLE);

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);


        }
    }

    private static class HTTPUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }



}