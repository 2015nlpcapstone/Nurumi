package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-06.
 */
// Jungsung 상태로 오려면 이전의 단어가 무조건 자음.
public class StateJungsung implements BuildState {

    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempDivBokJaEum[];
        KoreanCharacter tempBuildBokJaEum;

        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 입력된 단어 버퍼에 저장
        buffer[1] = inputChar;

        // 중성 위치에 모음이 왔을 때
        if (inputChar instanceof Vowel) {
            if ((tempDivBokJaEum = korean.divideBokJaEum(buffer[0])) != null) {
                // 첫 자음이 복자음
                result = String.format("%c%c", tempDivBokJaEum[0].getUnicode(),
                        korean.generate_korean_char_code(((Consonant) tempDivBokJaEum[1]).getCharNumCho(), buffer[1].getCharNum(), 0));
                buffer[0] = tempDivBokJaEum[1];
            } else {
                // 복자음이 아닐 때
                Log.d("mytag", "" + buffer[0] + " " + buffer[1]);
                result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                        buffer[1].getCharNum(),
                        0));
            }
            context.setState(new StateJongsung());
        }
        // 중성 위치에 자음이 왔을 때
        else if (inputChar instanceof Consonant) {
            if ((tempBuildBokJaEum = korean.buildBokJaEum(buffer[0], buffer[1])) != null) {
                // 두 자음이 복자음이 되는 경우
                result = String.format("%c", tempBuildBokJaEum.getUnicode());
                buffer[0] = tempBuildBokJaEum;
            } else {
                result = String.format("%c%c", buffer[0].getUnicode(), buffer[1].getUnicode());
                buffer[0] = buffer[1];
            }
        }

        korean.deleteSurroundingText();
        return result;
    }
}
