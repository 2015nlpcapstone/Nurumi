package kookmin.cs.fouram.nurumikeyboard.inputmethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import kookmin.cs.fouram.nurumikeyboard.automata.IME_Automata;
import kookmin.cs.fouram.nurumikeyboard.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.AttributeSet;
import android.view.View;

/////////////////////////////////////////////
/// @class MotionKeyboardView
/// kookmin.cs.fouram.nurumikeyboard.inputmethod \n
///   ㄴ MotionKeyboardView.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description :\n
///	- This file is for the view of motion keyboard.\n
///	- This view will pop-up when user\n put cursor in text-box.\n
/////////////////////////////////////////////
public class MotionKeyboardView extends View {
	private Context ctx; 
	private NurumiIME ime;

	private final int INVALID_CIRCLE	 = -1;	
	private final int SWIPE_MIN_DISTANCE = 40; /// dp value
	private final int STD_CIRCLE_SIZE	 = 70; /// dp value
	private final int INNER_CIRCLE_SIZE	 = 40; /// dp value

	public static final int DIRECTION_EMPTY = -1;
	public static final int DIRECTION_DOT = 0;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_LEFT = 2;
	public static final int DIRECTION_UP = 3;
	public static final int DIRECTION_RIGHT = 4;
	
	public static final int THUMB_FINGER = 0;
	public static final int INDEX_FINGER = 1;
	public static final int MIDLE_FINGER = 2;
	public static final int RING__FINGER = 3;
	public static final int PINKY_FINGER = 4;

	
	/////////////////////////////////////////////
	/// @class CircleLinkedWithPtId
	/// kookmin.cs.fouram.nurumikeyboard.inputmethod \n
	///   ㄴ MotionKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description : \n
	///	- This class will bind pointerID with circleNum.\n
	/////////////////////////////////////////////
	public class CircleLinkedWithPtId {
		CircleLinkedWithPtId(){}
		CircleLinkedWithPtId(int pointerId, int circleNum) {
			this.pointerId = pointerId;
			this.circleNum = circleNum;
		}
		int pointerId;
		int circleNum;		
	}
	/////////////////////////////////////////////
	/// @class PtIdLinkedWithPtIndex
	/// kookmin.cs.fouram.nurumikeyboard.inputmethod \n
	///   ㄴ MotionKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description : \n
	///  - This class will bind pointerID with pointerIndex.
	/////////////////////////////////////////////
	public class PtIdLinkedWithPtIndex {
		PtIdLinkedWithPtIndex(){}
		PtIdLinkedWithPtIndex(int pointerId, int pointerIndex) {
			this.pointerId = pointerId;
			this.pointerIndex = pointerIndex;
		}
		int pointerId;
		int pointerIndex;	
	}

