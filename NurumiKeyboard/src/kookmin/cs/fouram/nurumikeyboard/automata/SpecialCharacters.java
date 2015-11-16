package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import java.util.HashMap;

/**
 * Created by kimminho on 15. 5. 21..
 */
public class SpecialCharacters extends IME_Automata {
    protected class SpecialCharacter {
        private char name;
        private int unicode;

        // Constructor
        public SpecialCharacter(char name, int unicode) {
            this.name = name;
            this.unicode = unicode;
        }
        // getter
        public char getName() {
            return name;
        }
        public int getUnicode() {
            return unicode;
        }
    }
    protected final SpecialCharacter Zero = new SpecialCharacter('0', 48);
    protected final SpecialCharacter One = new SpecialCharacter('1', 49);
    protected final SpecialCharacter Two = new SpecialCharacter('2', 50);
    protected final SpecialCharacter Three = new SpecialCharacter('3', 51);
    protected final SpecialCharacter Four = new SpecialCharacter('4', 52);
    protected final SpecialCharacter Five = new SpecialCharacter('5', 53);
    protected final SpecialCharacter Six = new SpecialCharacter('6', 54);
    protected final SpecialCharacter Seven = new SpecialCharacter('7', 55);
    protected final SpecialCharacter Eight = new SpecialCharacter('8', 56);
    protected final SpecialCharacter Nine = new SpecialCharacter('9', 57);

    protected final SpecialCharacter Dot = new SpecialCharacter('.', 46);
    protected final SpecialCharacter Comma = new SpecialCharacter(',', 44);
    protected final SpecialCharacter Point = new SpecialCharacter('!', 33);
    protected final SpecialCharacter Question = new SpecialCharacter('?', 63);
    protected final SpecialCharacter Computing = new SpecialCharacter('^', 94);
    protected final SpecialCharacter Swungdash = new SpecialCharacter('~', 126);
    protected final SpecialCharacter Slash = new SpecialCharacter('/', 47);
    protected final SpecialCharacter Semicolon = new SpecialCharacter(';', 59);
    protected final SpecialCharacter Hyphen = new SpecialCharacter('-', 45);
    protected final SpecialCharacter Under_hyphen = new SpecialCharacter('_', 95);
    protected final SpecialCharacter Left_parentheses = new SpecialCharacter('(', 40);
    protected final SpecialCharacter Right_parentheses = new SpecialCharacter(')', 41);
    protected final SpecialCharacter Plus = new SpecialCharacter('+', 43);
    protected final SpecialCharacter Star = new SpecialCharacter('*', 42);
    protected final SpecialCharacter At = new SpecialCharacter('@', 64);
    protected final SpecialCharacter Equal = new SpecialCharacter('=', 61);

    HashMap<Long, SpecialCharacter> sMap = new HashMap<Long, SpecialCharacter>();

    public SpecialCharacters() {
        sMap.put(32L, One);
        sMap.put(1024L, Two);
        sMap.put(32768L, Three);
        sMap.put(1048576L, Four);
        sMap.put(1056L, Five);
        sMap.put(33792L, Six);
        sMap.put(1081344L, Seven);
        sMap.put(33824L, Eight);
        sMap.put(1082368L, Nine);
        sMap.put(1082400L, Zero);
        sMap.put(64L, Dot);
        sMap.put(128L, Comma);
        sMap.put(512L, Point);
        sMap.put(256L, Question);
        sMap.put(2048L, Computing);
        sMap.put(4096L, Swungdash);
        sMap.put(16384L, Slash);
        sMap.put(8192L, Semicolon);
        sMap.put(65536L, Hyphen);
        sMap.put(131072L, Under_hyphen);
        sMap.put(524288L, Left_parentheses);
        sMap.put(262144L, Right_parentheses);
        sMap.put(2097152L, Plus);
        sMap.put(4194304L, Star);
        sMap.put(16777216L, At);
        sMap.put(8388608L, Equal);
    }

    public String execute(long motionValue, InputConnection input_connection) {
    	Log.i("AUTOMATA_LOG", "Location : Automata_type_Spc - execute()");
        // kim // 150516 // init values
        ic = input_connection;

        // kim // 150412 // count finger

        if(motionValue == 16L) {
            return " ";
        }
        else if(motionValue == 8L) {
            deleteSurroundingText();
            return "";
        }

        return String.format("%c", sMap.get(motionValue).getUnicode());
    }

    @Override
    public boolean isAllocatedMotion(long motion) {
        Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
        return ( super.isAllocatedMotion(motion) || sMap.containsKey(motion) );
    }
}
