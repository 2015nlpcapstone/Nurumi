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

		giyeok(); space(); nieun(); space(); digut(); space(); lieul(); space(); mieum(); space();
		beeup(); space(); seeot(); space(); yieung(); space(); jieut(); space(); chiut(); space();
		keeuk(); space(); teeut(); space(); peeup(); space(); heeut(); space();
		dbKiyoek(); space(); dbDigut(); space(); dbBeeup(); space(); dbSeeot(); space(); dbJieut(); enter();
		ah(); space(); ya(); space(); uh(); space(); yuh(); space(); o(); space(); yo(); space();
		u(); space(); yu(); space(); eu(); space(); yi(); space(); ae(); space(); e(); space();
		yae(); space(); yeh(); space(); wa(); space(); wae(); space();
		woe(); space(); wuh(); space(); weh(); space(); wui(); space(); ui(); enter();


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
		nieun(); ui(); enter();


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
		beeup(); yae(); heeut(); enter();

		yieung(); u(); lieul(); teeut(); ah(); lieul(); yi(); space(); // 울타리
		seeot(); ah(); lieul(); mieum(); yieung(); ui();
		heeut(); yuh(); nieun(); jieut(); ah(); yieung(); enter(); //삶의현장
	};

	abstract public void giyeok(); // ㄱ
	abstract public void nieun(); // ㄴ
	abstract public void digut(); // ㄷ
	abstract public void lieul(); // ㄹ
	abstract public void mieum(); // ㅁ
	abstract public void beeup(); // ㅂ
	abstract public void seeot(); // ㅅ
	abstract public void yieung(); // ㅇ
	abstract public void jieut(); // ㅈ
	abstract public void chiut(); // ㅊ
	abstract public void keeuk(); // ㅋ
	abstract public void teeut(); // ㅌ
	abstract public void peeup(); // ㅍ
	abstract public void heeut(); // ㅎ

	abstract public void dbKiyoek(); // ㄲ
	abstract public void dbDigut(); // ㄸ
	abstract public void dbBeeup(); // ㅃ
	abstract public void dbSeeot(); // ㅆ
	abstract public void dbJieut(); // ㅉ

	abstract public void ah(); // ㅏ
	abstract public void ya(); // ㅑ
	abstract public void uh(); // ㅓ
	abstract public void yuh(); // ㅕ
	abstract public void o(); // ㅗ
	abstract public void yo(); // ㅛ
	abstract public void u(); // ㅜ
	abstract public void yu(); // ㅠ
	abstract public void eu(); // ㅡ
	abstract public void yi(); // ㅣ
	abstract public void ae(); // ㅐ
	abstract public void e(); // ㅔ
	abstract public void yae(); // ㅒ
	abstract public void yeh(); // ㅖ
	abstract public void wa(); // ㅘ
	abstract public void wae(); // ㅙ
	abstract public void woe(); // ㅚ
	abstract public void wuh(); // ㅝ
	abstract public void weh(); // ㅞ
	abstract public void wui(); // ㅟ
	abstract public void ui(); // ㅢ
	public void space() {
		automata.execute(new int[] {D_RIGHT,-1,-1,-1,-1}, ic);
		ic.commitText(" ", 1);
	}

	public void enter() {
		automata.execute(new int[] {0,-1,-1,-1,0}, ic);
		ic.commitText("\n", 1);
		//Log.d("TestResult", "next----------------------");
	}
}
