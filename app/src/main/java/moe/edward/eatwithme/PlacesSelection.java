package moe.edward.eatwithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlacesSelection extends AppCompatActivity implements View.OnClickListener{

    private String PLACE = "place";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_selection);

        final ImageButton imageButton = (ImageButton)findViewById(R.id.douglas);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacesSelection.this, TopicSelection.class);
                intent.putExtra(PLACE, imageButton.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        ImageButton button = (ImageButton)findViewById(v.getId());
        Intent intent = new Intent(PlacesSelection.this, TopicSelection.class);
        intent.putExtra(PLACE, button.getId());
        startActivity(intent);
    }

}
