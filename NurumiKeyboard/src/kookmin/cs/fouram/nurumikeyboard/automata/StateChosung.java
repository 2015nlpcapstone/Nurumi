package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

/**
 * Created by sloth on 2015-11-06.
 */
public class StateChosung implements BuildState {

    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempBuildBokMoEum;
        Korean korean = AutomataStateContext.getInstance().getKorean();

        String result = "";

        // 이전에 모음이 있었고 복모음이 되는 경우
        if ((tempBuildBokMoEum = korean.buildBokMoEum(buffer[0], inputChar)) != null) {
            buffer[0] = tempBuildBokMoEum;
            result = String.format("%c", buffer[0].getUnicode());
            korean.deleteSurroundingText();

            return result;
        }

        AutomataStateContext.getInstance().clear_buffer();

        // 입력된 단어 버퍼에 저장
        buffer[0] = inputChar;

        // 초성으로 입력된 단어 출력
        result = String.format("%c", buffer[0].getUnicode());

        // 초성으로 자음이 들어오면 스테이트를 바꿈
        if (inputChar instanceof Consonant)
            context.setState(new StateJungsung());

        // 출력할 단어 반환
        return result;
    }

}
