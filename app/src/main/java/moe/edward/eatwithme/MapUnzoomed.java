package moe.edward.eatwithme;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MapUnzoomed extends AppCompatActivity {
    ImageView map;
    boolean selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_unzoomed);
        map = (ImageView)findViewById(R.id.imageView_MapUnzoomed);
        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Cite: https://stackoverflow.com/questions/11312128/get-the-touch-position-inside-the-imageview-in-android
                int posX = (int) event.getX();
                int posY = (int) event.getY();

                int[] imagePos = new int[2];
                map.getLocationOnScreen(imagePos);
                int[] selection = {posX - imagePos[0], posY - imagePos[1]};
                Log.d("Pos",selection[0]+" "+selection[1]);
                Canvas mCanvas = new Canvas();
                mCanvas.drawLine((float)selection[0]-10,(float)selection[1]-10,(float)selection[0]+10,(float)selection[1]+10,new Paint());
                return false;
            }
        });
    }
}
