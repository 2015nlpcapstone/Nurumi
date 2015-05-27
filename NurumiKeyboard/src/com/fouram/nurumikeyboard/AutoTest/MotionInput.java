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
		
		yieung(); ah(); // æ∆
		enter();
		yieung(); ah(); giyeok(); // æ«
		enter();
		yieung(); ah(); keeuk(); // ù⁄
		enter();
		yieung(); ah(); dbKiyoek(); // ù–
		enter();
		yieung(); yeh(); giyeok(); // ûë
		enter();
		yieung(); yeh(); giyeok(); seeot();// ûì
		enter();
		yieung(); yeh(); lieul(); teeut();// ûõ
		
		/*
		dbJieut(); ah(); // ¬•
		jieut(); ah(); yieung(); // ¿Â
		mieum(); yuh(); nieun(); // ∏È
		enter();
		
		beeup(); u(); //∫Œ
		yieung(); uh(); keeuk(); //æ˝
		enter();
		
		beeup(); ah(); lieul(); beeup(); //π‚
		giyeok(); o(); // ∞Ì
		enter();
		
		giyeok(); ah(); beeup(); seeot(); //∞™
		*/
	};
	
	abstract public void giyeok(); //§°
	abstract public void nieun(); //§§
	abstract public void digut(); //§ß
	abstract public void lieul(); //§©
	abstract public void mieum(); //§±
	abstract public void beeup(); //§≤
	abstract public void seeot(); //§µ
	abstract public void yieung(); //§∑
	abstract public void jieut(); //§∏
	abstract public void chiut(); //§∫
	abstract public void keeuk(); //§ª
	abstract public void teeut(); //§º
	abstract public void peeup(); //§Ω
	abstract public void heeut(); //§æ
	
	abstract public void dbKiyoek(); //§¢
	abstract public void dbDigut(); //§®
	abstract public void dbBeeup(); //§≥
	abstract public void dbSeeot(); //§∂
	abstract public void dbJieut(); //§π
	
	abstract public void ah(); //§ø
	abstract public void ya(); //§¡
	abstract public void uh(); //§√
	abstract public void yuh(); //§≈
	abstract public void o(); //§«
	abstract public void yo(); //§À
	abstract public void u(); //§Ã
	abstract public void yu(); //§–
	abstract public void eu(); //§—
	abstract public void yi(); //§”
	abstract public void ae(); //§¿
	abstract public void e(); //§ƒ
	abstract public void yae(); //§¬
	abstract public void yeh(); //§∆
	abstract public void wa(); //§»
	abstract public void wae(); //§…
	abstract public void woe(); //§ 
	abstract public void wuh(); //§Õ
	abstract public void weh(); //§Œ
	abstract public void wui(); //§œ
	abstract public void ui(); //§“
	public void empty() {
		automata.execute(new int[] {-1,-1,-1,-1,-1}, ic);
	}
	public void enter() {
		automata.execute(new int[] {0,-1,-1,-1,0}, ic);
		Log.d("TestResult", "next----------------------");
	}
}
