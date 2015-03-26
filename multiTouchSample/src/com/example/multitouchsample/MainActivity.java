package com.example.multitouchsample;

import java.util.*;

import android.app.Activity;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements OnGestureListener{

	int[] motion;
	private static final int SWIPE_MIN_DISTANCE = 50;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureScanner;
	
	ArrayList<PointF> startPtArr;
	ArrayList<PointF> ptArr;
	final Comparator<PointF> comparator = new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2)
		{
			return (int) (pt1.x - pt2.x);
		}
	};
	
	boolean start;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			MyView view = new MyView(this);
			setContentView(view);
			
			start = false;
			motion = new int[5];
			startPtArr = new ArrayList<PointF>();
			ptArr = new ArrayList<PointF>();
	}

	protected class MyView extends View
	{
		public MyView(Context context)
		{
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			Paint pnt = new Paint();
			canvas.drawColor(Color.WHITE);
			pnt.setTextSize(64.0f);			
			
			/* start position */
			if(startPtArr.isEmpty())
				return;
			int index=0;
			for(PointF spt : startPtArr)
			{
				index++;
				pnt.setColor(Color.BLACK);
				pnt.setStyle(Paint.Style.STROKE);
				pnt.setStrokeWidth(1);
				canvas.drawCircle(spt.x,spt.y, 180, pnt);

				pnt.setStyle(Paint.Style.FILL);
				canvas.drawText(String.valueOf(index),spt.x,spt.y-114,pnt);
			}
			
			/* current finger */
			if(ptArr.isEmpty())
				return;
			
			for (PointF pt : ptArr)
			{
				int circleNum = checkTouchedCircle((int)pt.x, (int)pt.y);
				pnt.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(pt.x,pt.y, 100, pnt);

				pnt.setStyle(Paint.Style.FILL);
				canvas.drawText(String.valueOf(circleNum),pt.x,pt.y-114,pnt);
			}
		} 

		//@Override
		public boolean onTouchEvent (MotionEvent e)
		{
			if(start == false)
			{				
				startPtArr.clear();
				if ( e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE )
				{
					int touchCount = e.getPointerCount();		
					if(touchCount == 5)
					{
						start = true;
						Log.d("start" , "start : " + start);
						for (int i=0; i<touchCount; i++)
						{
							PointF ptf = new PointF();
							ptf.x = e.getX(i);
							ptf.y = e.getY(i);
							startPtArr.add(ptf);
						}
						Collections.sort(startPtArr, comparator);
					}
					else {return true;}
				}
				else {return false;}
			}
			else
			{
				ptArr.clear();
				if ( e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE )
				{				
					int touchCount = e.getPointerCount();
					for (int i=0; i<touchCount; i++)
					{
						PointF ptf = new PointF();
						ptf.x = e.getX(i);
						ptf.y = e.getY(i);
						//Log.d("Pointer", "Pointer "+(i+1)+": x="+e.getX(i)+",y="+e.getY(i));
						Log.d("touchCount" , "touchCount : " + touchCount);
						ptArr.add(ptf);
					}
					invalidate();
					return true;
				}
			}
			invalidate();
			return gestureScanner.onTouchEvent(e);
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	float velocityY)
	{
		Log.d("onfiling" , "onFling");
		if(start == false)
			return false;
		try
		{
			int circleNum = checkTouchedCircle((int)e1.getX(), (int)e1.getY());
			Log.d("circleNum" , "circleNum : " + circleNum);
            // dot
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            {
				Toast.makeText(getApplicationContext(), circleNum + " dot", Toast.LENGTH_SHORT).show();
            }            
            // right to left swipe
            else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	Toast.makeText(getApplicationContext(), circleNum + "Left Swipe", Toast.LENGTH_SHORT).show();
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	Toast.makeText(getApplicationContext(), circleNum + "Right Swipe", Toast.LENGTH_SHORT).show();
            }
            // down to up swipe
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            	Toast.makeText(getApplicationContext(), circleNum + "Swipe up", Toast.LENGTH_SHORT).show();
            }
            // up to down swipe
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            	Toast.makeText(getApplicationContext(), circleNum + "Swipe down", Toast.LENGTH_SHORT).show();
            }
		} catch (Exception e) {}
		return false;
	}
	
	public int checkTouchedCircle(int x, int y)
	{		
		int index=0;
		for(PointF spt : startPtArr)
		{
			index++;
			if( (Math.abs((int)spt.x - x) < 180) && (Math.abs((int)spt.y - y) < 180) )
				return index;
		}
		return -1;
	}
}