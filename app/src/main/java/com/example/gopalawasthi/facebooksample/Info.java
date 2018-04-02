package com.example.gopalawasthi.facebooksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Info extends AppCompatActivity implements View.OnClickListener {

    TextView first;
    TextView second;
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        first = findViewById(R.id.firstname);
        second = findViewById(R.id.lastname);
        imageView = findViewById(R.id.profilepicture);
        button = findViewById(R.id.posts);
        Intent intent = getIntent();
        String firstname =intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
        String profilepic =  intent.getStringExtra("picture");
        first.setText(firstname);
        second.setText(lastname);
        Picasso.get().load(profilepic).fit().into(imageView);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,posts.class));
    }
}
