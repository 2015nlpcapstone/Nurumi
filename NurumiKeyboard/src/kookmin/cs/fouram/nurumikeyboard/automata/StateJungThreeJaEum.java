package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-13.
 */
public class StateJungThreeJaEum extends StateJungsung {
    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempBuildBokJaEum;

        KoreanCharacter firstJaeum = buffer[0];
        KoreanCharacter secondJaeum = buffer[1];

        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 자음 입력
        if (inputChar instanceof Consonant) {
            // 자음 입력시에는 무조건 중성 상태로 감.
            context.setState(new StateJungsung());

            // 3단 결합 복자음이 되는지 확인 함. <--- 방향으로 결합함. (ex. ㄹ + ㄷ + ㄷ -> ㄹ + ㅌ -> ㄾ)
            if ((tempBuildBokJaEum = korean.buildBokJaEum(firstJaeum,
                    korean.buildBokJaEum(secondJaeum, inputChar))) != null) {
                result = String.format("%c", tempBuildBokJaEum.getUnicode());

                // 합쳐진 복자음을 버퍼의 초성의 자리로 옮김.
                buffer[0] = tempBuildBokJaEum;
                korean.deleteSurroundingText();
                korean.deleteSurroundingText();
            }
            // 3단 결합이 되지 않으면 중성 상태에서 입력된 자음을 초성의 자리로 옮기고
            // jungsung 의 함수 호출
            else {
                buffer[0] = buffer[1];

                // 부모의 buildCharacter 함수에 따라 중성 상태로 남거나 혹은 3자음 중성 상태로 올 수 있음.
                result = super.buildCharacter(context, inputChar, buffer);
            }
        }
        // 모음 입력시에는 중성 상태와 작업이 같으므로
        // 두 번째 입력된 자음을 초성 자리로 옮기고 부모인 중성 상태의 함수 호출
        else if (inputChar instanceof Vowel)
        {
            buffer[0] = buffer[1];

            // 함수 콜 이후 무조건 종성의 상태로 감.
            result = super.buildCharacter(context, inputChar, buffer);
        }

        // 출력할 단어 반환
        return result;
    }
}
