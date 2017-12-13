package com.example.administrator.mytest1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnbaidu = (Button)findViewById(R.id.button_baidu);
        btnbaidu.setOnClickListener(new View.OnClickListener(){
            //over
            public void onClick(View v)
            {
                Intent baiduIntent = new Intent(Intent.ACTION_VIEW);
                baiduIntent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(baiduIntent);
            }
        });

        Intent intent = getIntent();
        String getData = intent.getStringExtra("First");
        TextView text2 = (TextView)findViewById(R.id.textView2);
        text2.setText(getData);

        Button btnReturn = (Button)findViewById(R.id.button_back);
        btnReturn.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra("return_data", "I'm return from second active");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
