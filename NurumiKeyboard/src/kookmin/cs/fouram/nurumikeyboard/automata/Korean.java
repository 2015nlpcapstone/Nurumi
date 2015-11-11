package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;
import android.view.inputmethod.InputConnection;

public abstract class Korean extends IME_Automata {
	
	protected final int AC00 = 44032; // 한글 유니코드 계산 공식에 사용되는 상수
	
	protected final int CHO_SUNG = 1;
	protected final int JUNG_SUNG = 2;
	protected final int JONG_SUNG = 3;
		
	protected final int JAEUM = 0;
	protected final int MOEUM = 1;
	protected final int SPC_JAEUM = 2;
	
	// 자음	
	protected final KoreanCharacter GIYEOK = new Consonant('ㄱ', 0, 0, 12593, JAEUM);
	protected final KoreanCharacter NIEUN = new Consonant('ㄴ', 2, 3, 12596, JAEUM);
	protected final KoreanCharacter DIGEUT = new Consonant('ㄷ', 3, 6, 12599, JAEUM);
	protected final KoreanCharacter LIEUL = new Consonant('ㄹ', 5, 7, 12601, JAEUM);
	protected final KoreanCharacter MIEUM = new Consonant('ㅁ', 6, 15, 12609, JAEUM);
	protected final KoreanCharacter BIEUP = new Consonant('ㅂ', 7, 16, 12610, JAEUM);
	protected final KoreanCharacter SIOT = new Consonant('ㅅ', 9, 18, 12613, JAEUM);
	protected final KoreanCharacter YIEUNG = new Consonant('ㅇ', 11, 20, 12615, JAEUM);
	protected final KoreanCharacter JIEUT = new Consonant('ㅈ', 12, 21, 12616, JAEUM);
	protected final KoreanCharacter CHIEUT = new Consonant('ㅊ', 14, 22, 12618, JAEUM);
	protected final KoreanCharacter KIEUK = new Consonant('ㅋ', 15, 23, 12619, JAEUM);
	protected final KoreanCharacter TIEUT = new Consonant('ㅌ', 16, 24, 12620, JAEUM);
	protected final KoreanCharacter PIEUP = new Consonant('ㅍ', 17, 25, 12621, JAEUM);
	protected final KoreanCharacter HIEUT = new Consonant('ㅎ', 18, 26, 12622, JAEUM);

	// 쌍자음
	protected final KoreanCharacter SSANG_GIYEOK = new Consonant('ㄲ', 1, 1, 12594, SPC_JAEUM);
	protected final KoreanCharacter SSANG_DIGEUT = new Consonant('ㄸ', 4, -1, 12600, SPC_JAEUM);
	protected final KoreanCharacter SSANG_BIEUP = new Consonant('ㅃ', 8, -1, 12611, SPC_JAEUM);
	protected final KoreanCharacter SSANG_SIOT = new Consonant('ㅆ', 10, 19, 12614, SPC_JAEUM);
	protected final KoreanCharacter SSANG_JIEUT = new Consonant('ㅉ', 13, -1, 12617, SPC_JAEUM);
	
	// 겹자음
	protected final KoreanCharacter GIYEOK_SIOT = new Consonant('ㄳ', -1, 2, 12595, SPC_JAEUM);
	protected final KoreanCharacter NIEUN_JIEUT = new Consonant('ㄵ', -1, 4, 12597, SPC_JAEUM);
	protected final KoreanCharacter NIEUN_HIEUT = new Consonant('ㄶ', -1, 5, 12598, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_GIYEOK = new Consonant('ㄺ', -1, 8, 12602, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_MIEUM = new Consonant('ㄻ', -1, 9, 12603, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_BIEUP = new Consonant('ㄼ', -1, 10, 12604, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_SIOT = new Consonant('ㄽ', -1, 11, 12605, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_TIEUT = new Consonant('ㄾ', -1, 12, 12606, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_PIEUP = new Consonant('ㄿ', -1, 13, 12607, SPC_JAEUM);
	protected final KoreanCharacter LIEUL_HIEUT = new Consonant('ㅀ', -1, 14, 12608, SPC_JAEUM);
	protected final KoreanCharacter BIEUP_SIOT = new Consonant('ㅄ', -1, 17, 12612, SPC_JAEUM);

	//모음
	protected final KoreanCharacter AH = new Vowel('ㅏ', 12623, 0);
	protected final KoreanCharacter AE = new Vowel('ㅐ', 12624, 1);
	protected final KoreanCharacter YA = new Vowel('ㅑ', 12625, 2);
	protected final KoreanCharacter YAE = new Vowel('ㅒ', 12626, 3);
	protected final KoreanCharacter UH = new Vowel('ㅓ', 12627, 4);
	protected final KoreanCharacter E = new Vowel('ㅔ', 12628, 5);
	protected final KoreanCharacter YUH = new Vowel('ㅕ', 12629, 6);
	protected final KoreanCharacter YE = new Vowel('ㅖ', 12630, 7);
	protected final KoreanCharacter OH = new Vowel('ㅗ', 12631, 8);
	protected final KoreanCharacter WA = new Vowel('ㅘ', 12632, 9);
	protected final KoreanCharacter WAE = new Vowel('ㅙ', 12633, 10);
	protected final KoreanCharacter OE = new Vowel('ㅚ', 12634, 11);
	protected final KoreanCharacter YO = new Vowel('ㅛ', 12635, 12);
	protected final KoreanCharacter WOO = new Vowel('ㅜ', 12636, 13);
	protected final KoreanCharacter WUH = new Vowel('ㅝ', 12637, 14);
	protected final KoreanCharacter WEH = new Vowel('ㅞ', 12638, 15);
	protected final KoreanCharacter WUI = new Vowel('ㅟ', 12639, 16);
	protected final KoreanCharacter YOO = new Vowel('ㅠ', 12640, 17);
	protected final KoreanCharacter EU = new Vowel('ㅡ', 12641, 18);
	protected final KoreanCharacter EUI = new Vowel('ㅢ', 12642, 19);
	protected final KoreanCharacter YI = new Vowel('ㅣ', 12643, 20);
	

	public abstract boolean isAllocatedMotion(long finger_array);
	public abstract String execute(long motion, InputConnection input_connection);

}
