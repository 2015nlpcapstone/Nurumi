package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;


/////////////////////////////////////////////
/// @class IME_Automata
///com.fouram.nurumikeyboard.NurumiIME \n
///   ㄴ IME_Automata.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 5. 20. |
/// @section Description
///  - An abstract class for various input method automata
/// 
/////////////////////////////////////////////
public abstract class IME_Automata {
	
	/////////////////////////////////////////////
	/// @fn isAllocatedMotion
	/// @brief Function information : Check motion array.
	/// @remark
	/// - Description : This method will check whether the motion is allocated in automata or not.
	/// @param finger_array : motion array.
	/// @return If motion is allocated, return true. If not, return false.
	/////////////////////////////////////////////
	public abstract boolean isAllocatedMotion(int[] finger_array);
	
	/////////////////////////////////////////////
	/// @fn execute
	/// @brief Function information : Get motion and returns output string.
	/// @remark
	/// - Description : returns output string from motion input.
	/// @param finger_array : motion array
	/// @param input_connection : InputConnection in InputMethodService.
	/// @return Output string.
	/////////////////////////////////////////////
	public abstract String execute(int[] finger_array, InputConnection input_connection);
	
	public final boolean ENABLE_DEBUG = true;
	  // 0.ㄱ 1.ㄲ 2.ㄴ 3.ㄷ 4.ㄸ 5.ㄹ 6.ㅁ 7.ㅂ 8.ㅃ 9.ㅅ 10.ㅆ 11.ㅇ 12.ㅈ 13.ㅉ 14.ㅊ 15.ㅋ 16.ㅌ 17.ㅍ 18ㅎ
	protected final int[] PREF_CHO = {12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610,
	      			  12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622};
	  // 0.ㅏ 1.ㅐ 2.ㅑ 3.ㅒ 4.ㅓ 5.ㅔ 6.ㅕ 7.ㅖ 8.ㅗ 9.ㅘ 10.ㅙ 11.ㅚ 12.ㅛ 13.ㅜ 14.ㅝ 15.ㅞ 16.ㅟ 17.ㅠ 18.ㅡ 19.ㅢ 20.ㅣ
	protected final int[] PREF_JUNG = {12623, 12624, 12625, 12626, 12627, 12628, 12629, 12630,
	    12631, 12632, 12633, 12634, 12635, 12636, 12637, 12638, 12639, 12640, 12641, 12642, 12643};
	  // 0.ㄱ 1.ㄲ 2.ㄳ 3.ㄴ 4.ㄵ 5.ㄶ 6.ㄷ 7.ㄹ 8.ㄺ 9.ㄻ 10.ㄼ 11.ㄽ 12.ㄾ 13.ㄿ 14.ㅀ 15.ㅁ 16.ㅂ 17.ㅄ
	  // 18.ㅅ 19.ㅆ 20.ㅇ 21.ㅈ 22.ㅊ 23.ㅋ 24.ㅌ 25.ㅍ 26.ㅎ
	protected final int[] PREF_JONG = {12593, 12594, 12595, 12596, 12597, 12598, 12599, 12601,
	     12602, 12603, 12604, 12605, 12606, 12607, 12608, 12609, 12610, 12612, 12613, 12614, 12615,
	     												 12616, 12618, 12619, 12620, 12621, 12622};
	protected final int AC00 = 44032;
	
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
	
	protected int count_finger = 0;
	protected String text_to_commit = null;
	protected int[] finger;
	protected InputConnection ic;
	
	public void finalize() {
		Log.i("IME_LOG", "Location : IME_Automata - finalize()");
	}
}
