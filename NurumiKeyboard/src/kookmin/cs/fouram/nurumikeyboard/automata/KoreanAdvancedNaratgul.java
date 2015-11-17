package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

public class KoreanAdvancedNaratgul extends Korean {

    public KoreanAdvancedNaratgul() {

        asc.setKorean(this);

        kMap.put(32L, YIEUNG);    // ㅇ
        kMap.put(1024L, MIEUM);   // ㅁ
        kMap.put(32768L, NIEUN);  // ㄴ
        kMap.put(1048576L, LIEUL);// ㄹ

        kMap.put(1056L, GIYEOK);  // ㄱ
        kMap.put(33792L, DIGEUT); // ㄷ
        kMap.put(32800L, BIEUP);  // ㅂ
        kMap.put(1082368L, SIOT); // ㅅ
        kMap.put(33824L, JIEUT);  // ㅈ
        kMap.put(1082400L, HIEUT);// ㅎ

        kMap.put(1057L, SSANG_GIYEOK);  // ㄲ
        kMap.put(33793L, SSANG_DIGEUT); // ㄸ
        kMap.put(32801L, SSANG_BIEUP);  // ㅃ
        kMap.put(1082369L, SSANG_SIOT); // ㅆ
        kMap.put(33825L, SSANG_JIEUT);  // ㅉ

        kMap.put(64L, OH);   // ㅗ
        kMap.put(512L, AH);  // ㅏ
        kMap.put(128L, WOO); // ㅜ
        kMap.put(256L, UH);  // ㅓ
        kMap.put(2112L, YO); // ㅛ
        kMap.put(16896L, YA);// ㅑ
        kMap.put(4224L, YOO);// ㅠ
        kMap.put(8448L, YUH);// ㅕ
        kMap.put(17318400L, EU);// ㅡ
        kMap.put(4329600L, YI); // ㅣ
        kMap.put(524800L, AE);  // ㅐ
        kMap.put(262400L, E);   // ㅔ
        kMap.put(541184L, YAE); // ㅒ
        kMap.put(270592L, YE);  // ㅖ
    }

    protected KoreanCharacter buildBokJaEum(KoreanCharacter first, KoreanCharacter second) {
        KoreanCharacter bokJaEum = super.buildBokJaEum(first, second);

        if ( bokJaEum != null )
            return bokJaEum;

        if ( first == null || second == null )
            return null;

        switch (first.getName()) {
            case 'ㄱ':
                if (second == GIYEOK) bokJaEum = KIEUK;  // ㅋ
                break;

            case 'ㄷ':
                if (second == DIGEUT) bokJaEum = TIEUT; // ㅌ
                break;

            case 'ㅂ':
                if (second == BIEUP) bokJaEum = PIEUP;    // ㅍ
                break;

            case 'ㅈ':
                if (second == JIEUT) bokJaEum = CHIEUT; // ㅊ
                break;

            case 'ㄼ':
                if (second == BIEUP) bokJaEum = LIEUL_PIEUP;  // ㄿ
                break;
        }

        return bokJaEum;
    }

    // yoon // THIS IS WHAT I'M REALLY WANT TO DO !!
    public String execute(long motionValue, InputConnection input_connection) {
        return super.execute(motionValue, input_connection);
    }

    @Override
    public boolean isAllocatedMotion(long motion) {
        Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
        return ( super.isAllocatedMotion(motion) || kMap.containsKey(motion) );
    }
}