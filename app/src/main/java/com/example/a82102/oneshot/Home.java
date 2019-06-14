package com.example.a82102.oneshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class Home extends BaseActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    PopupMenu submenu;
    MenuInflater menuInflater;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        submenu = new PopupMenu(this, findViewById(R.id.more));
        menuInflater = submenu.getMenuInflater();
        menu = submenu.getMenu();
        menuInflater.inflate(R.menu.home_submenu, menu);
        submenu.setOnMenuItemClickListener(this);

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
                submenu.show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.point:
                startActivity(new Intent(this, PointActivity.class));
                return true;
            case R.id.logout:
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        redirectLoginActivity();
                    }
                });
                return true;
                default:
                    break;
        }
        return false;
    }
}
