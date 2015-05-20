package com.fouram.nurumikeyboard.NurumiIME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.util.DisplayMetrics;
//import android.widget.Toast;
import android.util.Log;
import android.util.AttributeSet;
import android.view.View;

/////////////////////////////////////////////
/// @class MKeyboardView
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� MKeyboardView.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///	- This file is for the view of motion keyboard.\n
///	- This view will popup when user\n put cursor in textbox.\n
/////////////////////////////////////////////
//
///////////////////////////////////////////
/// @class MKeyboardView
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� MKeyboardView.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///	- View of motion keyboard.\n
/////////////////////////////////////////////
public class MKeyboardView extends View {
	private Context ctx; 
	private NurumiIME ime;
	
	public static final int INVALID_CIRCLE		= -1;	
	public static final int SWIPE_MIN_DISTANCE = 40; /// dp value
	public static final int STD_CIRCLE_SIZE	= 90; /// dp value
	public static final int INNER_CIRCLE_SIZE	= 40; /// dp value
	public static final int FONT_SIZE = 21; // sp value
	
	/////////////////////////////////////////////
	/// @class CircleLinkedWithPtId
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   �� MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///	- This class will bind pointerID with circleNum.\n
	/////////////////////////////////////////////
	public class CircleLinkedWithPtId {
		int pointerId;
		int circleNum;		
	}
	/////////////////////////////////////////////
	/// @class PtIdLinkedWithPtIndex
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   �� MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///  - This class will bind pointerID with pointerIndex.\n
	/// 
	/////////////////////////////////////////////
	public class PtIdLinkedWithPtIndex {
		int pointerId;
		int pointerIndex;	
	}
	
