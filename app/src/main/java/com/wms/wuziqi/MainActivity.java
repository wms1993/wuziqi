package com.wms.wuziqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Wuziqi mWuziqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWuziqi = (Wuziqi) findViewById(R.id.wuziqi);
    }

    public void restart(View view) {
        mWuziqi.restart();
    }
}
