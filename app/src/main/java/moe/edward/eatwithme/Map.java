package moe.edward.eatwithme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ortiz.touch.*;

public class Map extends AppCompatActivity {
    int[] pos = new int[2];
    boolean set = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TouchImageView imageView = (TouchImageView)findViewById(R.id.image);
//        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        imageView.startAnimation(zoom);

    }

    public void setPos(int x, int y){
        pos[0] = x;
        pos[1] = y;
        set = true;
        ((Button)findViewById(R.id.button_Map)).setEnabled(true);
    }
}
