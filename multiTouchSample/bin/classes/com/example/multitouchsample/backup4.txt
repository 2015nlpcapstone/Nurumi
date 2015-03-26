package com.example.multitouchsample;

import java.util.*;

import android.app.Activity;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
//import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;

public class MainActivity extends Activity {

	int[] motion;
	int[] CircleNumLinkedWithptId;
	//private static final int SWIPE_MIN_DISTANCE = 50;
	//private static final int SWIPE_MAX_OFF_PATH = 250;
	//private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	ArrayList<PointF> startPtArr;
	ArrayList<PointF> oldPtArr;
	ArrayList<PointF> ptArr;
	
	final Comparator<PointF> comparatorX = new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2)
		{
			return (int) (pt1.x - pt2.x);
		}
	};
	final Comparator<PointF> comparatorY = new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2)
		{
			return (int) (pt2.y - pt1.y);
		}
	};
	
	boolean start;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			start = false;
			motion = new int[5];
			CircleNumLinkedWithptId = new int[5];
			for(int i=0; i<5; i++)
				CircleNumLinkedWithptId[i]=-1;
			MyView view = new MyView(this);
			setContentView(view);
			
			startPtArr = new ArrayList<PointF>();
			oldPtArr = new ArrayList<PointF>();
			ptArr = new ArrayList<PointF>();
			
			startPtArr.clear();
			oldPtArr.clear();
			ptArr.clear();
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
			
			/* standard position */
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
			
			/* down event position */
			if(!oldPtArr.isEmpty())
			{
				for (PointF pt : oldPtArr)
				{
					int circleNum = checkTouchedCircle((int)pt.x, (int)pt.y);
					pnt.setStyle(Paint.Style.STROKE);
					canvas.drawCircle(pt.x,pt.y, 100, pnt);
	
					pnt.setStyle(Paint.Style.FILL);
					canvas.drawText(String.valueOf(circleNum),pt.x,pt.y-114,pnt);
				}
			}
			
			/* current finger */
			index=0;
			Log.d("index", "index : " + index);
			if(!ptArr.isEmpty())
			{			
				for (PointF pt : ptArr)
				{
					int circleNum = -1; //= CircleNumLinkedWithptId[index++];//= checkTouchedCircle((int)pt.x, (int)pt.y);
					while(index < 5 && circleNum == -1)
						circleNum = CircleNumLinkedWithptId[index++];
					
					pnt.setStyle(Paint.Style.STROKE);
					canvas.drawCircle(pt.x,pt.y, 100, pnt);
	
					pnt.setStyle(Paint.Style.FILL);
					canvas.drawText(String.valueOf(circleNum),pt.x,pt.y-114,pnt);
				}
			}
			
		} 

		//@Override
		public boolean onTouchEvent (MotionEvent e)
		{
			int action = e.getAction() & MotionEvent.ACTION_MASK;
			
			if(start == false)
				return startMultiTouch(e);
			else
			{
				
				switch(action)
				{
					case MotionEvent.ACTION_DOWN :
					case MotionEvent.ACTION_POINTER_DOWN :
					{
						int touchCount = e.getPointerCount();
						if(touchCount>5)
						{
							invalidate();
							return true;
						}
						PointF ptf = new PointF();
						ptf.x = e.getX(touchCount-1);
						ptf.y = e.getY(touchCount-1);
						Log.d("DOWN", "PointerID : " + e.getPointerId(touchCount-1) + "| Grid) x="+e.getX(touchCount-1)+",y="+e.getY(touchCount-1));
						CircleNumLinkedWithptId[touchCount-1] = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
						oldPtArr.add(ptf);
						invalidate();
						return true;
					}
					
					case MotionEvent.ACTION_MOVE :
					{
						ptArr.clear();
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						for (int i=0; i<touchCount; i++)
						{
							PointF ptf = new PointF();
							ptf.x = e.getX(i);
							ptf.y = e.getY(i);
							//Log.d("Moving", "PointerID : " + e.getPointerId(i) + "| Grid) x="+e.getX(i)+",y="+e.getY(i));
							//Log.d("Pointer", "Pointer "+(i+1)+": x="+e.getX(i)+",y="+e.getY(i));
							ptArr.add(ptf);
						}
						invalidate();
						return true;
					}
					//case MotionEvent.ACTION_POINTER_UP :
					case MotionEvent.ACTION_UP :
					{
						oldPtArr.clear();
						ptArr.clear();
						invalidate();
						return true;
					}
					default :
					{
						invalidate();
						return false;
					}		
				}
			}
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
		
		
		public boolean startMultiTouch(MotionEvent e)
		{
			startPtArr.clear();
			if ( e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE )
			{
				int touchCount = e.getPointerCount();		
				if(touchCount == 5)
				{
					start = true;
					//Log.d("start" , "start : " + start);
					for (int i=0; i<touchCount; i++)
					{
						PointF ptf = new PointF();
						ptf.x = e.getX(i);
						ptf.y = e.getY(i);
						startPtArr.add(ptf);
					}
					Collections.sort(startPtArr, comparatorX);
					PointF pt1, pt2;
					pt1 = startPtArr.get(0);
					pt2 = startPtArr.get(4);
					if(pt2.x-pt1.x < 800)
						Collections.sort(startPtArr, comparatorY);
					return true;
				}
				else {return true;}
			}
			else {return false;}
		}
		
		
	}	
}