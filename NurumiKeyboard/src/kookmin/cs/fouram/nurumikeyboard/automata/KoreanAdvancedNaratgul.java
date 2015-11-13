package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

public class KoreanAdvancedNaratgul extends Korean {

    public KoreanAdvancedNaratgul() {

        AutomataStateContext.getInstance().setKorean(this);

        kMap.put(32L, YIEUNG);
        kMap.put(1024L, MIEUM);
        kMap.put(32768L, NIEUN);
        kMap.put(1048576L, LIEUL);

        kMap.put(1056L, GIYEOK);
        kMap.put(33792L, DIGEUT);
        kMap.put(32800L, BIEUP);
        kMap.put(1082368L, SIOT);
        kMap.put(33824L, JIEUT);
        kMap.put(1082400L, HIEUT);

        kMap.put(3L, KIEUK); // 임의의 KEY값
        kMap.put(5L, TIEUT);
        kMap.put(9L, PIEUP);
        kMap.put(17L, CHIEUT);

        kMap.put(1057L, SSANG_GIYEOK);
        kMap.put(33793L, SSANG_DIGEUT);
        kMap.put(32801L, SSANG_BIEUP);
        kMap.put(1082369L, SSANG_SIOT);
        kMap.put(33825L, SSANG_JIEUT);

        kMap.put(64L, OH);
        kMap.put(512L, AH);
        kMap.put(128L, WOO);
        kMap.put(256L, UH);
        kMap.put(2112L, YO);
        kMap.put(16896L, YA);
        kMap.put(4224L, YOO);
        kMap.put(8448L, YUH);
        kMap.put(17318400L, EU);
        kMap.put(4329600L, YI);
        kMap.put(524800L, AE);
        kMap.put(262400L, E);
        kMap.put(541184L, YAE);
        kMap.put(270592L, YE);

        kMap.put(18L, WA);
        kMap.put(19L, WAE);
        kMap.put(20L, OE);
        kMap.put(21L, WUH);
        kMap.put(22L, WEH);
        kMap.put(23L, WUI);
        kMap.put(24L, EUI);

        kMap.put(25L, GIYEOK_SIOT);
        kMap.put(26L, NIEUN_JIEUT);
        kMap.put(27L, NIEUN_HIEUT);
        kMap.put(28L, LIEUL_GIYEOK);
        kMap.put(29L, LIEUL_MIEUM);
        kMap.put(30L, LIEUL_BIEUP);
        kMap.put(31L, LIEUL_SIOT);
        kMap.put(35L, LIEUL_TIEUT);
        kMap.put(37L, LIEUL_PIEUP);
        kMap.put(38L, LIEUL_HIEUT);
        kMap.put(39L, BIEUP_SIOT);
    }

    protected KoreanCharacter buildBokJaEum(KoreanCharacter first, KoreanCharacter second) {
        KoreanCharacter bokJaEum = null;

        if ((bokJaEum = super.buildBokJaEum(first, second)) != null)
            return bokJaEum;

        if (first == null || second == null)
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

        // 기능키. 엔터, 백스페이스, 스페이스
        if (motion == 1048577L || motion == 8L || motion == 16L)
            return true;
        return kMap.containsKey(motion);
    }
}