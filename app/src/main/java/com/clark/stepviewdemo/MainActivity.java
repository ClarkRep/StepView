package com.clark.stepviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StepView stepView = (StepView) findViewById(R.id.stepview);
        List<String> list = new ArrayList<>();
        list.add("第一步");
        list.add("第二步");
        list.add("第三步");
        list.add("第四步");
        stepView.setStepList(list, 1);
    }

}
