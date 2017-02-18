package moe.edward.eatwithme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.ortiz.touch.*;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TouchImageView imageView = (TouchImageView)findViewById(R.id.image);
//        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        imageView.startAnimation(zoom);

    }
}
