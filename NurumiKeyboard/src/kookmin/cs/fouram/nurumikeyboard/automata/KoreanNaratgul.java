package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

public class KoreanNaratgul extends Korean {
    public KoreanNaratgul() {
        asc.setKorean(this);

        // one dot
        kMap.put(32L, YIEUNG);    // ㅇ
        kMap.put(1024L, NIEUN);   // ㄴ
        kMap.put(32768L, GIYEOK);  // ㄱ

        // two dot
        kMap.put(1056L, SIOT);  // ㅅ
        kMap.put(33792L, DIGEUT); // ㄷ
        kMap.put(32800L, BIEUP);  // ㅂ

        // three dot
        kMap.put(33824L, JIEUT);  // ㅈ

        kMap.put(32769L, SSANG_GIYEOK);  // ㄲ
        kMap.put(33793L, SSANG_DIGEUT); // ㄸ
        kMap.put(32801L, SSANG_BIEUP);  // ㅃ
        kMap.put(1057L, SSANG_SIOT); // ㅆ
        kMap.put(33825L, SSANG_JIEUT);  // ㅉ

        // one swipe
        kMap.put(64L, OH);   // ㅗ
        kMap.put(512L, AH);  // ㅏ
        kMap.put(128L, WOO); // ㅜ
        kMap.put(256L, UH);  // ㅓ

        // two swipe
        kMap.put(2112L, YO); // ㅛ
        kMap.put(16896L, YA);// ㅑ
        kMap.put(4224L, YOO);// ㅠ
        kMap.put(8448L, YUH);// ㅕ

        kMap.put(524800L, AE);  // ㅐ
        kMap.put(262400L, E);   // ㅔ

        // three swipe
        kMap.put(541184L, EU);// ㅡ
        kMap.put(135296L, YI); // ㅣ
    }

    protected KoreanCharacter buildBokJaEum(KoreanCharacter first, KoreanCharacter second) {
        KoreanCharacter bokJaEum = super.buildBokJaEum(first, second);

        if ( bokJaEum != null )
            return bokJaEum;

        if ( first == null || second == null )
            return null;

        switch (first.getName()) {
            case 'ㅇ':
                if (second == YIEUNG) bokJaEum = MIEUM; // ㅁ
                break;

            case 'ㄴ':
                if (second == NIEUN) bokJaEum = LIEUL;  // ㄹ
                break;

            case 'ㄱ':
                if (second == GIYEOK) bokJaEum = KIEUK;  // ㅋ
                break;

            case 'ㅅ':
                if (second == SIOT) bokJaEum = HIEUT;  // ㅋ
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

            case 'ㄽ':
                if (second == SIOT) bokJaEum = LIEUL_HIEUT;  // ㅀ
                break;

            case 'ㄼ':
                if (second == BIEUP) bokJaEum = LIEUL_PIEUP;  // ㄿ
                break;
        }

        return bokJaEum;
    }

    @Override
    public boolean isAllocatedMotion(long motion) {
        Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
        return ( super.isAllocatedMotion(motion) || kMap.containsKey(motion) );
    }
}
