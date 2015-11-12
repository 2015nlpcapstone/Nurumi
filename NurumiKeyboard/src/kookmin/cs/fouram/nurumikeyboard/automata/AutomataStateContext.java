package kookmin.cs.fouram.nurumikeyboard.automata;


import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;

/**
 * Created by sloth on 2015-11-06.
 */
public class AutomataStateContext {
    private static AutomataStateContext ourInstance = new AutomataStateContext();
    private BuildState state;

    private KoreanCharacter buffer[] = new KoreanCharacter[5];
    private Korean korean;

    public static AutomataStateContext getInstance() {
        return ourInstance;
    }

    private AutomataStateContext() {
        setState(new StateChosung());
        clear_buffer();
    }

    public void setState(BuildState state) {
        this.state = state;
    }

    public void setKorean(Korean korean) {
        this.korean = korean;
    }

    public Korean getKorean() {
        return korean;
    }

    // yoon // 150517 // clean character buffer
    protected void clear_buffer() {
        int i = 4;
        while (i-- > 0)
            buffer[i] = null;
    }

    public String buildCharacter(KoreanCharacter inputChar) {
        return state.buildCharacter(this, inputChar, buffer);
    }
}
