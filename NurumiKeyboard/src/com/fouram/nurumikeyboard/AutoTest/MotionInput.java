package com.fouram.nurumikeyboard.AutoTest;

import android.util.Log;
import android.view.inputmethod.InputConnection;


//import android.view.inputmethod.InputConnection;
import com.fouram.nurumikeyboard.IME_Automata.*;


public abstract class MotionInput {
	
	protected IME_Automata automata;
	protected String str=null;
	protected InputConnection ic;
	
	public MotionInput(InputConnection ic) {
		this.ic = ic;
	}
	
	protected static final int D_EPT = -1;
	protected static final int D_DOT = 0;
	protected static final int D_DOWN = 1;
	protected static final int D_LEFT = 2;
	protected static final int D_UP = 3;
	protected static final int D_RIGHT = 4;
	
	public void test() {
		
		giyeok(); space(); nieun(); space(); digut(); space(); lieul(); space(); mieum(); enter();
		beeup(); space(); seeot(); space(); yieung(); space(); jieut(); space(); chiut(); enter();
		keeuk(); space(); teeut(); space(); peeup(); space(); heeut(); enter();
		dbKiyoek(); space(); dbDigut(); space(); dbBeeup(); space(); dbSeeot(); space(); dbJieut(); enter();
		ah(); space(); ya(); space(); uh(); space(); yuh(); space(); o(); yo(); enter();
		u(); space(); yu(); space(); eu(); space(); yi(); space(); ae(); space(); e(); enter();
		yae(); space(); yeh(); space(); wa(); space(); wae(); enter();
		woe(); space(); wuh(); space(); weh(); space(); wui(); space(); ui();
		
		/*
		nieun(); ah(); space();
		nieun(); ya(); space();
		nieun(); uh(); space();
		nieun(); yuh(); space();
		nieun(); o(); space();
		nieun(); yo(); space();
		nieun(); u(); space();
		nieun(); yu(); space();
		nieun(); eu(); space();
		nieun(); yi(); space();
		nieun(); ae(); space();
		nieun(); e(); space();
		nieun(); yae(); space();
		nieun(); yeh(); space();
		nieun(); wa(); space();
		nieun(); wae(); space();
		nieun(); woe(); space();
		nieun(); wuh(); space();
		nieun(); weh(); space();
		nieun(); wui(); space();
		nieun(); ui(); space();
		*/
		/*
		beeup(); yae(); giyeok(); space();
		beeup(); yae(); dbKiyoek(); space();
		beeup(); yae(); giyeok(); seeot(); space();
		beeup(); yae(); nieun(); space();
		beeup(); yae(); nieun(); jieut(); space();
		beeup(); yae(); nieun(); heeut(); space();
		beeup(); yae(); digut(); space();
		
		beeup(); yae(); lieul(); space();
		beeup(); yae(); lieul(); giyeok(); space();
		beeup(); yae(); lieul(); mieum(); space();		
		beeup(); yae(); lieul(); beeup(); space();
		beeup(); yae(); lieul(); seeot(); space();
		beeup(); yae(); lieul(); teeut(); space();
		beeup(); yae(); lieul(); peeup(); space();
		beeup(); yae(); lieul(); heeut(); space();
		
		beeup(); yae(); mieum(); space();
		beeup(); yae(); beeup(); space();
		beeup(); yae(); beeup(); seeot(); space();
		beeup(); yae(); seeot(); space();
		beeup(); yae(); dbSeeot(); space();
		beeup(); yae(); yieung(); space();
		beeup(); yae(); jieut(); space();
		beeup(); yae(); chiut(); space();
		beeup(); yae(); keeuk(); space();
		beeup(); yae(); teeut(); space();
		beeup(); yae(); peeup(); space();
		beeup(); yae(); heeut(); space();
		*/
		/*
		nieun(); empty(); ah(); space();
		nieun(); ah(); empty(); nieun(); space();
		nieun(); ah(); nieun(); empty(); ah(); space();
		nieun(); ah(); nieun(); jieut(); empty(); ah(); space();
		nieun(); ah(); nieun(); heeut(); ya(); yieung(); empty(); ah(); space();
		*/
	};
	
	abstract public void giyeok(); //��
	abstract public void nieun(); //��
	abstract public void digut(); //��
	abstract public void lieul(); //��
	abstract public void mieum(); //��
	abstract public void beeup(); //��
	abstract public void seeot(); //��
	abstract public void yieung(); //��
	abstract public void jieut(); //��
	abstract public void chiut(); //��
	abstract public void keeuk(); //��
	abstract public void teeut(); //��
	abstract public void peeup(); //��
	abstract public void heeut(); //��
	
	abstract public void dbKiyoek(); //��
	abstract public void dbDigut(); //��
	abstract public void dbBeeup(); //��
	abstract public void dbSeeot(); //��
	abstract public void dbJieut(); //��
	
	abstract public void ah(); //��
	abstract public void ya(); //��
	abstract public void uh(); //��
	abstract public void yuh(); //��
	abstract public void o(); //��
	abstract public void yo(); //��
	abstract public void u(); //��
	abstract public void yu(); //��
	abstract public void eu(); //��
	abstract public void yi(); //��
	abstract public void ae(); //��
	abstract public void e(); //��
	abstract public void yae(); //��
	abstract public void yeh(); //��
	abstract public void wa(); //��
	abstract public void wae(); //��
	abstract public void woe(); //��
	abstract public void wuh(); //��
	abstract public void weh(); //��
	abstract public void wui(); //��
	abstract public void ui(); //��
	public void space() {
		automata.execute(new int[] {D_RIGHT,-1,-1,-1,-1}, ic);
	}
	public void empty() {
		automata.execute(new int[] {-1,-1,-1,D_UP,-1}, ic);
	}
	
	public void enter() {
		automata.execute(new int[] {0,-1,-1,-1,0}, ic);
		Log.d("TestResult", "next----------------------");
	}
}
