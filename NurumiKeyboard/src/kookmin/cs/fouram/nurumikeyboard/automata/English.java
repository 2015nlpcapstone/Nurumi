package kookmin.cs.fouram.nurumikeyboard.automata;

import java.util.HashMap;

import android.util.Log;
import android.view.inputmethod.InputConnection;

/**
 * Created by soyeong on 15. 5. 17..
 */
public class English extends IME_Automata {
	
	private void setText(String str) {
		text_to_commit = str;
	}
	
	
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
	protected final EnglishCharacter small_a = new EnglishCharacter('a', 65);
	protected final EnglishCharacter small_b = new EnglishCharacter('b', 66);
	protected final EnglishCharacter small_c = new EnglishCharacter('c', 67);
	protected final EnglishCharacter small_d = new EnglishCharacter('d', 68);
	protected final EnglishCharacter small_e = new EnglishCharacter('e', 69);
	protected final EnglishCharacter small_f = new EnglishCharacter('f', 70);
	protected final EnglishCharacter small_g = new EnglishCharacter('g', 71);
	protected final EnglishCharacter small_h = new EnglishCharacter('h', 72);
	protected final EnglishCharacter small_i = new EnglishCharacter('i', 73);
	protected final EnglishCharacter small_j = new EnglishCharacter('j', 74);
	protected final EnglishCharacter small_k = new EnglishCharacter('k', 75);
	protected final EnglishCharacter small_l = new EnglishCharacter('l', 76);
	protected final EnglishCharacter small_m = new EnglishCharacter('m', 77);
	protected final EnglishCharacter small_n = new EnglishCharacter('n', 78);
	protected final EnglishCharacter small_o = new EnglishCharacter('o', 79);
	protected final EnglishCharacter small_p = new EnglishCharacter('p', 80);
	protected final EnglishCharacter small_q = new EnglishCharacter('q', 81);
	protected final EnglishCharacter small_r = new EnglishCharacter('r', 82);
	protected final EnglishCharacter small_s = new EnglishCharacter('s', 83);
	protected final EnglishCharacter small_t = new EnglishCharacter('t', 84);
	protected final EnglishCharacter small_u = new EnglishCharacter('u', 85);
	protected final EnglishCharacter small_v = new EnglishCharacter('v', 86);
	protected final EnglishCharacter small_w = new EnglishCharacter('w', 87);
	protected final EnglishCharacter small_x = new EnglishCharacter('x', 88);
	protected final EnglishCharacter small_y = new EnglishCharacter('y', 89);
	protected final EnglishCharacter small_z = new EnglishCharacter('z', 90);

	protected final EnglishCharacter big_a = new EnglishCharacter('A', 97);
	protected final EnglishCharacter big_b = new EnglishCharacter('B', 98);
	protected final EnglishCharacter big_c = new EnglishCharacter('C', 99);
	protected final EnglishCharacter big_d = new EnglishCharacter('D', 100);
	protected final EnglishCharacter big_e = new EnglishCharacter('E', 101);
	protected final EnglishCharacter big_f = new EnglishCharacter('F', 102);
	protected final EnglishCharacter big_g = new EnglishCharacter('G', 103);
	protected final EnglishCharacter big_h = new EnglishCharacter('H', 104);
	protected final EnglishCharacter big_i = new EnglishCharacter('I', 105);
	protected final EnglishCharacter big_j = new EnglishCharacter('J', 106);
	protected final EnglishCharacter big_k = new EnglishCharacter('K', 107);
	protected final EnglishCharacter big_l = new EnglishCharacter('L', 108);
	protected final EnglishCharacter big_m = new EnglishCharacter('M', 109);
	protected final EnglishCharacter big_n = new EnglishCharacter('N', 110);
	protected final EnglishCharacter big_o = new EnglishCharacter('O', 111);
	protected final EnglishCharacter big_p = new EnglishCharacter('P', 112);
	protected final EnglishCharacter big_q = new EnglishCharacter('Q', 113);
	protected final EnglishCharacter big_r = new EnglishCharacter('R', 114);
	protected final EnglishCharacter big_s = new EnglishCharacter('S', 115);
	protected final EnglishCharacter big_t = new EnglishCharacter('T', 116);
	protected final EnglishCharacter big_u = new EnglishCharacter('U', 117);
	protected final EnglishCharacter big_v = new EnglishCharacter('V', 118);
	protected final EnglishCharacter big_w = new EnglishCharacter('W', 119);
	protected final EnglishCharacter big_x = new EnglishCharacter('X', 120);
	protected final EnglishCharacter big_y = new EnglishCharacter('Y', 121);
	protected final EnglishCharacter big_z = new EnglishCharacter('Z', 122);

