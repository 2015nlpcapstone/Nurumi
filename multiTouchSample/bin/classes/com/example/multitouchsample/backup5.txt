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
	
	public class CircleLinkedWithPtId
	{
		int pointerId;
		int circleNum;		
	}
	public class PtIdLinkedWithPtIndex
	{
		int pointerId;
		int pointerIndex;	
	}
	int[] motion;
	
	boolean motionStartFlag;
	
	private static final int INVALID_DIRECTION 	= -1;
	private static final int DOT  				= 0;
	private static final int DOWN  				= 1;
	private static final int LEFT  				= 2;
	private static final int UP  				= 3;
	private static final int RIGHT  			= 4;
	
	private static final int SWIPE_MIN_DISTANCE = 140;
	//private static final int SWIPE_MAX_OFF_PATH = 250;
	//private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	LinkedList<PtIdLinkedWithPtIndex> plp;
	ArrayList<CircleLinkedWithPtId> clp;
	
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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		MyView view = new MyView(this);
		setContentView(view);
		
		start = false;
		motionStartFlag = false;
		motion = new int[5];
		
		plp = new LinkedList<PtIdLinkedWithPtIndex>();
		clp = new ArrayList<CircleLinkedWithPtId>();
		
		startPtArr = new ArrayList<PointF>();
		oldPtArr = new ArrayList<PointF>();
		ptArr = new ArrayList<PointF>();
		
		plp.clear();
		clp.clear();
		
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
				canvas.drawCircle(spt.x,spt.y, 140, pnt);

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
			if(!ptArr.isEmpty() && !plp.isEmpty() && !clp.isEmpty())
			{			
				for (PointF pt : ptArr)
				{
					int pointerId = plp.get(index++).pointerId;
					if(pointerId >= clp.size())
						break;					
					int circleNum = clp.get(pointerId).circleNum;
					
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
					{
						for(int i=0; i<5; i++)
							motion[i] = -1;
						motionStartFlag = true;
						Log.d("Motion Start", "------------------------------");
					}
					case MotionEvent.ACTION_POINTER_DOWN :
					{
						int touchCount = e.getPointerCount();
						int circleNum = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
						if(touchCount>5 || circleNum == -1 || !motionStartFlag)
						{
							invalidate();
							return true;
						}
						
						motion[circleNum-1] = DOT;
						
						PointF ptf = new PointF();
						ptf.x = e.getX(touchCount-1);
						ptf.y = e.getY(touchCount-1);
						oldPtArr.add(ptf);
						
						//Log.d("DOWN", "PointerID : " + e.getPointerId(touchCount-1) + "| Grid) x="+e.getX(touchCount-1)+",y="+e.getY(touchCount-1));
						
						CircleLinkedWithPtId cp = new CircleLinkedWithPtId();
						cp.pointerId = e.getPointerId(touchCount-1);
						cp.circleNum = circleNum;
						clp.add(cp);
						
						invalidate();
						return true;
					}
					
					case MotionEvent.ACTION_MOVE :
					{
						ptArr.clear();
						plp.clear();
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						for (int i=0; i<touchCount; i++)
						{
							PointF ptf = new PointF();
							ptf.x = e.getX(i);
							ptf.y = e.getY(i);
							ptArr.add(ptf);
							//Log.d("Moving", "PointerID : " + e.getPointerId(i) + "| Grid) x="+e.getX(i)+",y="+e.getY(i));
							//Log.d("Pointer", "Pointer "+(i+1)+": x="+e.getX(i)+",y="+e.getY(i));
							
							PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex();
							pp.pointerIndex = i;
							pp.pointerId = e.getPointerId(i);
							plp.add(pp);
						}
						invalidate();
						return true;
					}
					case MotionEvent.ACTION_POINTER_UP :
					{
						plp.clear();
						motionStartFlag = false;
						
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						
						PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex();
						for (int i=0; i<touchCount; i++)
						{
							pp.pointerIndex = i;
							pp.pointerId = e.getPointerId(i);
							plp.add(pp);
						}
						PointF pt = new PointF(e.getX(), e.getY());
						for (int i=0; i<touchCount; i++)
						{
							if(plp.size() <= i)
								break;
							pp = plp.get(i);
							checkDirection(pp, pt);
						}
						
						return true;
					}
					case MotionEvent.ACTION_UP :
					{
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						
						PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex();
						pp.pointerIndex = 0;
						pp.pointerId = e.getPointerId(0);
						
						PointF pt = new PointF(e.getX(), e.getY());
						
						checkDirection(pp, pt);
						for(int i=0; i<5; i++)
						{
							if(motion[i] != -1)
							{
								switch(motion[i])
								{
									case DOT :
									{
										Log.d("UP", "circleIndex : " + (i+1) + "| Dir) DOT");
										break;
									}
									case UP :
									{
										Log.d("UP", "circleIndex : " + (i+1) + "| Dir) UP");
										break;
									}
									case DOWN :
									{
										Log.d("UP", "circleIndex : " + (i+1) + "| Dir) DOWN");
										break;
									}
									case LEFT :
									{
										Log.d("UP", "circleIndex : " + (i+1) + "| Dir) LEFT");
										break;
									}
									case RIGHT :
										Log.d("UP", "circleIndex : " + (i+1) + "| Dir) RIGHT");
								}//switch end
							}//motion check if end
						}//motion check for end
						Log.d("Motion End", "------------------------------");
						
						/* initialization for next motion */
						oldPtArr.clear();
						ptArr.clear();
						clp.clear();
						plp.clear();
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
				if( (Math.abs((int)spt.x - x) < 180) && (Math.abs((int)spt.y - y) < 140) )
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
					Log.d("start" , "start : " + start);
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
		
		
		public void checkDirection(PtIdLinkedWithPtIndex pp, PointF pt)
		{
			CircleLinkedWithPtId cp = new CircleLinkedWithPtId();
			for(int i=0; i<5; i++)
			{
				if(clp.size() <= i)
					return;
				cp = clp.get(i);
				if(pp.pointerId == cp.pointerId)
					break;
			}
			
			PointF oldPt = new PointF();
			int circleNum = -1;
			for(int i=0; i<5; i++)
			{
				if(oldPtArr.size() <= i)
					break;
				oldPt = oldPtArr.get(i);
				circleNum = checkTouchedCircle((int)oldPt.x, (int)oldPt.y);
				if(cp.circleNum == circleNum)
					break;
			}
			if(circleNum == -1) return;
			
			float distanceX = oldPt.x - pt.x;
			float distanceY = oldPt.y - pt.y;
			
			if( Math.abs(distanceX) < SWIPE_MIN_DISTANCE && Math.abs(distanceY) < SWIPE_MIN_DISTANCE )
				motion[circleNum-1] = DOT;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && Math.abs(distanceY) > SWIPE_MIN_DISTANCE )
				motion[circleNum-1] = INVALID_DIRECTION;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX > 0)
				motion[circleNum-1] = LEFT;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX < 0)
				motion[circleNum-1] = RIGHT;
			else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY < 0)
				motion[circleNum-1] = DOWN;
			else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY > 0)
				motion[circleNum-1] = UP;
			else
				motion[circleNum-1] = INVALID_DIRECTION;
		}
		
	}	
}