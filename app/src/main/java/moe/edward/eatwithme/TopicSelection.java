package moe.edward.eatwithme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
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

    //map data structure: {text,X,Y}
    private void updateEntryList(ScrollView view) {
        ArrayList<String[]> map = getEntries();
        for(String[] entry : map){
            Button button = new Button(this);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(entry[0]);
            final int x = Integer.getInteger(entry[1]);
            final int y = Integer.getInteger(entry[2]);
            button.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(TopicSelection.this, Map.class);
                    intent.putExtra("entry", new int[]{x,y});
                    startActivity(intent);
                }
            });
        }
    }

    private ArrayList<String[]> getEntries(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        result.add(new String[]{"Anime, JRPG Games, Hentai", "1", "2"});
        result.add(new String[]{"Trump, Make America Great Again, Democrat", "2", "2"});
        result.add(new String[]{"Hack, Virus, Stack Overflow", "3", "2"});
        result.add(new String[]{"Segmentation Fault, Core Dumped, Screwed", "4", "2"});
        return result;
    }

    public void onClickButton(View e){

        Intent intent = new Intent(TopicSelection.this, Map.class);
        startActivity(intent);
    }
}
