package moe.edward.eatwithme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
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
    Bitmap imageMap, tempMap;
    Canvas mCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_unzoomed);

        imageMap = BitmapFactory.decodeResource(getResources(), R.drawable.douglas);
        tempMap = Bitmap.createBitmap(imageMap.getWidth(), imageMap.getHeight(), Bitmap.Config.RGB_565);
        Log.d("tempMap",tempMap.getHeight()+"");
        Log.d("imageMap",imageMap.getWidth()+"");

        mCanvas = new Canvas(tempMap);
        mCanvas.drawBitmap(imageMap, 0, 0, null);
        Log.d("mCanvas",mCanvas.getWidth()+"");
        map = (ImageView)findViewById(R.id.imageView_MapUnzoomed);
        map.setImageDrawable(new BitmapDrawable(getResources(),tempMap));
        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Cite: https://stackoverflow.com/questions/11312128/get-the-touch-position-inside-the-imageview-in-android
                int posX = (int) event.getX();
                int posY = (int) event.getY();

                int[] imagePos = new int[2];
                map.getLocationOnScreen(imagePos);
                //int[] selection = {posX - imagePos[0], posY - imagePos[1]};

                updatePoint(posX - imagePos[0], posY - imagePos[1]);
                Canvas mCanvas = new Canvas();
                mCanvas.drawLine((float)selection[0]-10,(float)selection[1]-10,(float)selection[0]+10,(float)selection[1]+10,new Paint());
                return false;
            }
        });
    }

    private void updatePoint(int x, int y){
        selection = new int[2];
        selection[0]=x;
        selection[1]=y;
        selected=true;
        Log.d("Pos",selection[0]+" "+selection[1]);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        float xf = x;
        float yf = y;
        mCanvas.drawRect((mCanvas.getWidth()/map.getWidth())*xf-20,(mCanvas.getWidth()/map.getWidth())*yf-20,(mCanvas.getWidth()/map.getWidth())*xf+20,(mCanvas.getWidth()/map.getWidth())*yf+20,paint);
        map.setImageDrawable(new BitmapDrawable(getResources(),tempMap));
    }


}
