package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

public class Home extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.qrcode).setOnClickListener(this);
        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qrcode:
                startActivity(new Intent(this, QRcode.class));
                break;
            case R.id.start:
                startActivity(new Intent(this, Game.class));
                break;
            case R.id.more:
                break;
        }
    }
}
