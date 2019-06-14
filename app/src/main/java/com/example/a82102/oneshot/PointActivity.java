package com.example.a82102.oneshot;

import android.app.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class PointActivity extends Activity {
    String[] pubs = {"그냥 갈 수 없잖아", "인생술집", "아는형네"};
    int[] pubImage = {R.drawable.point_geugal, R.drawable.point_empty, R.drawable.point_empty};
    ListView pubList;
    ArrayAdapter adapter;
    AlertDialog.Builder builder;
    AlertDialog alert;
    LayoutInflater inflater;
    View layout;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        pubList = findViewById(R.id.list_pub);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pubs);
        pubList.setAdapter(adapter);
        builder = new AlertDialog.Builder(this);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.alert_pubs, (ViewGroup) findViewById(R.id.layout_root));
        image = layout.findViewById(R.id.image_point);
        builder.setView(layout);
        alert = builder.create();
        pubList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image.setImageResource(pubImage[position]);
                alert.show();
            }
        });

    }
}
