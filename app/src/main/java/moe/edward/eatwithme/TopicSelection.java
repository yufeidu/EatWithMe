package moe.edward.eatwithme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v7.appcompat.R.styleable.View;

public class TopicSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout_Inside_Topic);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        updateEntryList(layout);
        EditText text = (EditText)findViewById(R.id.editText_topic);
        text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if(s.toString().equals("")){
                    ((Button)findViewById(R.id.button_topic)).setEnabled(false);
                }else{
                    ((Button)findViewById(R.id.button_topic)).setEnabled(true);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    //map data structure: {text,X,Y}
    private void updateEntryList(LinearLayout view) {
        ArrayList<String[]> map = getEntries();
        for(String[] entry : map){
            Button button = new Button(this);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(entry[0]);
            final int x = Integer.parseInt(entry[1]);
            final int y = Integer.parseInt(entry[2]);
            button.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(TopicSelection.this, Map.class);
                    intent.putExtra("mode",1);
                    intent.putExtra("entry", new int[]{x,y});
                    startActivity(intent);
                }
            });
            button.setTransformationMethod(null);
            view.addView(button);
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
        String text = ((EditText)findViewById(R.id.editText_topic)).getText().toString();
        Intent intent = new Intent(TopicSelection.this, Map.class);
        intent.putExtra("mode", 2);
        intent.putExtra("data", text);
        startActivity(intent);
    }
}
