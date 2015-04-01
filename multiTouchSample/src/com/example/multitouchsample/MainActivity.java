package com.example.multitouchsample;

import java.util.*;

import android.app.Activity;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.util.Log;


public class MainActivity extends Activity {
	private Context ctx = this;	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		MyView view = new MyView(this);
		setContentView(view);
	}

	protected class MyView extends View
	{
		/////////////////////////////////////////////
		/// @fn 
		/// @brief Constructor of Motion keyboard view
		/// @remark
		/// - Description : Initialize all variables and lists for gesture recognition.
		/// @param context 
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public MyView(Context context)
		{
			super(context);
			initialize();
		}
		
		/////////////////////////////////////////////
		/// @class CircleLinkedWithPtId
		///com.example.multitouchsample \n
		///   ¤¤ MainActivity.java
		/// @section Class information
		///    |    Item    |    Contents    |
		///    | :-------------: | -------------   |
		///    | Company | 4:00 A.M. |    
		///    | Author | Park, Hyung Soon |
		///    | Date | 2015. 3. 26. |
		/// @section Description
		///	- This class will bind pointerID with circleNum
		/////////////////////////////////////////////
		public class CircleLinkedWithPtId
		{
			int pointerId;
			int circleNum;		
		}
		/////////////////////////////////////////////
		/// @class PtIdLinkedWithPtIndex
		///com.example.multitouchsample \n
		///   ¤¤ MainActivity.java
		/// @section Class information
		///    |    Item    |    Contents    |
		///    | :-------------: | -------------   |
		///    | Company | 4:00 A.M. |    
		///    | Author | Park, Hyung Soon |
		///    | Date | 2015. 3. 26. |
		/// @section Description
		///  - This class will bind pointerID with pointerIndex
		/// 
		/////////////////////////////////////////////
		public class PtIdLinkedWithPtIndex
		{
			int pointerId;
			int pointerIndex;	
		}
		
		private int[] motion;
		private boolean[] circleAvailable;
		private boolean motionStartFlag;
		
		private static final int INVALID_CIRCLE		= -1;
		private static final int INVALID_DIRECTION 	= -1;
		private static final int DIRECTION_DOT		= 0;
		private static final int DIRECTION_DOWN 	= 1;
		private static final int DIRECTION_LEFT 	= 2;
		private static final int DIRECTION_UP  		= 3;
		private static final int DIRECTION_RIGHT	= 4;
		private static final int SWIPE_MIN_DISTANCE = 140;
		
		private LinkedList<PtIdLinkedWithPtIndex> plp;
		private ArrayList<CircleLinkedWithPtId> clp;
		
		private ArrayList<PointF> startPtArr;
		private ArrayList<PointF> oldPtArr;
		private ArrayList<PointF> ptArr;
		
		private boolean start;
		private boolean startFlag;
		
		final Comparator<PointF> comparatorX = 
		/////////////////////////////////////////////
		/// @class 1
		///com.example.multitouchsample \n
		///   ¤¤ MainActivity.java
		/// @section Class information
		///    |    Item    |    Contents    |
		///    | :-------------: | -------------   |
		///    | Company | 4:00 A.M. |    
		///    | Author | Park, Hyung Soon |
		///    | Date | 2015. 3. 26. |
		/// @section Description
		///  - Comparator function for sort Circle number
		/// 
		/////////////////////////////////////////////
		new Comparator<PointF> () {

			public int compare(PointF pt1, PointF pt2)
			{
				return (int) (pt1.x - pt2.x);
			}
		};
		/*
		final Comparator<PointF> comparatorY = new Comparator<PointF> () {
			public int compare(PointF pt1, PointF pt2)
			{
				return (int) (pt2.y - pt1.y);
			}
		};*/

		
		/////////////////////////////////////////////
		/// @fn initialize
		/// @brief Function information : Initialize function
		/// @remark
		/// - Description : 
		/// Initialize all variables and lists 
		///
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public void initialize()
		{
			startFlag = false;
			start = false;
			motionStartFlag = false;
			motion = new int[5];
			circleAvailable = new boolean[5];
			for(int i=0; i<5; i++)
				circleAvailable[i] = true;
			
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
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief (Override method) Function information : Screen drawing function.
		/// @remark
		/// - Description
		///	Draw 5 or 10 start point circles and touched point circles.
		/// @see android.view.View#onDraw(android.graphics.Canvas)
		/////////////////////////////////////////////
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
		/////////////////////////////////////////////
		/// @fn 
		/// @brief (Override method) Function information : Touch event method
		/// @remark
		/// - Description : This method will classify motion events.\n
		///	Used MotionEvent.ACTION_MASK for recognize ACTION_POINTER events.\n
		/// ACTION_DOWN, ACTION_POINTER_DOWN, ACTION_UP, ACTION_POINTER_UP, ACTION_MOVE and other event(default) will be recognzied.
		/// @param e A motion event
		/// @return Returns boolean value wether the touch event is valid or not.
		///
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/// @see android.view.View#onTouchEvent(android.view.MotionEvent)
		/////////////////////////////////////////////
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
						if( checkTouchedCircle((int)e.getX(), (int)e.getY()) == INVALID_CIRCLE )
							return false;
						for(int i=0; i<5; i++)
							motion[i] = -1;
						motionStartFlag = true;
						Log.d("Motion Start", "------------------------------");
					}
					case MotionEvent.ACTION_POINTER_DOWN :
					{
						int touchCount = e.getPointerCount();
						int circleNum = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
						if(touchCount>5 || circleNum == -1 || !motionStartFlag || !circleAvailable[circleNum-1])
						{
							invalidate();
							return true;
						}
													
						circleAvailable[circleNum-1] = false;
						motion[circleNum-1] = DIRECTION_DOT;
						
						PointF ptf = new PointF();
						ptf.x = e.getX(touchCount-1);
						ptf.y = e.getY(touchCount-1);
						oldPtArr.add(ptf);

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
							
							PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex();
							pp.pointerIndex = i;
							pp.pointerId = e.getPointerId(i);
							plp.add(pp);
							
							checkDirection(pp, ptf);
						}
						invalidate();
						return true;
					}
					case MotionEvent.ACTION_POINTER_UP :
					{
						motionStartFlag = false;
						
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						return true;
					}
					case MotionEvent.ACTION_UP :
					{
						if(!startFlag && start)
						{
							startFlag = true;
							return true;
						}
						int touchCount = e.getPointerCount();
						if(touchCount>5)
							touchCount = 5;
						motionCheck();
						
						/* initialization for next motion */
						oldPtArr.clear();
						ptArr.clear();
						clp.clear();
						plp.clear();
						for(int i=0; i<5; i++)
							circleAvailable[i] = true;
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
		
		/////////////////////////////////////////////
		/// @fn motionCheck
		/// @brief Function information : Motion checking method
		/// @remark
		/// - Description : In ACTION_UP motion event, this method will be called.\n 
		/// Checks motion array and check motion of each pointer.\n
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public void motionCheck()
		{
			String command = "";
			for(int i=0; i<5; i++)
			{
				if(motion[i] != -1)
				{
					switch(motion[i])
					{
						case DIRECTION_DOT :
						{
							Log.d("UP", "circleIndex : " + (i+1) + "| Dir) DOT");
							command += "circleIndex : " + (i+1) + "| Dir) DOT\n";
							break;
						}
						case DIRECTION_UP :
						{
							Log.d("UP", "circleIndex : " + (i+1) + "| Dir) UP");
							command += "circleIndex : " + (i+1) + "| Dir) UP\n";
							break;
						}
						case DIRECTION_DOWN :
						{
							Log.d("UP", "circleIndex : " + (i+1) + "| Dir) DOWN");
							command += "circleIndex : " + (i+1) + "| Dir) DOWN\n";
							break;
						}
						case DIRECTION_LEFT :
						{
							Log.d("UP", "circleIndex : " + (i+1) + "| Dir) LEFT");
							command += "circleIndex : " + (i+1) + "| Dir) LEFT\n";
							break;
						}
						case DIRECTION_RIGHT :
						{
							Log.d("UP", "circleIndex : " + (i+1) + "| Dir) RIGHT");
							command += "circleIndex : " + (i+1) + "| Dir) RIGHT\n";
						}
					}//switch end
				}//motion check if end
			}//motion check for end
			Log.d("Motion End", "------------------------------");
			if(!command.equals(""))
				Toast.makeText(ctx, command, android.widget.Toast.LENGTH_SHORT).show();
		}
		
		/////////////////////////////////////////////
		/// @fn checkTouchedCircle
		/// @brief Function information : Find touched circle
		/// @remark
		/// - Description : This method will check which circle is touched
		/// @param x x grid of touched point
		/// @param y y grid of touched point
		/// @return Returns touched circle number. If any of circle is touched, return -1.
		///
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
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
		
		
		/////////////////////////////////////////////
		/// @fn startMultiTouch
		/// @brief Function information : Start multi touch recognition. 
		/// @remark
		/// - Description : If 'numFingers' of fingers are touched, set 'start' flag true and start multi touch motion recognition.\n
		/// Set 'numFingers' of starting points.
		/// @param e A motion event
		/// @return Returns the boolean value of motion event is valid or not.
		///
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
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
					/*
					PointF pt1, pt2;
					pt1 = startPtArr.get(0);
					pt2 = startPtArr.get(4);
					if(pt2.x-pt1.x < 800)
						Collections.sort(startPtArr, comparatorY);
					*/
					return true;
				}
				else {return true;}
			}
			else {return false;}
		}
		
		
		/////////////////////////////////////////////
		/// @fn checkDirection
		/// @brief Function information : Check the direction of movement of pointers
		/// @remark
		/// - Description : Calculate the moved distances of pointers and save them in 'motion' array. 
		/// @param pp : List of class which has Pointer ID and Pointer Index to link them.
		/// @param pt : Grid of currently moving pointer.
		///
		///~~~~~~~~~~~~~{.java}
		/// // core code
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
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
				motion[circleNum-1] = DIRECTION_DOT;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && Math.abs(distanceY) > SWIPE_MIN_DISTANCE )
				motion[circleNum-1] = INVALID_DIRECTION;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX > 0)
				motion[circleNum-1] = DIRECTION_LEFT;
			else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX < 0)
				motion[circleNum-1] = DIRECTION_RIGHT;
			else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY < 0)
				motion[circleNum-1] = DIRECTION_DOWN;
			else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY > 0)
				motion[circleNum-1] = DIRECTION_UP;
			else
				motion[circleNum-1] = INVALID_DIRECTION;
		}
		
	}	
}