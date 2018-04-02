package com.example.gopalawasthi.facebooksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class posts extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        listView = findViewById(R.id.listview);

        new GraphRequest(AccessToken.getCurrentAccessToken()
                        ,"me/feed"
                        ,null
                        , HttpMethod.GET,
        new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.getRawResponse());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0 ;i < jsonArray.length() ; i++){
                        arrayList.add(jsonArray.getJSONObject(i).getString("story"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapter = new ArrayAdapter(posts.this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);
            }
        }).executeAsync();

    }
}
