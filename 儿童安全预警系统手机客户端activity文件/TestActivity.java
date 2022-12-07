package com.example.ok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ok.One_net_json;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button test_button=findViewById(R.id.test_button);
        TextView test_text_view=findViewById(R.id.test_text_view);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                            Data data=Data.get_data("T27H55HT26AC0AN0GV323BV176BPS94P123.456789P123.456789E");
                            test_text_view.setText(data.getTemperture());


            }
        });
    }
}