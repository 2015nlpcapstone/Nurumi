package com.fouram.nurumikeyboard.AutoTest;

//import android.util.Log;
import android.view.inputmethod.InputConnection;

import com.fouram.nurumikeyboard.IME_Automata.*;

public class InputType1 extends MotionInput {

	public InputType1(InputConnection ic) {
		super(ic);
		automata = new Automata_type_Kor_1();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void giyeok() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void nieun() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void digut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,-1,0,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,-1,0,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void lieul() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,-1,-1,0},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,-1,-1,0},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void mieum() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void beeup() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,0,0,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,0,0,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void seeot() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,0,0,0,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,0,0,0,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yieung() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,0,0,0},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,0,0,0},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void jieut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,0,0,0,0},ic)),1);
		/*
		str = automata.execute(new int[] {-1,0,0,0,0},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void chiut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,D_UP,D_UP,D_UP,D_UP},ic)),1);
		/*
		str = automata.execute(new int[] {-1,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void keeuk() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,3,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,3,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}	

	@Override
	public void teeut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,-1,3,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,-1,3,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void peeup() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,3,3,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,3,3,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void heeut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,3,3,3},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,3,3,3},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void dbKiyoek() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,1,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,1,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void dbDigut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,-1,1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,-1,1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void dbBeeup() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,-1,1,1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,-1,1,1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void dbSeeot() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,1,1,1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,1,1,1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void dbJieut() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {-1,1,1,1,1},ic)),1);
		/*
		str = automata.execute(new int[] {-1,1,1,1,1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void ah() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void ya() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void uh() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yuh() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void o() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yo() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void u() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yu() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void eu() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yi() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void ae() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void e() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void yeh() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void wa() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void wae() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void woe() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void wuh() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void weh() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void wui() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}
	public void ui() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,-1,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,-1,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

	@Override
	public void yae() {
		// TODO Auto-generated method stub
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,-1,-1,-1},ic)),1);
		ic.commitText(String.valueOf(automata.execute(new int[] {0,0,0,-1,-1},ic)),1);
		/*
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,-1,-1,-1},ic);
		str = automata.execute(new int[] {0,0,0,-1,-1},ic);
		Log.i("TestResult", "TestResult : " + str);
		*/
	}

}