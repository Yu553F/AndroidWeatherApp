package com.example.josepablo.weatherapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class WeatherDetail extends AppCompatActivity {
    //This is a personal OpenWeatherApp key generated with a personal account for development
    //purposes. You can also get one by creating an account at OpenWeatherApp
    private static final String DEV_KEY = "127d370b9415ea1c32da57b23db54624";
    private String stWeatherId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        //Get the parameter sent by the WeatherList activity.
        Intent intent = getIntent();
        String stLocId = intent.getStringExtra(WeatherList.ID_LOCATION);

        //Prepare an HTTP request to get necessary data from OpenWeatherApp according to its API
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/weather?id="+stLocId+"&appid="+WeatherDetail.DEV_KEY;

        //Get the Activity's TextViews for further manipulation
        final TextView tvLocation = (TextView) findViewById(R.id.tvLocation);
        final TextView tvWeather = (TextView) findViewById(R.id.tvWeather);
        final TextView tvTemperature = (TextView) findViewById(R.id.tvTemperature);

        //Through the Volley Request, format the response as a JSONObject
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //If the request is successful, do the following:
                try{
                    //Get the necessary parameters from the JSON response to display them in the
                    //Activity
                    String stWeather = response.getJSONArray("weather").getJSONObject(0).getString("main");
                    String stTemperature = Math.round(response.getJSONObject("main").getInt("temp") - 273.15) + "°C";
                    String stLocation = response.getString("name");

                    //This parameter is used to set a background image inside the activity
                    stWeatherId = response.getJSONArray("weather").getJSONObject(0).getInt("id") + "";

                    tvLocation.setText(stLocation);
                    tvTemperature.setText(stTemperature);
                    tvWeather.setText(stWeather);

                    //Get the appropiate name depending on the weather ID returned by OpenWeatherApp
                    String stImgLoc = getWeatherType(stWeatherId);
                    if(stImgLoc != null || !stImgLoc.isEmpty() || !stImgLoc.equals("default")){
                        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
                        //Load specific image from the Assets folder
                        try {
                            InputStream stream = getAssets().open(stImgLoc + ".jpg");

                            Drawable dImage = Drawable.createFromStream(stream, null);
                            ivBackground.setImageDrawable(dImage);
                            ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } catch (IOException ioe){
                            Toast.makeText(getApplicationContext(), "Failed to Load Image!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch(JSONException jse){
                    Toast.makeText(getApplicationContext(), "Error Handling RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection to OpenWeatherApp failed!", Toast.LENGTH_SHORT).show();
            }
        });
        //Execute Request by adding it to a Queue
        queue.add(jsObjRequest);

    }

    /**
     * This method is used in conjunction with the weather[n].id parameter in the JSON response,
     * which enables a simpler way to categorize weather types and thus reduce the number of images
     * needed to represent all the weather types specified in the OpenWeatherApp documentation
     * @param String from the weather id of the specified "n" Day
     * @return Returns a simple String from the main category to use for loading the background
     * image from the assets folder
     */
    private static String getWeatherType (String w_type) {
        if (w_type != null || w_type != ""){
            switch(w_type.charAt(0)){
                case '2':
                    return "thunderstorm";
                case '3':
                    return "drizzle";
                case '5':
                    return "rain";
                case '6':
                    return "snow";
                case '7':
                    return "atmosphere";
                case '8':
                    return (w_type == "800") ? "clear" : "clouds";
                case '9':
                    return (w_type.charAt(1) == '0') ? "extreme" : "additional";
                default:
                    return "default";
            }
        } else {
            return null;
        }
    }
}
