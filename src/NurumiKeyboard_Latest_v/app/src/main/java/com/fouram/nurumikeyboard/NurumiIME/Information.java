package com.fouram.nurumikeyboard.NurumiIME;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by soyeong on 15. 4. 16..
 */
public class Information extends View {
    private Context context;
//    private ImageView img_inform;

    public Information(Context context) {
        super(context);
        this.context = context;


        Log.i("+B+INFORM", "11SUCCESS");
        ImageView img_inform = (ImageView)findViewById(R.id.img_inform);


        img_inform.setImageResource(R.drawable.img_inform);
    //    setImage();

    }

    private void setImage() {

    //    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.img_inform);
    //    Bitmap resized = Bitmap.createScaledBitmap(image, 450, 200, true);
    //    img_inform.setImageResource(R.drawable.img_inform);
    //    img_inform.setImageBitmap(resized);
    //    img_inform.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //img_inform.setImageResource(R.drawable.img_inform);
    }

}
