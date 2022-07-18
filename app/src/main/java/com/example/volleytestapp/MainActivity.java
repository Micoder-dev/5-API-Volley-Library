package com.example.volleytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue mRequestQueue;
    RequestQueue mRequestQueueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueueArray = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://joke.deno.dev/",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("RESPONSEVALIDDATA", response + "");

                        try {

                            int jokeID = response.getInt("id");
                            Toast.makeText(MainActivity.this, jokeID + "", Toast.LENGTH_SHORT).show();

                            String setup = response.getString("setup");
                            Toast.makeText(MainActivity.this, setup + "", Toast.LENGTH_SHORT).show();

                            String punchLine = response.getString("punchline");
                            Toast.makeText(MainActivity.this, punchLine + "", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Something went wrong, please check your internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("RESPONSEERROR", error.getMessage());

                    }
                });

        mRequestQueue.add(jsonObjectRequest);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://joke.deno.dev/type/general/10",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("ARRAYRESPONSE", response + "");

                        for (int index = 0; index < response.length(); index++) {

                            try {

                                JSONObject jokeJsonObject = response.getJSONObject(index);
                                Log.i("JOKE",
                                        jokeJsonObject.getString("setup")
                                                + " - "
                                                + jokeJsonObject.getString("punchline"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("ARRAYERROR", error.getMessage());

                    }
                });
        mRequestQueueArray.add(jsonArrayRequest);

    }
}