	private final static Comparator<PointF> comparator =
	/////////////////////////////////////////////
	/// @class 1
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   �� MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///  - Comparator function for sort Circle number.\n
	///  - For right handed user.\n
	/////////////////////////////////////////////
	new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2) {
			return (int) (pt1.x - pt2.x);
		}
	};
		
	// variables in MKeyboardView
	private Paint pnt;
	
	private int innerCircleSize;
	private int standardCircleSize;
	private int fontSize;
	private int swipeThreshold;
	private int numFingers;
	
	private int[] motion;
	private boolean[] circleAvailable;	
		
	private LinkedList<PtIdLinkedWithPtIndex> plp;
	private ArrayList<CircleLinkedWithPtId> clp;
	private ArrayList<PointF> startPtArr;
	private ArrayList<PointF> oldPtArr;
	private ArrayList<PointF> ptArr;
	
	private boolean motionStartFlag;
	private boolean start;
	private boolean startFlag;
	
	private Bitmap bitmap;
	private Bitmap dotImg;
	private Bitmap upImg;
	private Bitmap downImg;
	private Bitmap leftImg;
	private Bitmap rightImg;
	private Bitmap stdCircleImg;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief Constructor of Motion keyboard view 
	/// @remark
	/// - Description : Initialize all variables and lists for gesture recognition.
	/// @param context : View context
	/// @param attrs : View attribute set
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public MKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		setDpValues();
		initialize();
		setBitmap();
	}
	
	private void setDpValues() {
		standardCircleSize = (int) dpToPx(STD_CIRCLE_SIZE, ctx.getApplicationContext());
		innerCircleSize = (int) dpToPx(INNER_CIRCLE_SIZE, ctx.getApplicationContext());
		swipeThreshold = (int) dpToPx(SWIPE_MIN_DISTANCE, ctx.getApplicationContext());
		fontSize = (int) dpToPx(FONT_SIZE, ctx.getApplicationContext());
	}
	
	/////////////////////////////////////////////
	/// @fn convertDpToPixel
	/// @brief Function information : Convers dp value to px value.
	/// @remark
	/// - Description : This method converts dp unit to equivalent pixels, depending on device density.
	/// @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels.
	/// @param context Context to get resources and device specific display metrics.
	/// @return A float value to represent px equivalent to dp depending on device density.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public float dpToPx(float dp, Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}
	
	/////////////////////////////////////////////
	/// @fn initialize
	/// @brief Initialize function
	/// @remark
	/// - Description : Initialize all variables and lists.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void initialize()	{
		pnt = new Paint();
		numFingers = NurumiIME.FIVE_FINGERS;
		
		startFlag = false;
		start = false;
		motionStartFlag = false;
		motion = new int[numFingers];
		circleAvailable = new boolean[numFingers];
		for(int i=0; i<numFingers; i++)
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

	public MKeyboardView(Context context) {
		super(context);
		this.ctx = context;
	}
	
	/////////////////////////////////////////////
	/// @fn setBitmap
	/// @brief Function information : Set bitmap images. 
	/// @remark
	/// - Description : Set bitmap images for directions.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void setBitmap() {
		BitmapDrawable drawable;
			
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_finger_dot);
		bitmap = drawable.getBitmap();
		dotImg = Bitmap.createScaledBitmap(drawable.getBitmap(), innerCircleSize*2, innerCircleSize*2, true);
				
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_finger_up);
		bitmap = drawable.getBitmap();
		upImg = Bitmap.createScaledBitmap(drawable.getBitmap(), innerCircleSize*2, innerCircleSize*2, true);
				
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_finger_down);
		bitmap = drawable.getBitmap();
		downImg = Bitmap.createScaledBitmap(drawable.getBitmap(), innerCircleSize*2, innerCircleSize*2, true);
				
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_finger_left);
		bitmap = drawable.getBitmap();
		leftImg = Bitmap.createScaledBitmap(drawable.getBitmap(), innerCircleSize*2, innerCircleSize*2, true);
				
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_finger_right);
		bitmap = drawable.getBitmap();
		rightImg = Bitmap.createScaledBitmap(drawable.getBitmap(), innerCircleSize*2, innerCircleSize*2, true);
		
		drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.img_std_circle);
		bitmap = drawable.getBitmap();
		stdCircleImg = Bitmap.createScaledBitmap(drawable.getBitmap(), standardCircleSize*2, standardCircleSize*2, true);
		
		drawable.setCallback(null);
	}
	
	/////////////////////////////////////////////
	/// @fn recycleBitmap
	/// @brief Function information : Free all Bitmap objects to evade memory leak.
	/// @remark
	/// - Description : To evade memory leak user have to free up Bitmap object. Android OS will not free up all Bitmaps.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void recycleBitmap() {
		if(bitmap != null)
			bitmap.recycle();
		if(dotImg != null)
			dotImg.recycle();
		if(upImg != null)
			upImg.recycle();
		if(downImg != null)
			downImg.recycle();
		if(leftImg != null)
			leftImg.recycle();
		if(rightImg != null)
			rightImg.recycle();
		if(stdCircleImg != null)
			stdCircleImg.recycle();
			
	}
	
	/////////////////////////////////////////////
	/// @fn setIme
	/// @brief Set parent IME
	/// @remark
	/// - Description : Set parent ime and link with IME variable MKeyboard View.\n
	/// @param ime Parent IME
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void setIme(NurumiIME ime) {
		this.ime = ime;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Screen drawing function.
	/// @remark
	/// - Description
	///	Draw 5 or 10 start point circles and touched point circles.\n
	/// @see android.view.View#onDraw(android.graphics.Canvas)
	/////////////////////////////////////////////
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	    canvas.drawColor(Color.TRANSPARENT);
	    
	    pnt.setTextSize(fontSize);
	    
	    /* standard position */
		if(startPtArr.isEmpty())
			return;
		
		for(PointF spt : startPtArr) {
			float pointX = spt.x - standardCircleSize;
			float pointY = spt.y - standardCircleSize;
			canvas.drawBitmap(stdCircleImg, pointX, pointY, pnt);
		}
		
		/* down event position */
		if(!oldPtArr.isEmpty())	{			
			for (PointF pt : oldPtArr) {
				int circleNum = checkTouchedCircle((int)pt.x, (int)pt.y);
				PointF spt = startPtArr.get(circleNum-1);
				
				/// Start points are the center points of circles.
				/// Images will be drawn from upper left corner.
				float pointX = spt.x - innerCircleSize;
				float pointY = spt.y - innerCircleSize;
				
				switch(motion[circleNum-1])	{				
					case IME_Automata.DIRECTION_DOT :		
						canvas.drawBitmap(dotImg, pointX, pointY, pnt);
						break;
					case IME_Automata.DIRECTION_UP :
						canvas.drawBitmap(upImg, pointX, pointY, pnt);
						break;
					case IME_Automata.DIRECTION_DOWN :
						canvas.drawBitmap(downImg, pointX, pointY, pnt);
						break;
					case IME_Automata.DIRECTION_LEFT :
						canvas.drawBitmap(leftImg, pointX, pointY, pnt);
						break;
					case IME_Automata.DIRECTION_RIGHT :
						canvas.drawBitmap(rightImg, pointX, pointY, pnt);
						break;
					default :
						break;
				} // switch end
			} // oldPtArr for-each end
		} // if end
	} // onDraw fin
	
	@Override
	public boolean performClick() {
	    return super.performClick();
	}
	
	//@Override
	/////////////////////////////////////////////
	/// @fn onTouchEvent
	/// @brief Touch event method
	/// @remark
	/// - Description : This method will classify motion events.\n
	///	Used MotionEvent.ACTION_MASK for recognize ACTION_POINTER events.\n
	/// ACTION_DOWN, ACTION_POINTER_DOWN, ACTION_UP, ACTION_POINTER_UP, ACTION_MOVE, ACTION_CANCEL, and other event(default) will be recognzied.
	/// @param e A motion event
	/// @return Returns boolean value wether the touch event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean onTouchEvent (MotionEvent e)	{
		int action = e.getAction() & MotionEvent.ACTION_MASK;
		
		if(start == false)
			return startMultiTouch(e);
		else {				
			switch(action) {
				case MotionEvent.ACTION_DOWN : {
					if( checkTouchedCircle((int)e.getX(), (int)e.getY()) == INVALID_CIRCLE )
						return false;
					for(int i=0; i<numFingers; i++) // initialize motion array
						motion[i] = IME_Automata.DIRECTION_EMPTY;
					motionStartFlag = true;
					Log.d("Motion Start", "------------------------------");
				}
				case MotionEvent.ACTION_POINTER_DOWN : {
					int touchCount = e.getPointerCount();
					int circleNum = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
					if(touchCount>numFingers || circleNum == INVALID_CIRCLE || !motionStartFlag || !circleAvailable[circleNum-1]) {
						invalidate();
						return true;
					}
					circleAvailable[circleNum-1] = false; // One finger in one circle.
					motion[circleNum-1] = IME_Automata.DIRECTION_DOT;
					
					PointF ptf = new PointF(); // Add start position.
					ptf.x = e.getX(touchCount-1);
					ptf.y = e.getY(touchCount-1);
					oldPtArr.add(ptf);

					CircleLinkedWithPtId cp = new CircleLinkedWithPtId(); // Link pointerID and circle number.
					cp.pointerId = e.getPointerId(touchCount-1);
					cp.circleNum = circleNum;
					clp.add(cp);
					
					invalidate();
					return true;
				}
				
				case MotionEvent.ACTION_MOVE : {
					ptArr.clear();
					plp.clear();
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					for (int i=0; i<touchCount; i++)
					{
						PointF ptf = new PointF();
						ptf.x = e.getX(i);
						ptf.y = e.getY(i);
						ptArr.add(ptf);
						
						PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex(); // Link pointerID and pointer index.
						pp.pointerIndex = i;
						pp.pointerId = e.getPointerId(i);
						plp.add(pp);
						
						checkDirection(pp, ptf);
					}
					invalidate();
					return true;
				}
				case MotionEvent.ACTION_POINTER_UP : {
					motionStartFlag = false;
					
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					return true;
				}
				case MotionEvent.ACTION_UP : { // No point left.
					if(!startFlag && start) { // If it is first touch to set standard position,
						startFlag = true;	  // do nothing.
						return true;
					}
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					motionCheck();
					
					/* initialization for next motion */
					oldPtArr.clear();
					ptArr.clear();
					clp.clear();
					plp.clear();
					for(int i=0; i<numFingers; i++)
						circleAvailable[i] = true;
					performClick();
					invalidate();					
					return true;
				}
				case MotionEvent.ACTION_CANCEL : // cancel all motions and initialize
					oldPtArr.clear();
					ptArr.clear();
					clp.clear();
					plp.clear();
					for(int i=0; i<numFingers; i++)
						circleAvailable[i] = true;
				default :
					invalidate();
					return false;
			} // switch end
		} // else end (for start flag)
	} // onTouchEvent fin
	
	/////////////////////////////////////////////
	/// @fn motionCheck
	/// @brief Motion checking method 
	/// @remark
	/// - Description : In ACTION_UP motion event, this method will be called.\n 
	/// Checks motion array and check motion of each pointer.\n
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void motionCheck() {
		int checkEmpty=numFingers;
		for(int i=0; i<numFingers; i++)
			checkEmpty += motion[i]; // Empty motion value is -1.
		Log.d("Motion End", "------------------------------");
		if(checkEmpty == 0)
			return;
		/* key event will be here. */
		ime.onFinishGesture(motion);
	}
	
	/////////////////////////////////////////////
	/// @fn checkTouchedCircle
	/// @brief Find touched circle
	/// @remark
	/// - Description : This method will check which circle is touched.\n
	/// If the distance of the touched point and the center of circle, it's in the circle.\n
	/// @param x x grid of touched point
	/// @param y y grid of touched point
	/// @return Returns touched circle number. If any of circle is touched, return -1.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private int checkTouchedCircle(int x, int y) {		
		int index=0;
		for(PointF spt : startPtArr) {
			index++;
			if( (Math.abs((int)spt.x - x) < standardCircleSize) && (Math.abs((int)spt.y - y) < standardCircleSize) )
				return index;
		}
		return INVALID_CIRCLE;
	} // checkTouchedCircle fin
	
	
	/////////////////////////////////////////////
	/// @fn startMultiTouch
	/// @brief Start multi touch recognition. 
	/// @remark
	/// - Description : If 'numFingers' of fingers are touched, set 'start' flag true and start multi touch motion recognition.\n
	/// Set 'numFingers' of starting points.
	/// @param e A motion event
	/// @return Returns the boolean value of motion event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean startMultiTouch(MotionEvent e) {
		startPtArr.clear();
		if ( e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE ) {
			int touchCount = e.getPointerCount();		
			if(touchCount == numFingers) {
				start = true;
				Log.d("start" , "start : " + start);
				for (int i=0; i<touchCount; i++) {
					PointF ptf = new PointF();
					ptf.x = e.getX(i);
					ptf.y = e.getY(i);
					startPtArr.add(ptf);
				}
				Collections.sort(startPtArr, comparator);
				return true;
			} // if(touchCount == numFingers) end
			else {return true;}
		} // motion event if end
		else {return false;}
	} // startMultiTouch fin
	
	
	/////////////////////////////////////////////
	/// @fn checkDirection
	/// @brief Check the direction of movement of pointers
	/// @remark
	/// - Description : Calculate the moved distances of pointers and save them in 'motion' array.\n
	/// By linking up pointer index and circle number, get started position.\n
	/// Get the distance between started position and current position.\n
	/// And figure out which direction the current point is.
	/// @param pp : An object which has Pointer ID and Pointer Index.
	/// @param pt : Grid of currently moving pointer.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void checkDirection(PtIdLinkedWithPtIndex pp, PointF pt)
	{
		CircleLinkedWithPtId cp = new CircleLinkedWithPtId();
		for(CircleLinkedWithPtId clpElement : clp) {   // Find cp which has same pointerID of pp.
			if(pp.pointerId == clpElement.pointerId) { // This is for link up circle number and pointer index.
				cp = clpElement;
				break;
			}
		}
		
		PointF oldPt = new PointF();
		int circleNum = INVALID_CIRCLE;
		for(PointF oldPtElement : oldPtArr) { // Find the started point with circle number.
			circleNum = checkTouchedCircle((int)oldPtElement.x, (int)oldPtElement.y);
			if(cp.circleNum == circleNum) {
				oldPt = oldPtElement;
				break;
			}
		}
		if(circleNum == INVALID_CIRCLE) return;
		
		float distanceX = oldPt.x - pt.x;
		float distanceY = oldPt.y - pt.y;
		
		if( Math.abs(distanceX) < swipeThreshold && Math.abs(distanceY) < swipeThreshold )
			motion[circleNum-1] = IME_Automata.DIRECTION_DOT;
		else if( Math.abs(distanceX) > swipeThreshold || Math.abs(distanceY) > swipeThreshold ) {
			if( Math.abs(distanceY/distanceX) < 1 && Math.abs(distanceX) > swipeThreshold) {
				// Gradient is smaller than 1.
				if(distanceX > 0)
					motion[circleNum-1] = IME_Automata.DIRECTION_LEFT;
				else
					motion[circleNum-1] = IME_Automata.DIRECTION_RIGHT;
			}
			else if( Math.abs(distanceY/distanceX) >= 1 && Math.abs(distanceY) > swipeThreshold) {
				// Gradient is 1 or larger.
				if(distanceY > 0)
					motion[circleNum-1] = IME_Automata.DIRECTION_UP;
				else
					motion[circleNum-1] = IME_Automata.DIRECTION_DOWN;
			}
		} // direction UDLR end
	} // checkDirection fin
}