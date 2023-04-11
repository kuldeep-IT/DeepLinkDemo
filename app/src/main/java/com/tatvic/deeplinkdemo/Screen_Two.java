package com.tatvic.deeplinkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Set;

public class Screen_Two extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_two);

        TextView textView = findViewById(R.id.tvScreen2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_Two.this, Screen_Three.class);
                startActivity(intent);
            }
        });
        Uri uri = getIntent().getData();
        Log.d("SecondActivity URI: ", String.valueOf(uri));

        if (uri != null) {
            // if the uri is not null then we are getting
            // the path segments and storing it in list.
            //List<String> parameters = uri.getPathSegments();

            // after that we are extracting string
            // from that parameters.
            //String param = parameters.get(parameters.size() - 1);

            // on below line we are setting that string
            // to our text view which we got as params.
            //textView.setText(param);
            Set<String> parameters = uri.getQueryParameterNames();
            String utm_source = uri.getQueryParameter("utm_source");
            String utm_medium = uri.getQueryParameter("utm_medium");

            Log.d("Parameters: ", String.valueOf(utm_source + "  " + utm_medium));
        }

    }
}