package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-06.
 */
// Jongsung 상태로 오려면 이전의 단어가 무조건 모음.
public class StateJongsung implements BuildState {

    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempBuildBokJaEum;
        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 입력된 단어 버퍼에 저장
        buffer[2] = inputChar;

        // 종성에 자음이 입력되었을 때
        if (inputChar instanceof Consonant) {
            if( ((Consonant)buffer[2]).getCharNumJong() == -1) {
                result = String.format("%c", buffer[2].getUnicode());
                buffer[0] = buffer[2];
                context.setState(new StateJungsung());
                return result;
            }
            // 한글을 생성해서 출력
            result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                    buffer[1].getCharNum(),
                    ((Consonant) buffer[2]).getCharNumJong()));
            // 복자음 종성 상태로..
            context.setState(new StateBokJongsung());
        }
        // 종성에 모음이 입력 되었을 때
        else if (inputChar instanceof Vowel) {
            if ((tempBuildBokJaEum = korean.buildBokMoEum(buffer[1], buffer[2])) != null) {
                // 복모음으로 만들 수 있으면
                // 중성을 복모음으로 만들고 단어 출력
                buffer[1] = tempBuildBokJaEum;
                result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                        buffer[1].getCharNum(),
                        0));
            } else {
                // 복모음으로 만들 수 없는 모음이면
                // 이전 글자와 모음을 따로 출력
                result = String.format("%c%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                                buffer[1].getCharNum(),
                                0),
                        buffer[2].getUnicode());
                // 버퍼의 초성 자리에 입력된 모음 대입
                buffer[0] = buffer[2];
                // 상태를 초성 상태로 변경
                context.setState(new StateChosung());
            }
        }

        korean.deleteSurroundingText();
        return result;
    }
}