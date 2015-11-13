package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Consonant;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.Vowel;

/**
 * Created by sloth on 2015-11-13.
 */
public class StateBokJongThreeJaEum extends StateJungsung {
    public String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]) {
        KoreanCharacter tempBuildBokJaEum;

        KoreanCharacter firstJaeum = buffer[2];
        KoreanCharacter secondJaeum = buffer[3];

        Korean korean = AutomataStateContext.getInstance().getKorean();
        String result = "";

        // 자음 입력
        // 복자음이 되는지 확인 함. <--- 방향으로 결합함. (ex. ㄹ + ㄷ + ㄷ -> ㄹ + ㅌ -> ㄾ)
        if (inputChar instanceof Consonant) {

            // 3단 결합 복자음이 되는지 확인 함. <--- 방향으로 결합함. (ex. ㄹ + ㄷ + ㄷ -> ㄹ + ㅌ -> ㄾ)
            if ((tempBuildBokJaEum = korean.buildBokJaEum(firstJaeum,
                    korean.buildBokJaEum(secondJaeum, inputChar))) != null) {
                result = String.format("%c", korean.generate_korean_char_code(((Consonant)buffer[0]).getCharNumCho(),
                                                                               buffer[1].getCharNum(),
                                                                             ((Consonant)tempBuildBokJaEum).getCharNumJong()));

                // 종성 위치에 3단 결합된 복자음을 넣음
                buffer[2] = tempBuildBokJaEum;

                korean.deleteSurroundingText();
                korean.deleteSurroundingText();

                // 복자음 상태로
                context.setState(new StateBokJongsung());
            }
            // 3단 결합이 되지 않으면 복종성 상태에서 입력된 자음을 초성의 자리로 옮기고
            // jungsung 의 함수 호출
            else {
                buffer[0] = buffer[3];

                // 부모의 buildCharacter 함수에 따라 중성으로 남거나 혹은 3자음 중성 상태로 갈 수 있음.
                context.setState(new StateJungsung());
                result = super.buildCharacter(context, inputChar, buffer);
            }
        }

        // 모음 입력시에는 중성 상태와 작업이 같으므로
        // 복종성 상태에서 입력된 자음을 초성 자리로 옮기고 부모인 중성 상태의 함수 호출
        else if (inputChar instanceof Vowel) {
            buffer[0] = buffer[3];

            // 함수 콜 이후 무조건 종성의 상태로 감.
            result = super.buildCharacter(context, inputChar, buffer);
        }

        // 출력할 단어 반환
        return result;
    }
}
