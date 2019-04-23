package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Home extends Activity implements View.OnClickListener{

    ImageButton qr, hm, mr, st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        qr = (ImageButton) findViewById(R.id.qrcode);
        hm = (ImageButton) findViewById(R.id.home);
        mr = (ImageButton) findViewById(R.id.more);
        st = (ImageButton) findViewById(R.id.start);

        findViewById(R.id.qrcode).setOnClickListener(this);
        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.a82102.oneshot", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qrcode :
                startActivity(new Intent(this, QRcode.class));
                break;
            case R.id.start :
                startActivity(new Intent (this, Game.class));
                break;

        }
    }
}
