package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import java.util.HashMap;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

public abstract class Korean extends IME_Automata {

	protected HashMap<Long, KoreanCharacter> kMap = new HashMap<>();

	protected final int AC00 = 44032; // 한글 유니코드 계산 공식에 사용되는 상수
	protected KoreanCharacter inputChar;

	// yoon // 150516 // get a Korean character code key value
	protected int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
		return ((AC00 + ((cho_seong * 21) + jung_seong) * 28) + jong_seong);
	}

	// 자음
	protected final KoreanCharacter GIYEOK = new Consonant('ㄱ', 0, 1, 12593);
	protected final KoreanCharacter NIEUN = new Consonant('ㄴ', 2, 4, 12596);
	protected final KoreanCharacter DIGEUT = new Consonant('ㄷ', 3, 7, 12599);
	protected final KoreanCharacter LIEUL = new Consonant('ㄹ', 5, 8, 12601);
	protected final KoreanCharacter MIEUM = new Consonant('ㅁ', 6, 16, 12609);
	protected final KoreanCharacter BIEUP = new Consonant('ㅂ', 7, 17, 12610);
	protected final KoreanCharacter SIOT = new Consonant('ㅅ', 9, 19, 12613);
	protected final KoreanCharacter YIEUNG = new Consonant('ㅇ', 11, 21, 12615);
	protected final KoreanCharacter JIEUT = new Consonant('ㅈ', 12, 22, 12616);
	protected final KoreanCharacter CHIEUT = new Consonant('ㅊ', 14, 23, 12618);
	protected final KoreanCharacter KIEUK = new Consonant('ㅋ', 15, 24, 12619);
	protected final KoreanCharacter TIEUT = new Consonant('ㅌ', 16, 25, 12620);
	protected final KoreanCharacter PIEUP = new Consonant('ㅍ', 17, 26, 12621);
	protected final KoreanCharacter HIEUT = new Consonant('ㅎ', 18, 27, 12622);

	// 쌍자음
	protected final KoreanCharacter SSANG_GIYEOK = new Consonant('ㄲ', 1, 2, 12594);
	protected final KoreanCharacter SSANG_DIGEUT = new Consonant('ㄸ', 4, -1, 12600);
	protected final KoreanCharacter SSANG_BIEUP = new Consonant('ㅃ', 8, -1, 12611);
	protected final KoreanCharacter SSANG_SIOT = new Consonant('ㅆ', 10, 20, 12614);
	protected final KoreanCharacter SSANG_JIEUT = new Consonant('ㅉ', 13, -1, 12617);

	// 겹자음
	protected final KoreanCharacter GIYEOK_SIOT = new Consonant('ㄳ', -1, 3, 12595);
	protected final KoreanCharacter NIEUN_JIEUT = new Consonant('ㄵ', -1, 5, 12597);
	protected final KoreanCharacter NIEUN_HIEUT = new Consonant('ㄶ', -1, 6, 12598);
	protected final KoreanCharacter LIEUL_GIYEOK = new Consonant('ㄺ', -1, 9, 12602);
	protected final KoreanCharacter LIEUL_MIEUM = new Consonant('ㄻ', -1, 10, 12603);
	protected final KoreanCharacter LIEUL_BIEUP = new Consonant('ㄼ', -1, 11, 12604);
	protected final KoreanCharacter LIEUL_SIOT = new Consonant('ㄽ', -1, 12, 12605);
	protected final KoreanCharacter LIEUL_TIEUT = new Consonant('ㄾ', -1, 13, 12606);
	protected final KoreanCharacter LIEUL_PIEUP = new Consonant('ㄿ', -1, 14, 12607);
	protected final KoreanCharacter LIEUL_HIEUT = new Consonant('ㅀ', -1, 15, 12608);
	protected final KoreanCharacter BIEUP_SIOT = new Consonant('ㅄ', -1, 18, 12612);

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

	public String execute(long motionValue, InputConnection input_connection) {
		Log.d("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - execute(). motion : " + motion + ", motionValue : " + motionValue);

		AutomataStateContext asc = AutomataStateContext.getInstance();

		ic = input_connection;

		if (motionValue == 16L) {
			asc.setState(new StateChosung());
			asc.clear_buffer();

			return " ";
		} else if (motionValue == 8L) {
			asc.setState(new StateChosung());
			asc.clear_buffer();

			deleteSurroundingText();
			return "";
		}

		if (!kMap.containsKey(motionValue))
			return "";

		if (inputChar == null) {
			inputChar = kMap.get(motionValue);
		}

		String result = asc.buildCharacter(inputChar);

		inputChar = null;

		return result;
	}

	protected void deleteSurroundingText() {
		ic.deleteSurroundingText(1, 0);
	}

	protected KoreanCharacter[] divideBokJaEum(KoreanCharacter bok_ja_eum) {
		KoreanCharacter divisionBok[] = new KoreanCharacter[2];

		if (bok_ja_eum == null)
			return null;

		switch (bok_ja_eum.getName()) {
			case 'ㄳ':
				divisionBok[0] = GIYEOK; // ㄱ
				divisionBok[1] = SIOT;   // ㅅ
				break;
			case 'ㄵ':
				divisionBok[0] = NIEUN;  // ㄴ
				divisionBok[1] = JIEUT;  // ㅈ
				break;
			case 'ㄶ':
				divisionBok[0] = NIEUN;  // ㄴ
				divisionBok[1] = HIEUT;  // ㅎ
				break;
			case 'ㄺ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = GIYEOK; // ㄱ
				break;
			case 'ㄻ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = MIEUM;  // ㅁ
				break;
			case 'ㄼ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = BIEUP;  // ㅂ
				break;
			case 'ㄽ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = SIOT;   // ㅅ
				break;
			case 'ㄾ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = TIEUT;  // ㅌ
				break;
			case 'ㄿ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = PIEUP;  // ㅍ
				break;
			case 'ㅀ':
				divisionBok[0] = LIEUL;  // ㄹ
				divisionBok[1] = HIEUT;  // ㅎ
				break;
			case 'ㅄ':
				divisionBok[0] = BIEUP;  // ㅂ
				divisionBok[1] = SIOT;   // ㅅ
				break;
		}

		return (divisionBok[0] == null) ? null : divisionBok;
	}

	protected KoreanCharacter buildBokJaEum(KoreanCharacter first, KoreanCharacter second) {
		KoreanCharacter bokJaEum = null;

		if (first == null || second == null)
			return null;

		switch (first.getName()) {
			case 'ㄱ':
				if (second == SIOT) bokJaEum = GIYEOK_SIOT;   // ㄳ
				break;

			case 'ㄴ':
				if (second == JIEUT) bokJaEum = NIEUN_JIEUT; // ㄵ
				else if (second == HIEUT) bokJaEum = NIEUN_HIEUT; // ㄶ
				break;

			case 'ㄹ':
				if (second == GIYEOK) bokJaEum = LIEUL_GIYEOK; // ㄺ
				else if (second == MIEUM) bokJaEum = LIEUL_MIEUM;  // ㄻ
				else if (second == BIEUP) bokJaEum = LIEUL_BIEUP;  // ㄼ
				else if (second == SIOT) bokJaEum = LIEUL_SIOT;   // ㄽ
				else if (second == TIEUT) bokJaEum = LIEUL_TIEUT;  // ㄾ
				else if (second == PIEUP) bokJaEum = LIEUL_PIEUP;  // ㄿ
				else if (second == HIEUT) bokJaEum = LIEUL_HIEUT;  // ㅀ
				break;

			case 'ㅂ':
				if (second == SIOT) bokJaEum = BIEUP_SIOT; // ㅄ
				break;
		}

		return bokJaEum;
	}

	protected KoreanCharacter buildBokMoEum(KoreanCharacter first, KoreanCharacter second) {
		KoreanCharacter bokMoEum = null;

		if (first == null || second == null)
			return null;

		switch (first.getName()) {
			case 'ㅏ':
				if (second == YI) bokMoEum = AE;   // ㅐ
				break;

			case 'ㅑ':
				if (second == YI) bokMoEum = YAE; // ㅒ
				break;

			case 'ㅓ':
				if (second == YI) bokMoEum = E; // ㅔ
				break;

			case 'ㅕ':
				if (second == YI) bokMoEum = YE; // ㅖ
				break;

			case 'ㅗ':
				if (second == AH) bokMoEum = WA; // ㅘ
				else if (second == AE) bokMoEum = WAE; // ㅙ
				else if (second == YI) bokMoEum = OE; // ㅚ
				break;

			case 'ㅜ':
				if (second == UH) bokMoEum = WUH; // ㅝ
				else if (second == E) bokMoEum = WEH; // ㅞ
				else if (second == YI) bokMoEum = WUI; // ㅟ
				break;

			case 'ㅡ':
				if (second == YI) bokMoEum = EUI; // ㅢ
				break;

			case 'ㅘ':
				if (second == YI) bokMoEum = WAE; // ㅙ
				break;

			case 'ㅝ':
				if (second == YI) bokMoEum = WEH; // ㅞ
				break;
		}

		return bokMoEum;
	}
}
