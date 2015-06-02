package com.fouram.nurumikeyboard.AutoTest;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class AutomataTest {
	private InputConnection ic;
	MotionInput inputType;
	
	public AutomataTest(InputConnection ic) {
		this.ic = ic;
	}
	
	public void testAutomata() {
		/*
		Log.d("TestResult", "-----Automata 1 Start-----");
		inputType = new InputType1(ic);
		inputType.test();
		Log.d("TestResult", "-----Automata 1 End-----");
		*/
		
		/*
		Log.d("TestResult", "-----Automata 2 Start-----");
		inputType = new InputType2(ic);
		inputType.test();
		Log.d("TestResult", "-----Automata 2 End-----");
		*/
		
		
		Log.d("TestResult", "-----Automata 3 Start-----");
		inputType = new InputType3(ic);
		inputType.test();
		Log.d("TestResult", "-----Automata 3 End-----");
		
	}
}
