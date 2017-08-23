package com.example.josepablo.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import customList.CustomListElement;

public class WeatherList extends AppCompatActivity {

    public final static String ID_LOCATION = "com.example.josepablo.weatherapp.CITY_ID";
    private ListView lvLocations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        //Get the ListView reference from the Activity for further manipulation
        lvLocations = (ListView) findViewById(R.id.lvLocations);

        //Create a custom ArrayList to store objects from a self-made class
        final ArrayList<CustomListElement> locationList = new ArrayList<>();
        locationList.add(new CustomListElement("Mexico City", "3530597"));
        locationList.add(new CustomListElement("London", "2643743"));
        locationList.add(new CustomListElement("Toluca", "3515302"));

        //Create an ArrayAdapter and add the ArrayList elements to the adapter
        ArrayAdapter<CustomListElement> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList
        );

        //Set the adapter configuration to the ListView so that the ArrayList elements are displayed
        //on the Activity
        lvLocations.setAdapter(adapter);

        //Add a Listener to the ListView to communicate this Activity with another one called
        //WeatherDetail
        lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Get the "ID" from the selected "city"
                String test = locationList.get(position).getStId();

                //Prepare to start the new Activity and send an additional parameter as well.
                Intent intent = new Intent(WeatherList.this, WeatherDetail.class);
                intent.putExtra(ID_LOCATION, test);
                //Go to the WeatherDetail activity, sending the city's ID as an explicit parameter.
                startActivity(intent);
            }
        });

    }


}
