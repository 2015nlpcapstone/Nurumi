package kookmin.cs.fouram.nurumikeyboard.automata;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

/**
 * Created by sloth on 2015-11-06.
 */
public interface BuildState {
    String buildCharacter(AutomataStateContext context, KoreanCharacter inputChar, KoreanCharacter buffer[]);
}
