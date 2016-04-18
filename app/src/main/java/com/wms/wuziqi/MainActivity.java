package com.wms.wuziqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Wuziqi mWuziqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWuziqi = (Wuziqi) findViewById(R.id.wuziqi);
        mWuziqi.setOnGameOverListener(new Wuziqi.OnGameOverListener() {
            @Override
            public void gameOver(boolean isWhite) {
                if (isWhite) {
                    Toast.makeText(MainActivity.this, "白棋胜", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "黑棋胜", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void restart(View view) {
        mWuziqi.restart();
    }
}
