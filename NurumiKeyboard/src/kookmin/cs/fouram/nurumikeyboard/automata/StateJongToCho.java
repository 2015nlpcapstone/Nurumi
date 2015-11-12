package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-11.
 */

// 복종성 스테이트에서 입력된 글자와 기존의 종성의 글자가 결합되지 않을 때 실행 됨.
// 복자음을 오른쪽 글자부터 만듦. (ex. {ㄹ,ㄷ,ㄷ} ㄷ+ㄷ-> ㅌ, ㄹ+ㅌ-> ㅌ)
public class StateJongToCho implements BuildState {
    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempBuildBokJaEum;
        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 입력된 단어 버퍼에 저장
        buffer[4] = inputChar;

        // 자음 입력
        if(inputChar instanceof Consonant) {
            // 입력된 글자와 복종성 스테이트에서 입력된 글자 결합
            if ((tempBuildBokJaEum = korean.buildBokJaEum(buffer[3], buffer[4])) != null) {
                // 위의 결합 후 기존 종성과 결합
                if ((tempBuildBokJaEum = korean.buildBokJaEum(buffer[2], tempBuildBokJaEum)) != null) {
                    // 종성의 3단 결합이 가능할 때 (ex, ㄹ->ㄷ->ㄷ => ㄾ)
                    // 유니코드 글자 결합.
                    result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[0]).getCharNumCho(),
                            buffer[1].getCharNum(),
                            ((Consonant) tempBuildBokJaEum).getCharNumJong()));
                    // 합쳐진 복자음을 버퍼의 초성의 자리로 옮김.
                    buffer[0] = tempBuildBokJaEum;

                    korean.deleteSurroundingText();
                    korean.deleteSurroundingText();
                } else {
                    // 복종성에서 입력된 글자만 결합 가능할 때
                    buffer[0] = korean.buildBokJaEum(buffer[3], buffer[4]);
                    result = String.format("%c", buffer[0].getUnicode());

                    korean.deleteSurroundingText();
                }
            } else {
                // 모두 결합이 되지 않는 자음일 때
                result = String.format("%c", buffer[4].getUnicode());
                buffer[0] = buffer[4];
            }

            // 중성 스테이트로 변경.
            context.setState(new StateJungsung());
        }
        // 모음 입력
        else if( inputChar instanceof Vowel) {
            result = String.format("%c", korean.generate_korean_char_code(((Consonant) buffer[3]).getCharNumCho(),
                    buffer[4].getCharNum(),
                    0));
            buffer[0] = buffer[3];
            buffer[1] = buffer[4];

            korean.deleteSurroundingText();

            // 종성 스테이트로 변경.
            context.setState(new StateJongsung());
        }

        return result;
    }
}
