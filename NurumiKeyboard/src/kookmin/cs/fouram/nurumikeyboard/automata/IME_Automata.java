package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;


/////////////////////////////////////////////
/// @class IME_Automata
/// kookmin.cs.fouram.nurumikeyboard.automata \n
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
	public abstract boolean isAllocatedMotion(long finger_array);
	
	/////////////////////////////////////////////
	/// @fn execute
	/// @brief Function information : Get motion and returns output string.
	/// @remark
	/// - Description : returns output string from motion input.
	/// @param finger_array : motion array
	/// @param input_connection : InputConnection in InputMethodService.
	/// @return Output string.
	/////////////////////////////////////////////
	public abstract String execute(long motion, InputConnection input_connection);
	
	protected String text_to_commit = null;
	protected long motion;
	protected int[] finger;
	protected InputConnection ic;
	
	public boolean isEnter(long motion) {
		if(motion == 1048577L)
			return true;
		return false;
	}
	
	public void finalize() {
		Log.i("IME_LOG", "Location : IME_Automata - finalize()");
	}
}
