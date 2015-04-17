package com.fouram.nurumikeyboard.NurumiIME;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by soyeong on 15. 4. 16..
 */
public class Information extends View {
    private Context context;
    private ImageView informView;
    private Bitmap image;
    private Bitmap infImg;

    public Information(Context context) {
        super(context);
        this.context = context;
        Log.i("+B+INFORM", "11SUCCESS");
        setImage();
    }

    public Information (Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("+B+INFORM", "1SUCCESS");
        setImage();
    }
    /*
    public Information(Context context) {
        super(context);
        this.context = context;
        Log.i("+B+INFORM", "11SUCCESS");
        setImage();

    }*/

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pnt = new Paint();

        canvas.drawBitmap(image,0,0,pnt);
    }

    public void ddd() {
        Log.d("d", "ddd");
    }

    protected void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Log.i("+B+INFORM", "111SUCCESS");
        image = BitmapFactory.decodeResource(getResources(), R.drawable.img_inform, options);
        //BitmapDrawable drawable;
        //drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_inform);
        //image = drawable.getBitmap();

        //infImg = Bitmap.createScaledBitmap(drawable.getBitmap(), 720, 1280, true);
        //this.setImageBitmap(image);
        //drawable.setCallback(null);
        //image.recycle();
    }

}
