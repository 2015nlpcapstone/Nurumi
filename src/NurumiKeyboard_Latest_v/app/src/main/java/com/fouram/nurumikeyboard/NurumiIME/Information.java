package com.fouram.nurumikeyboard.NurumiIME;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * @file InformActivity.java
 *
 * @brief This Class's major function is introducing all finger button's input means about all character.
 *
 * @author Soyeong Park
 * @date 2015-04-16
 */
public class Information extends View {
    private Context context;
    private Bitmap image;

    /**
     * @brief This Constructor's function is to set default value about dialog
     *
     * @param context is object of function receiving a called Parent's View.
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    public Information(Context context) {
        super(context);
        this.context = context;

        setImage(R.drawable.img_inform);
    }

    /**
     * @brief This Constructor's function is to set default value about dialog
     *
     * @param context is object of function receiving a called Parent's View.
     * @param attrs
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    public Information(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setImage(R.drawable.img_inform);
    }

    /**
     * @brief This onDraw's function is to draw an image on view.
     *
     * @param canvas is drawing an image
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pnt = new Paint();

        canvas.drawBitmap(image, 0, 0, pnt);
    }

    /**
     * @brief This resizeBitmapImage's function is to resize image to fit
     *
     * @param bmpSource is an original image source
     * @param maxResoulution is a image's max value among a and b
     * @return Bitmap.createScaledBitmap is data related image
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    public Bitmap resizeBitmapImage(Bitmap bmpSource, int maxResoulution) {
        int curWidth = bmpSource.getWidth();
        int curHeight = bmpSource.getHeight();
        int newWidth = curWidth;
        int newHeight = curHeight;
        float rate = 0.0f;

        if(curWidth > curHeight) {
            if(maxResoulution < curWidth) {
                rate = maxResoulution / (float) curWidth;
                newHeight = (int) (curHeight * rate);
                newWidth = maxResoulution;
            }
        }
        else {
            if (maxResoulution < curHeight) {
                rate = maxResoulution / (float) curHeight;
                newWidth = (int) (curWidth * rate);
                newHeight = maxResoulution;
            }
        }
        return Bitmap.createScaledBitmap(bmpSource, newWidth, newHeight, true);
    }

    /**
     * @brief This getImageHeight's function is to get an image's height
     *
     * @return getHeight() is an image's height
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    public int getImageHeight() {
        return image.getHeight();
    }

    /**
     * @brief This getImageWidth's function is to get an image's width
     *
     * @return getWidth() is an image's width
     *
     * @author Soyeong Park
     * @date 2015-04-16
     */
    public int getImageWidth() {
        return image.getWidth();
    }

    /**
     * @brief This setImage's function is to set all about images
     *
     * @param id is id of an image about Information
     *
     * @author Soyeong Park
     * @date 2015-05-02
     */
    protected void setImage(int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(id);
        Bitmap bitmap = drawable.getBitmap();

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        // Can modify size of an image using 2nd parameter
        image = resizeBitmapImage(bitmap, Math.min(deviceWidth, deviceHeight - 50));

        Log.i("+++++HEIGHT", String.valueOf(image.getHeight()));
        Log.i("+++++WEIGHT", String.valueOf(image.getWidth()));
    }
}
