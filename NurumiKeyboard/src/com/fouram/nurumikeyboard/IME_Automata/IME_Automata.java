package com.fouram.nurumikeyboard.IME_Automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;


/////////////////////////////////////////////
/// @class IME_Automata
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� IME_Automata.java
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
	/// @fn execute
	/// @brief Function information : Abstract method which returns output string from motion input.
	/// @remark
	/// - Description : 
	/// @param finger_array
	/// @param input_connection
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public abstract String execute(int[] finger_array, InputConnection input_connection);
	
	public final boolean ENABLE_DEBUG = true;
	  // 0.�� 1.�� 2.�� 3.�� 4.�� 5.�� 6.�� 7.�� 8.�� 9.�� 10.�� 11.�� 12.�� 13.�� 14.�� 15.�� 16.�� 17.�� 18��
	protected final int[] PREF_CHO = {12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610,
	      			  12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622};
	  // 0.�� 1.�� 2.�� 3.�� 4.�� 5.�� 6.�� 7.�� 8.�� 9.�� 10.�� 11.�� 12.�� 13.�� 14.�� 15.�� 16.�� 17.�� 18.�� 19.�� 20.��
	protected final int[] PREF_JUNG = {12623, 12624, 12625, 12626, 12627, 12628, 12629, 12630,
	    12631, 12632, 12633, 12634, 12635, 12636, 12637, 12638, 12639, 12640, 12641, 12642, 12643};
	  // 0.�� 1.�� 2.�� 3.�� 4.�� 5.�� 6.�� 7.�� 8.�� 9.�� 10.�� 11.�� 12.�� 13.�� 14.�� 15.�� 16.�� 17.��
	  // 18.�� 19.�� 20.�� 21.�� 22.�� 23.�� 24.�� 25.�� 26.��
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
	protected String str_to_write = null;
	protected int[] finger;
	protected InputConnection ic;
	
	public void finalize() {
		Log.d("GC Free", "finalize automata object");
	}
}
