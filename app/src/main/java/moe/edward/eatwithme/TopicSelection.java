package moe.edward.eatwithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v7.appcompat.R.styleable.View;

public class TopicSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
    }

    private void updateEntryList(ScrollView view) {
        ArrayList<String[]> map = getEntries();
        for(String[] entry : map){
            Button button = new Button(this);
        }
    }

    private ArrayList<String[]> getEntries(){
        return null;
    }

    public void onClickButton(View e){

        Intent intent = new Intent(TopicSelection.this, Map.class);
        startActivity(intent);
    }
}
