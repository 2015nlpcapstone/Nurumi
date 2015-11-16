package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by soyeong on 15. 5. 17..
 */
public class English extends IME_Automata {
    protected ArrayList<EnglishCharacter> alphabet = new ArrayList<>();

    protected class EnglishCharacter {
        private char name;
        private int unicode;

        // Constructor
        public EnglishCharacter(char name, int unicode) {
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

    protected final EnglishCharacter small_a = new EnglishCharacter('a', 97);
    protected final EnglishCharacter small_b = new EnglishCharacter('b', 98);
    protected final EnglishCharacter small_c = new EnglishCharacter('c', 99);
    protected final EnglishCharacter small_d = new EnglishCharacter('d', 100);
    protected final EnglishCharacter small_e = new EnglishCharacter('e', 101);
    protected final EnglishCharacter small_f = new EnglishCharacter('f', 102);
    protected final EnglishCharacter small_g = new EnglishCharacter('g', 103);
    protected final EnglishCharacter small_h = new EnglishCharacter('h', 104);
    protected final EnglishCharacter small_i = new EnglishCharacter('i', 105);
    protected final EnglishCharacter small_j = new EnglishCharacter('j', 106);
    protected final EnglishCharacter small_k = new EnglishCharacter('k', 107);
    protected final EnglishCharacter small_l = new EnglishCharacter('l', 108);
    protected final EnglishCharacter small_m = new EnglishCharacter('m', 109);
    protected final EnglishCharacter small_n = new EnglishCharacter('n', 110);
    protected final EnglishCharacter small_o = new EnglishCharacter('o', 111);
    protected final EnglishCharacter small_p = new EnglishCharacter('p', 112);
    protected final EnglishCharacter small_q = new EnglishCharacter('q', 113);
    protected final EnglishCharacter small_r = new EnglishCharacter('r', 114);
    protected final EnglishCharacter small_s = new EnglishCharacter('s', 115);
    protected final EnglishCharacter small_t = new EnglishCharacter('t', 116);
    protected final EnglishCharacter small_u = new EnglishCharacter('u', 117);
    protected final EnglishCharacter small_v = new EnglishCharacter('v', 118);
    protected final EnglishCharacter small_w = new EnglishCharacter('w', 119);
    protected final EnglishCharacter small_x = new EnglishCharacter('x', 120);
    protected final EnglishCharacter small_y = new EnglishCharacter('y', 121);
    protected final EnglishCharacter small_z = new EnglishCharacter('z', 122);

    protected final EnglishCharacter big_a = new EnglishCharacter('A', 65);
    protected final EnglishCharacter big_b = new EnglishCharacter('B', 66);
    protected final EnglishCharacter big_c = new EnglishCharacter('C', 67);
    protected final EnglishCharacter big_d = new EnglishCharacter('D', 68);
    protected final EnglishCharacter big_e = new EnglishCharacter('E', 69);
    protected final EnglishCharacter big_f = new EnglishCharacter('F', 70);
    protected final EnglishCharacter big_g = new EnglishCharacter('G', 71);
    protected final EnglishCharacter big_h = new EnglishCharacter('H', 72);
    protected final EnglishCharacter big_i = new EnglishCharacter('I', 73);
    protected final EnglishCharacter big_j = new EnglishCharacter('J', 74);
    protected final EnglishCharacter big_k = new EnglishCharacter('K', 75);
    protected final EnglishCharacter big_l = new EnglishCharacter('L', 76);
    protected final EnglishCharacter big_m = new EnglishCharacter('M', 77);
    protected final EnglishCharacter big_n = new EnglishCharacter('N', 78);
    protected final EnglishCharacter big_o = new EnglishCharacter('O', 79);
    protected final EnglishCharacter big_p = new EnglishCharacter('P', 80);
    protected final EnglishCharacter big_q = new EnglishCharacter('Q', 81);
    protected final EnglishCharacter big_r = new EnglishCharacter('R', 82);
    protected final EnglishCharacter big_s = new EnglishCharacter('S', 83);
    protected final EnglishCharacter big_t = new EnglishCharacter('T', 84);
    protected final EnglishCharacter big_u = new EnglishCharacter('U', 85);
    protected final EnglishCharacter big_v = new EnglishCharacter('V', 86);
    protected final EnglishCharacter big_w = new EnglishCharacter('W', 87);
    protected final EnglishCharacter big_x = new EnglishCharacter('X', 88);
    protected final EnglishCharacter big_y = new EnglishCharacter('Y', 89);
    protected final EnglishCharacter big_z = new EnglishCharacter('Z', 90);

    HashMap<Long, EnglishCharacter> eMap = new HashMap<Long, EnglishCharacter>();

    public English() {
        eMap.put(32L, small_a);
        eMap.put(1024L, small_b);
        eMap.put(32768L, small_c);
        eMap.put(1048576L, small_d);
        eMap.put(541184L, small_e);
        eMap.put(16896L, small_f);
        eMap.put(540672L, small_g);
        eMap.put(131200L, small_h);
        eMap.put(128L, small_i);
        eMap.put(4096L, small_j);
        eMap.put(4128L, small_k);
        eMap.put(524288L, small_l);
        eMap.put(135296L, small_m);
        eMap.put(4224L, small_n);
        eMap.put(1056L, small_o);
        eMap.put(32800L, small_p);
        eMap.put(1048608L, small_q);
        eMap.put(33792L, small_r);
        eMap.put(8448L, small_s);
        eMap.put(16384L, small_t);
        eMap.put(65600L, small_u);
        eMap.put(2112L, small_v);
        eMap.put(67648L, small_w);
        eMap.put(2080L, small_x);
        eMap.put(67584L, small_y);
        eMap.put(270336L, small_z);

        eMap.put(33L, big_a);
        eMap.put(1025L, big_b);
        eMap.put(32769L, big_c);
        eMap.put(1048577L, big_d);
        eMap.put(541185L, big_e);
        eMap.put(16897L, big_f);
        eMap.put(540673L, big_g);
        eMap.put(131201L, big_h);
        eMap.put(129L, big_i);
        eMap.put(4097L, big_j);
        eMap.put(4129L, big_k);
        eMap.put(524289L, big_l);
        eMap.put(135297L, big_m);
        eMap.put(4225L, big_n);
        eMap.put(1057L, big_o);
        eMap.put(32801L, big_p);
        eMap.put(1048609L, big_q);
        eMap.put(33793L, big_r);
        eMap.put(8449L, big_s);
        eMap.put(16385L, big_t);
        eMap.put(65601L, big_u);
        eMap.put(2113L, big_v);
        eMap.put(67649L, big_w);
        eMap.put(2081L, big_x);
        eMap.put(67585L, big_y);
        eMap.put(270337L, big_z);
    }

    /*
        // 0.A 1.B 2.C 3.D 4.E 5.F 6.G 7.H 8.I 9.J 10.K 11.L 12.M 13.N
        // 14.O 15.P 16.Q 17.R 18.S 19.T 20.U 21.V 22.W 23.X 24.Y 25.Z
        private final int[] PREF_ENGLISH_B = {65, 66, 67, 68, 69, 70, 71,
                72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85,
                86, 87, 88, 89, 90
        };

        // 0.a 1.b 2.c 3.d 4.e 5.f 6.g 7.h 8.i 9.j 10.k 11.l 12.m 13.n
        // 14.o 15.p 16.q 17.r 18.s 19.t 20.u 21.v 22.w 23.x 24.y 25.z
        private final int[] PREF_ENGLISH_S = {97, 98, 99, 100, 101, 102, 103,
                104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117,
                118, 119, 120, 121, 122
        };
    */
    public String execute(long motionValue, InputConnection input_connection) {
        Log.i("AUTOMATA_LOG", "Location : Automata_type_Eng - execute()");

        // kim // 150507 // functional keys
        ic = input_connection;

        if (motionValue == 16L) {
            return " ";
        } else if (motionValue == 8L) {
            deleteSurroundingText();
            return "";
        }

        return String.format("%c", eMap.get(motionValue).getUnicode());
    }

    @Override
    public boolean isAllocatedMotion(long motion) {
        Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
        return (super.isAllocatedMotion(motion) || eMap.containsKey(motion));
    }
}