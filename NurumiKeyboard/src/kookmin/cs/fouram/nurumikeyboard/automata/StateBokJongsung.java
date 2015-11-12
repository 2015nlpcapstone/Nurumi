package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-07.
 */
public class StateBokJongsung implements BuildState {

    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempDivBokJaEum[];
        KoreanCharacter tempBuildBokJaEum;

        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 입력된 단어 버퍼에 저장
        buffer[3] = inputChar;

        // 자음이 입력되었을 때
        if (inputChar instanceof Consonant) {
            if ((tempBuildBokJaEum = korean.buildBokJaEum(buffer[2], buffer[3])) != null) {
                // 두 자음이 복자음이 되는 경우
                // 복자음을 종성 위치에 대입
                buffer[2] = tempBuildBokJaEum;
                // 출력할 글자를 만듦
                result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                        buffer[1].getCharNum(),
                        ((Consonant) buffer[2]).getCharNumJong()));
            } else {
                // 두 자음이 복자음이 되지 않는 경우
                // 이전 글자와 입력된 자음을 만듦
                result = String.format("%c%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                                buffer[1].getCharNum(),
                                ((Consonant) buffer[2]).getCharNumJong()),
                        buffer[3].getUnicode());

                context.setState(new StateJongToCho());
            }
        }
        // 모음이 입력 되었을 때
        else if (inputChar instanceof Vowel) {
            if ((tempDivBokJaEum = korean.divideBokJaEum(buffer[2])) != null) {
                // 종성이 복자음인 경우
                // 복자음의 뒷 자음을 떼고 앞의 글자를 만들고 뗀 자음과 입력된 모음으로 뒷 글자를 만듦
                result = String.format("%c%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                                buffer[1].getCharNum(),
                                ((Consonant) tempDivBokJaEum[0]).getCharNumJong()),
                        korean.generate_korean_char_code(((Consonant) tempDivBokJaEum[1]).getCharNumCho(),
                                buffer[3].getCharNum(),
                                0));
                // 뗀 자음과 모음을 초성과 중성 자리로 옮김
                buffer[0] = tempDivBokJaEum[1];
                buffer[1] = buffer[3];
            } else {
                // 종성이 단자음인 경우
                // 버퍼에 있던 초성과 중성으로 앞의 글자를 만들고 종성의 자음과 입력된 모음으로 뒷 글자를 만듦
                result = String.format("%c%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                                buffer[1].getCharNum(),
                                0),
                        korean.generate_korean_char_code(((Consonant) buffer[2]).getCharNumCho(),
                                buffer[3].getCharNum(),
                                0));
                // 종성의 자음과 입력된 모음을 초성과 중성 자리로 대입
                buffer[0] = buffer[2];
                buffer[1] = buffer[3];
            }

            // 종성 상태로 변경
            context.setState(new StateJongsung());
        }

        korean.deleteSurroundingText();
        return result;
    }
}