	private final static Comparator<PointF> comparator =
			/////////////////////////////////////////////
			/// @class Comparator
			/// kookmin.cs.fouram.nurumikeyboard.inputmethod \n
			///   ㄴ MotionKeyboardView.java
			/// @section Class information
			///    |    Item    |    Contents    |
			///    | :-------------: | -------------   |
			///    | Company | 4:00 A.M. |    
			///    | Author | Park, Hyung Soon |
			///    | Date | 2015. 3. 26. |
			/// @section Description : \n
			///  - Comparator function for sort Circle number.\n
			///  - For right handed user.
			/////////////////////////////////////////////
			new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2) {
			return (int) (pt1.x - pt2.x);
		}
	};

	// Variables in MotionKeyboardView
	private Paint pnt;

	// GUI state
	private int innerCircleSize;
	private int standardCircleSize;
	private int swipeThreshold;
	private int numFingers;

	private int[] motion;
	private boolean[] circleAvailable;	

	// Lists
	private LinkedList<PtIdLinkedWithPtIndex> plp;
	private ArrayList<CircleLinkedWithPtId> clp;
	private ArrayList<PointF> startPtArr;
	private ArrayList<PointF> oldPtArr;
	private ArrayList<PointF> ptArr;

	// Flags
	private boolean inputStartFlag;
	private boolean standardPositionFlag;
	private boolean motionStartFlag;

	// Bitmap images
	private Bitmap dotImg;
	private Bitmap upImg;
	private Bitmap downImg;
	private Bitmap leftImg;
	private Bitmap rightImg;
	private Bitmap stdCircleImg;

	/////////////////////////////////////////////
	/// @fn MotionKeyboardView
	/// @brief Constructor of Motion keyboard view 
	/// @remark
	/// - Description : Initialize all variables and lists for gesture recognition.
	/// @param context : View context
	/// @param attrs : View attribute set
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public MotionKeyboardView(Context context, AttributeSet attrs) {		
		super(context, attrs);
		Log.i("IME_LOG", "Location : MotionKeyboardView - Constructor");
		this.ctx = context;
		setDpValues();
		initialize();
		setBitmap();
	}

	/////////////////////////////////////////////
	/// @fn setDpValues()
	/// @brief Set DP values to all 'size variables'.
	/// @remark
	/// - Description : Set dp values with constant values. Calls dpToPx() method.
	/// @see kookmin.cs.fouram.nurumikeyboard.inputmethod.MotionKeyboardView#dpToPx()
	/////////////////////////////////////////////
	private void setDpValues() {
		Log.i("IME_LOG", "Location : MotionKeyboardView - setDpValues()");
		standardCircleSize = (int) dpToPx(STD_CIRCLE_SIZE, ctx.getApplicationContext());
		Log.v("IME_LOG", "Process : setDpValues(). Standard circle size : " + standardCircleSize);		
		innerCircleSize = (int) dpToPx(INNER_CIRCLE_SIZE, ctx.getApplicationContext());
		Log.v("IME_LOG", "Process : setDpValues(). Inner circle size    : " + innerCircleSize);		
		swipeThreshold = (int) dpToPx(SWIPE_MIN_DISTANCE, ctx.getApplicationContext());
		Log.v("IME_LOG", "Process : setDpValues(). Swipe Threshold      : " + swipeThreshold);
	}

	/////////////////////////////////////////////
	/// @fn dpToPx
	/// @brief Converts dp value to px value.
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
		Log.i("IME_LOG", "Location : MotionKeyboardView - initialize()");
	
		pnt = new Paint();
		numFingers = NurumiIME.FIVE_FINGERS;

		motionStartFlag = false;
		standardPositionFlag = false;
		inputStartFlag = false;

		motion = new int[numFingers];

		circleAvailable = new boolean[numFingers];

		plp = new LinkedList<PtIdLinkedWithPtIndex>();
		clp = new ArrayList<CircleLinkedWithPtId>();

		startPtArr = new ArrayList<PointF>();
		oldPtArr = new ArrayList<PointF>();
		ptArr = new ArrayList<PointF>();

		startPtArr.clear();
		
		clearLists();
	}
	
	/////////////////////////////////////////////
	/// @fn clearLists
	/// @brief Clear lists.
	/// @remark
	/// - Description : This method clears all lists except standard position list.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void clearLists() {
		oldPtArr.clear();
		ptArr.clear();
		clp.clear();
		plp.clear();
		for(int i = 0; i < numFingers; i++)
			circleAvailable[i] = true;
	}

	public MotionKeyboardView(Context context) {
		super(context);
		Log.i("IME_LOG", "Location : MotionKeyboardView - Constructor 2");
		this.ctx = context;
	}

	/////////////////////////////////////////////
	/// @fn setBitmap
	/// @brief Set bitmap images. 
	/// @remark
	/// - Description : Set bitmap images for directions. calls setImgToBitmap
	/// @see kookmin.cs.fouram.nurumikeyboard.inputmethod.MotionKeyboardView#setImgToBitmap()
	/////////////////////////////////////////////
	private void setBitmap() {
		Log.i("IME_LOG", "Location : MotionKeyboardView - setBitmap()");		
		dotImg = setImgToBitmap(R.drawable.img_finger_dot, innerCircleSize);
		upImg = setImgToBitmap(R.drawable.img_finger_up, innerCircleSize);
		downImg = setImgToBitmap(R.drawable.img_finger_down, innerCircleSize);
		leftImg = setImgToBitmap(R.drawable.img_finger_left, innerCircleSize);
		rightImg = setImgToBitmap(R.drawable.img_finger_right, innerCircleSize);
		stdCircleImg = setImgToBitmap(R.drawable.img_std_circle, standardCircleSize);
	}
	
	/////////////////////////////////////////////
	/// @fn setImgToBitmap
	/// @brief returns bitmap image  
	/// @remark
	/// - Description : This method will return bitmap image.
	/// @param imgId image ID that starts with R.drawable.~ 
	/// @param halfSize half size of image
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	Bitmap setImgToBitmap(int imgId, int halfSize) {
		BitmapDrawable drawable;
		Bitmap bitmap;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			drawable = (BitmapDrawable) getResources().getDrawable(imgId, null);
		else 
			drawable = (BitmapDrawable) getResources().getDrawable(imgId);
		
		bitmap = Bitmap.createScaledBitmap(drawable.getBitmap(), halfSize*2, halfSize*2, true);
		drawable.setCallback(null);
		drawable.getBitmap().recycle();
		return bitmap;
	}
	/////////////////////////////////////////////
	/// @fn onDestroyView
	/// @brief Free all objects in this view.
	/// @remark
	/// - Description : To evade memory leak user have to free up Bitmap object. Android OS will not free up all Bitmaps.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void onDestroyView() {
		Log.i("IME_LOG", "Location : MotionKeyboardView - onDestroyView()");
		try {
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
		} catch (Exception ignore) {}
		setIme(null);
	}

	/////////////////////////////////////////////
	/// @fn setIme
	/// @brief Set parent IME
	/// @remark
	/// - Description : Set parent ime and link with IME variable MKeyboard View.\n
	/// @param ime Parent IME
	/////////////////////////////////////////////
	protected void setIme(NurumiIME ime) {
		Log.i("IME_LOG", "Location : MotionKeyboardView - setIME()");
		this.ime = ime;		
	}

	/////////////////////////////////////////////
	/// @fn onDraw
	/// @brief (Override method) Screen drawing function.
	/// @remark
	/// - Description : Draw 5 or 10 start point circles and touched point circles.\n
	/////////////////////////////////////////////
	@Override
	public void onDraw(Canvas canvas) { // No log for this method because this is real-time method so it updates frequently.
		super.onDraw(canvas);
		canvas.drawColor(Color.TRANSPARENT);
		
		if(startPtArr.isEmpty())
			return;
		
		/* standard position */
		for(PointF spt : startPtArr) {
			float pointX = spt.x - standardCircleSize;
			float pointY = spt.y - standardCircleSize;
			canvas.drawBitmap(stdCircleImg, pointX, pointY, pnt);
		}

		if(oldPtArr.isEmpty()) // cannot go upward because the standard position
			return;			   // must always be drawn.
		/* down event position */	
		for (PointF pt : oldPtArr) {
			int circleNum = checkTouchedCircle((int)pt.x, (int)pt.y);
			PointF spt = startPtArr.get(circleNum-1);

			/// Start points are the center points of circles.
			/// Images will be drawn from upper left corner.
			float pointX = spt.x - innerCircleSize;
			float pointY = spt.y - innerCircleSize;

			switch(motion[circleNum-1])	{				
				case DIRECTION_DOT :		
					canvas.drawBitmap(dotImg, pointX, pointY, pnt);
					break;
				case DIRECTION_UP :
					canvas.drawBitmap(upImg, pointX, pointY, pnt);
					break;
				case DIRECTION_DOWN :
					canvas.drawBitmap(downImg, pointX, pointY, pnt);
					break;
				case DIRECTION_LEFT :
					canvas.drawBitmap(leftImg, pointX, pointY, pnt);
					break;
				case DIRECTION_RIGHT :
					canvas.drawBitmap(rightImg, pointX, pointY, pnt);
					break;
				default :
					break;
			} // switch end
		} // oldPtArr for-each end
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
	/// @return Returns boolean value whether the touch event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean onTouchEvent (MotionEvent e)	{
		int action = e.getAction() & MotionEvent.ACTION_MASK;
		if(standardPositionFlag == false)
			return startMultiTouch(e);
		int touchCount = e.getPointerCount();
		if(touchCount > numFingers)
			return false;		
		int circleNum = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
		
		// all cases meets return command. So There is no break command.
		switch(action) {		
			case MotionEvent.ACTION_DOWN :
			case MotionEvent.ACTION_POINTER_DOWN :
				if( circleDown(e, circleNum, touchCount, action) == false )
					return false;
				break;
				
			case MotionEvent.ACTION_MOVE :
				ptArr.clear();
				plp.clear();
				
				for (int pointerIndex = 0; pointerIndex < touchCount; pointerIndex++) {
					PointF ptf = new PointF(e.getX(pointerIndex), e.getY(pointerIndex));
					ptArr.add(ptf);					
					PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex(e.getPointerId(pointerIndex), pointerIndex);
					plp.add(pp); // Link pointerID and pointer index.
					checkDirection(pp, ptf);
				}
				invalidate();
				break;
				
			case MotionEvent.ACTION_POINTER_UP :
				Log.v("IME_LOG", "Process : onTouchEvent() . Finger detached");
				inputStartFlag = false; // stop getting new touch				
				break;
				
			case MotionEvent.ACTION_UP : // No point left.
				if(!motionStartFlag && standardPositionFlag) { // If it is first touch to set standard position,
					motionStartFlag = true;	 // do nothing.
					break;
				}
				motionCheck();
				/* initialization for next motion */
				clearLists();
				performClick();
				invalidate();					
				break;
				
			case MotionEvent.ACTION_CANCEL : // cancel all motions and initialize
				clearLists();
				//no break or return for sharing part.
			default :
				invalidate();
				return false;
		} // switch end		
		return true;
	} // onTouchEvent fin

	
	/////////////////////////////////////////////
	/// @fn circleDown
	/// @brief called when touch event is ACTION_DOWN or ACTION_POINTER_DOWN 
	/// @remark
	/// - Description : In ACTION_DOWN motion event, this method will initialize motion array.\n 
	/// And inputStartFlag will be true. In ACTION_DOWN, ACTION_POINTER_DOWN motion events, this method will\n
	/// save the position where down event occurred, and link pointerId with circle number.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean circleDown(MotionEvent e, int circleNum, int touchCount, int action) {
		if(circleNum == INVALID_CIRCLE) { // If circle is not touched, return
			invalidate(); 
			return false;
		}
		if(action == MotionEvent.ACTION_DOWN) { // If it is initial down action
			for(int i = 0; i < numFingers; i++) // initialize motion array
				motion[i] = DIRECTION_EMPTY;
			inputStartFlag = true;				// motion input start
			Log.d("IME_LOG", "Process : circleDown(). Motion input start");
		}
		
		if(!inputStartFlag || !circleAvailable[circleNum-1]) {
			invalidate(); // If motionStartFlag is false
			return false; // or the touched circle is already being used
		}
		Log.v("IME_LOG", "Process : circleDown(). Finger added");
		
		circleAvailable[circleNum-1] = false; // One finger in one circle.
		motion[circleNum-1] = DIRECTION_DOT;

		oldPtArr.add(new PointF(e.getX(touchCount-1), e.getY(touchCount-1))); // Add start position.
		clp.add(new CircleLinkedWithPtId(e.getPointerId(touchCount-1), circleNum)); // Link pointerID and circle number.

		invalidate();
		return true;
	}
	
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
	private void motionCheck() {
		Log.i("IME_LOG", "Location : MotionKeyboardView - motionCheck()");
		int checkEmpty = numFingers;
		for(int i = 0; i < numFingers; i++)
			checkEmpty += motion[i]; // Empty motion value is -1.
		Log.d("IME_LOG", "Process : motionCheck(). Motion input end"); 
		if(checkEmpty == 0)
			return;
		/* key event will be here. */
		
		ime.onFinishGesture(motionAryToLong(motion));
	}
	
	private long motionAryToLong(int[] motion) {
		long convertedValue = 0L;
		
		if(motion[0] == DIRECTION_DOT)
			convertedValue += 1L;
		else if(motion[0] == DIRECTION_UP)
			convertedValue += 2L;
		else if(motion[0] == DIRECTION_DOWN)
			convertedValue += 4L;
		else if(motion[0] == DIRECTION_LEFT)
			convertedValue += 8L;
		else if(motion[0] == DIRECTION_RIGHT)
			convertedValue += 16L;
		
		if(motion[1] == DIRECTION_DOT)
			convertedValue += 32L;
		else if(motion[1] == DIRECTION_UP)
			convertedValue += 64L;
		else if(motion[1] == DIRECTION_DOWN)
			convertedValue += 128L;
		else if(motion[1] == DIRECTION_LEFT)
			convertedValue += 256L;
		else if(motion[1] == DIRECTION_RIGHT)
			convertedValue += 512L;
		
		if(motion[2] == DIRECTION_DOT)
			convertedValue += 1024L;
		else if(motion[2] == DIRECTION_UP)
			convertedValue += 2048L;
		else if(motion[2] == DIRECTION_DOWN)
			convertedValue += 4096L;
		else if(motion[2] == DIRECTION_LEFT)
			convertedValue += 8192L;
		else if(motion[2] == DIRECTION_RIGHT)
			convertedValue += 16384L;
		
		if(motion[3] == DIRECTION_DOT)
			convertedValue += 32768L;
		else if(motion[3] == DIRECTION_UP)
			convertedValue += 65536L;
		else if(motion[3] == DIRECTION_DOWN)
			convertedValue += 131072L;
		else if(motion[3] == DIRECTION_LEFT)
			convertedValue += 262144L;
		else if(motion[3] == DIRECTION_RIGHT)
			convertedValue += 524288L;
		
		if(motion[4] == DIRECTION_DOT)
			convertedValue += 1048576L;
		else if(motion[4] == DIRECTION_UP)
			convertedValue += 2097152L;
		else if(motion[4] == DIRECTION_DOWN)
			convertedValue += 4194304L;
		else if(motion[4] == DIRECTION_LEFT)
			convertedValue += 8388608L;
		else if(motion[4] == DIRECTION_RIGHT)
			convertedValue += 16777216L;
		
		return convertedValue;
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
		// No log because this method will be called too frequently.
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
	/// - Description : If 'numFingers' fingers are touched, set 'start' flag true and start multi touch motion recognition.\n
	/// Set 'numFingers' of starting points.
	/// @param e A motion event
	/// @return Returns the boolean value of motion event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean startMultiTouch(MotionEvent e) {
		startPtArr.clear();
		if ( e.getAction() != MotionEvent.ACTION_DOWN && e.getAction() != MotionEvent.ACTION_MOVE ) 
			return false;
		
		Log.i("IME_LOG", "Location : MotionKeyboardView - startMultiTouch()");
		int touchCount = e.getPointerCount();		
		if(touchCount == numFingers) {
			standardPositionFlag = true;
			Log.d("IME_LOG", "Process : motionCheck(). standardPositionFlag : " + standardPositionFlag);
			
			for (int i = 0; i < touchCount; i++)
				startPtArr.add(new PointF(e.getX(i), e.getY(i)));
			Collections.sort(startPtArr, comparator);
		}
		return true;		
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
		// No log because this method will be called too frequently.
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

		float distanceX = Math.abs(oldPt.x - pt.x);
		float distanceY = Math.abs(oldPt.y - pt.y);

		if( distanceX < swipeThreshold && distanceY < swipeThreshold )
			motion[circleNum-1] = DIRECTION_DOT;
		else if( distanceY/distanceX < 1 && distanceX > swipeThreshold) // Gradient is smaller than 1.
			motion[circleNum-1] = ( (oldPt.x > pt.x) ? DIRECTION_LEFT : DIRECTION_RIGHT );
		else if( distanceY/distanceX >= 1 && distanceY > swipeThreshold) // Gradient is 1 or larger.			
			motion[circleNum-1] = ( (oldPt.y > pt.y) ? DIRECTION_UP : DIRECTION_DOWN );
	} // checkDirection fin
}
