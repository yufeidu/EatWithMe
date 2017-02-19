package moe.edward.eatwithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ortiz.touch.*;

public class Map extends AppCompatActivity {
    int[] pos = new int[2];
    int mode;
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
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",0);
        if(mode==1){
            button.setVisibility(View.INVISIBLE);
            int[] pos = intent.getIntArrayExtra("entry");
            Bitmap myimage = BitmapFactory.decodeResource(getResources(),R.drawable.douglas);
            Bitmap tempMap = Bitmap.createBitmap(myimage.getWidth(), myimage.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(tempMap);
            canvas.drawBitmap(myimage,0,0,null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect((float)pos[0]-50,(float)pos[1]-50,(float)pos[0]+50,(float)pos[1]+50,paint);
            Log.d("Rect","\n"+pos[0]+" "+pos[1]+"\n");
            imageView.setImageBitmap(tempMap);
        }
    }

    public void setPos(int x, int y, int xscreen, int yscreen){
        if(mode == 2) {
            pos[0] = x;
            pos[1] = y;
            set = true;
            button.setEnabled(true);

            RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(pin.getLayoutParams());
            marginLayoutParams.setMargins(xscreen, yscreen, 0, 0);
            pin.setLayoutParams(marginLayoutParams);
            pin.setVisibility(View.VISIBLE);
        }
    }

    public void hidePin(){
        pin.setVisibility(View.INVISIBLE);
        button.setEnabled(false);
    }
}
