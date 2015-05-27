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
	
	abstract public void giyeok(); //ぁ
	abstract public void nieun(); //い
	abstract public void digut(); //ぇ
	abstract public void lieul(); //ぉ
	abstract public void mieum(); //け
	abstract public void beeup(); //げ
	abstract public void seeot(); //さ
	abstract public void yieung(); //し
	abstract public void jieut(); //じ
	abstract public void chiut(); //ず
	abstract public void keeuk(); //せ
	abstract public void teeut(); //ぜ
	abstract public void peeup(); //そ
	abstract public void heeut(); //ぞ
	
	abstract public void dbKiyoek(); //あ
	abstract public void dbDigut(); //え
	abstract public void dbBeeup(); //こ
	abstract public void dbSeeot(); //ざ
	abstract public void dbJieut(); //す
	
	abstract public void ah(); //た
	abstract public void ya(); //ち
	abstract public void uh(); //っ
	abstract public void yuh(); //づ
	abstract public void o(); //で
	abstract public void yo(); //に
	abstract public void u(); //ぬ
	abstract public void yu(); //ば
	abstract public void eu(); //ぱ
	abstract public void yi(); //び
	abstract public void ae(); //だ
	abstract public void e(); //つ
	abstract public void yae(); //ぢ
	abstract public void yeh(); //て
	abstract public void wa(); //と
	abstract public void wae(); //ど
	abstract public void woe(); //な
	abstract public void wuh(); //ね
	abstract public void weh(); //の
	abstract public void wui(); //は
	abstract public void ui(); //ひ
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
