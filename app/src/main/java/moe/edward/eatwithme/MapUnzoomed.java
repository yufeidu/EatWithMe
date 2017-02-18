package moe.edward.eatwithme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MapUnzoomed extends AppCompatActivity {
    ImageView map;
    boolean selected = false;
    int[] selection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_unzoomed);
        DrawableImageView map = new DrawableImageView(this);
        map.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((RelativeLayout)findViewById(R.id.activity_map_unzoomed)).addView(map);
        /*map = (ImageView)findViewById(R.id.imageView_MapUnzoomed);
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
                updatePoint(posX - imagePos[0], posY - imagePos[1]);
                Canvas mCanvas = new Canvas();
                mCanvas.drawLine((float)selection[0]-10,(float)selection[1]-10,(float)selection[0]+10,(float)selection[1]+10,new Paint());
                return false;
            }
        });*/
    }

    private void updatePoint(int x, int y){
        selection = new int[2];
        selection[0]=x;
        selection[1]=y;
        selected=true;
    }

    public class DrawableImageView extends View{
        public DrawableImageView(Context context){
            super(context);
        }
        public void drawPoint(){

        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //Cite: https://stackoverflow.com/questions/11312128/get-the-touch-position-inside-the-imageview-in-android
            int posX = (int) event.getX();
            int posY = (int) event.getY();

            int[] imagePos = new int[2];
            map.getLocationOnScreen(imagePos);
            int[] selection = {posX - imagePos[0], posY - imagePos[1]};
            Log.d("Pos",selection[0]+" "+selection[1]);
            //updatePoint(posX - imagePos[0], posY - imagePos[1]);
            Canvas mCanvas = new Canvas();
            mCanvas.drawLine((float)selection[0]-10,(float)selection[1]-10,(float)selection[0]+10,(float)selection[1]+10,new Paint());
            return false;
        }

    }
}
