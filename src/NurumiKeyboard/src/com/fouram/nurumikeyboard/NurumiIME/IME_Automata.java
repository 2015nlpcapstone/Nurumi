package com.fouram.nurumikeyboard.NurumiIME;

import android.view.inputmethod.InputConnection;


/////////////////////////////////////////////
/// @class IME_Automata
///com.fouram.nurumikeyboard.NurumiIME \n
///   ¤¤ IME_Automata.java
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
	protected abstract String execute(int[] finger_array, InputConnection input_connection);
	
	protected static final int DIRECTION_EMPTY = -1;
	protected static final int DIRECTION_DOT = 0;
	protected static final int DIRECTION_DOWN = 1;
	protected static final int DIRECTION_LEFT = 2;
	protected static final int DIRECTION_UP = 3;
	protected static final int DIRECTION_RIGHT = 4;
}