	HashMap<Long, EnglishCharacter> eMap = new HashMap<Long, EnglishCharacter>();

	public English() {
		eMap.put(32L, small_a);
		eMap.put(1024L, small_b);
		eMap.put(32768L, small_c);
		eMap.put(1048576L, small_d);
		eMap.put(135296L, small_e);
		eMap.put(4224L, small_f);
		eMap.put(135168L, small_g);
		eMap.put(262400L, small_h);
		eMap.put(256L, small_i);
		eMap.put(8192L, small_j);
		eMap.put(8224L, small_k);
		eMap.put(131072L, small_l);
		eMap.put(270592L, small_m);
		eMap.put(8448L, small_n);
		eMap.put(1056L, small_o);
		eMap.put(32800L, small_p);
		eMap.put(1048608L, small_q);
		eMap.put(33792L, small_r);
		eMap.put(16896L, small_s);
		eMap.put(4096L, small_t);
		eMap.put(65600L, small_u);
		eMap.put(2112L, small_v);
		eMap.put(67648L, small_w);
		eMap.put(96L, small_x);
		eMap.put(67584L, small_y);
		eMap.put(540672L, small_z);
		eMap.put(33L, big_a);
		eMap.put(1025L, big_b);
		eMap.put(32769L, big_c);
		eMap.put(1048577L, big_d);
		eMap.put(135297L, big_e);
		eMap.put(4225L, big_f);
		eMap.put(135169L, big_g);
		eMap.put(262401L, big_h);
		eMap.put(257L, big_i);
		eMap.put(8193L, big_j);
		eMap.put(8225L, big_k);
		eMap.put(131073L, big_l);
		eMap.put(270593L, big_m);
		eMap.put(8449L, big_n);
		eMap.put(1057L, big_o);
		eMap.put(32801L, big_p);
		eMap.put(1048609L, big_q);
		eMap.put(33793L, big_r);
		eMap.put(16897L, big_s);
		eMap.put(4097L, big_t);
		eMap.put(65601L, big_u);
		eMap.put(2113L, big_v);
		eMap.put(67649L, big_w);
		eMap.put(97L, big_x);
		eMap.put(67585L, big_y);
		eMap.put(540673L, big_z);
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

	private void MODE_ENGLISH() {
		EnglishCharacter temp = eMap.get(motion);
		setText(String.format("%c", temp.getUnicode()));
	}


	public String execute(long motionValue, InputConnection input_connection) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Eng - execute()");

		// kim // 150516 // init values


		// kim // 150507 // functional keys
		motion = motionValue;
		ic = input_connection;

		if(motion == 4L) {
			return " ";
		}
		else if(motion == 16L) {
			ic.deleteSurroundingText(1, 0);
			return "";
		}
		else {
			MODE_ENGLISH();
		}// kim // 150413 // switch by automata level


		return text_to_commit;
	}


	@Override
	public boolean isAllocatedMotion(long motion) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
		if( motion == 32L || motion == 1024L || motion == 32768L || motion == 1048576L || motion == 135296L ||
				motion == 4224L || motion == 135168L || motion == 262400L || motion == 256L || motion == 8192L ||
				motion == 8224L || motion == 131072L || motion == 270592L || motion == 8448L || motion == 1056L ||
				motion == 32800L || motion == 1048608L || motion == 33792L || motion == 16896L || motion == 4096L ||
				motion == 65600L || motion == 2112L || motion == 67648L || motion == 96L || motion == 67584L ||
				motion == 540672L || motion == 33L || motion == 1025L || motion == 32769L || motion == 1048577L || motion == 135297L ||
				motion == 4225L || motion == 135169L || motion == 262401L || motion == 257L || motion == 8193L ||
				motion == 8225L || motion == 131073L || motion == 270593L || motion == 8449L || motion == 1057L || motion == 32801L ||
				motion == 1048609L || motion == 33793L || motion == 16897L || motion == 4097L || motion == 65601L ||
				motion == 2113L || motion == 67649L || motion == 97L || motion == 67585L || motion == 540673L)
			return true;
		return false;
	}
}
