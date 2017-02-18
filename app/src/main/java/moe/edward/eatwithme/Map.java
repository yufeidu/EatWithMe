package moe.edward.eatwithme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ortiz.touch.*;

public class Map extends AppCompatActivity {
    int[] pos = new int[2];
    boolean set = false;
    ImageView pin;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TouchImageView imageView = (TouchImageView)findViewById(R.id.image);
//        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        imageView.startAnimation(zoom);
        pin = (ImageView)findViewById(R.id.imagePin_Map);
        button = ((Button)findViewById(R.id.button_Map));

    }

    public void setPos(int x, int y, int xscreen, int yscreen){
        pos[0] = x;
        pos[1] = y;
        set = true;
        button.setEnabled(true);

        RelativeLayout.LayoutParams marginLayoutParams=new RelativeLayout.LayoutParams(pin.getLayoutParams());
        marginLayoutParams.setMargins(xscreen,yscreen,0,0);
        pin.setLayoutParams(marginLayoutParams);
        pin.setVisibility(View.VISIBLE);
    }
}
