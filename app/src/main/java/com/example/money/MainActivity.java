package com.example.money;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView total;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private SoundPool soundPool; // 声明一个SoundPool
    private int soundID; // 创建某个声音对应的音频ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        //设置让layout全屏显示
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //设置让状态栏颜色为透明
        window.setStatusBarColor(Color.TRANSPARENT);

        Button add = findViewById(R.id.addButton);
        add.setBackgroundColor(Color.parseColor("#ffa531"));

        total = findViewById(R.id.totalMoney);


        pref = PreferenceManager.getDefaultSharedPreferences(this);

        int b = pref.getInt("total", 0);
        String text = Integer.toString(b).concat("￥");
        total.setText(text);

        editor = pref.edit();

        initSound();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playSound();

                int a = pref.getInt("total", 0);
                a = a + 1;
                editor.putInt("total", a);
                String text = Integer.toString(a).concat("￥");
                total.setText(text);
                editor.apply();
            }
        });

    }

    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.mo, 1);
    }

    private void playSound() {
        soundPool.play(
                soundID,
                0.1f,      //左耳道音量【0~1】
                0.5f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1          //播放速度【1是正常，范围从0~2】
        );
    }
}