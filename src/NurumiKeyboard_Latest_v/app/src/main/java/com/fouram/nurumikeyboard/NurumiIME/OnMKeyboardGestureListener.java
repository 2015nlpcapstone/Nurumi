package com.fouram.nurumikeyboard.NurumiIME;

/////////////////////////////////////////////
/// @class OnMKeyboardGestureListener
///com.fouram.nurumikeyboard.NurumiIME \n
///   ¤¤ OnMKeyboardGestureListener.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 4. 2. |
/// @section Description
/// - An gesture listening interface for NurumiIME.\n 
/// Keyboard view will find and set it's parent InputMethodService by InputMethodService() method.\n
/// When the gesture input is finished, onFinishGesture() method will be called.
/// InputMethodService will implement this interface to get gesture input.
/////////////////////////////////////////////
public interface OnMKeyboardGestureListener {	
	/////////////////////////////////////////////
	/// @fn onFinishGesture
	/// @brief Listener of gesture input 
	/// @remark
	/// - Description : When the gesture from the keyboard view is finished, this method will be called.
	/// @param motion Motion array which has result of motion
	/////////////////////////////////////////////
	public void onFinishGesture(int[] motion);
}
