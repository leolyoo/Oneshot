package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.kakaologin).setOnClickListener(this);
        findViewById(R.id.nonmember).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kakaologin:
                startActivity(new Intent(this, Home.class));
                break;
            case R.id.nonmember:
                startActivity(new Intent(this, NmHome.class));
                break;
        }
    }
}
