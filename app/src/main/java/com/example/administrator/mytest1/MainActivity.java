package com.example.administrator.mytest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //String retData = new String();
    //TextView text1 = (TextView)findViewById(R.id.textView);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i("test xiang", "onCreate: ");
        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener(){
            //override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "you have click botton1", Toast.LENGTH_SHORT).show();
                //text1.setText(retData);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){
            //override
            public void onClick(View v)
            {
                finish();
            }
        });

        Button btnswitch = (Button)findViewById(R.id.button3);
        btnswitch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String data = "I'm main Active.";
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("First", data);
                startActivityForResult(intent,1);
            }
        });
        Button btnTest2 = (Button)findViewById(R.id.testAction3);
        btnTest2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String data = "I'm main Active.";
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                intent.putExtra("First", data);
                startActivityForResult(intent,1);
            }
        });
        Button btnTest3 = (Button)findViewById(R.id.testAction4);
        btnTest3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String data = "I'm main Active.";
                Intent intent = new Intent(MainActivity.this, Main4Activity.class);
                intent.putExtra("First", data);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {
/*
        if (1 == request)
        {
            String retData = new String();
            retData = data.getStringExtra("return_data");
            Toast.makeText(MainActivity.this,retData,Toast.LENGTH_LONG);
            Log.i("xiang test", retData);
        }
        else
        {
            Log.i("xiang test", "ERR");
        }
        */
    }

}
