package com.fouram.nurumikeyboard.IME_Automata;

import android.view.inputmethod.InputConnection;

/**
* Created by soyeong on 15. 5. 17..
*/
public class Automata_type_Eng extends IME_Automata {
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


	private void MODE_ENGLISH() {
		switch (count_finger) {
		case 1: // [A, B, C, D, I, J, L, T]
			if (finger[INDEX_FINGER] == DIRECTION_DOT) { // a
				text_to_commit = String.format("%c", PREF_ENGLISH_S[0]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) { // b
				text_to_commit = String.format("%c", PREF_ENGLISH_S[1]);
			} else if (finger[RING__FINGER] == DIRECTION_DOT) { // c
				text_to_commit = String.format("%c", PREF_ENGLISH_S[2]);
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) { // d
				text_to_commit = String.format("%c", PREF_ENGLISH_S[3]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN) { // i
				text_to_commit = String.format("%c", PREF_ENGLISH_S[8]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOWN) { // j
				text_to_commit = String.format("%c", PREF_ENGLISH_S[9]);
			} else if (finger[RING__FINGER] == DIRECTION_RIGHT) { // l
				text_to_commit = String.format("%c", PREF_ENGLISH_S[11]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_RIGHT) { // t
				text_to_commit = String.format("%c", PREF_ENGLISH_S[19]);
			}
			else {
				text_to_commit="";
			}
			break;

		case 2: // [F, G, H, K, N, O, P, Q, R, S, U, V, X, Y, Z]
			if(finger[THUMB_FINGER] == DIRECTION_DOT){
				if (finger[INDEX_FINGER] == DIRECTION_DOT) { // a
					text_to_commit = String.format("%c", PREF_ENGLISH_B[0]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) { // b
					text_to_commit = String.format("%c", PREF_ENGLISH_B[1]);
				} else if (finger[RING__FINGER] == DIRECTION_DOT) { // c
					text_to_commit = String.format("%c", PREF_ENGLISH_B[2]);
				} else if (finger[PINKY_FINGER] == DIRECTION_DOT) { // d
					text_to_commit = String.format("%c", PREF_ENGLISH_B[3]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOWN) { // i
					text_to_commit = String.format("%c", PREF_ENGLISH_B[8]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_DOWN) { // j
					text_to_commit = String.format("%c", PREF_ENGLISH_B[9]);
				} else if (finger[RING__FINGER] == DIRECTION_RIGHT) { // l
					text_to_commit = String.format("%c", PREF_ENGLISH_B[11]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_RIGHT) { // t
					text_to_commit = String.format("%c", PREF_ENGLISH_B[19]);
				}else {
					text_to_commit="";
				}
			} else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT) { // F
				text_to_commit = String.format("%c", PREF_ENGLISH_S[5]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_RIGHT && finger[RING__FINGER] == DIRECTION_RIGHT) { // G
				text_to_commit = String.format("%c", PREF_ENGLISH_S[6]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[RING__FINGER] == DIRECTION_DOWN) { // H
				text_to_commit = String.format("%c", PREF_ENGLISH_S[7]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOWN) { // K
				text_to_commit = String.format("%c", PREF_ENGLISH_S[10]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN) { // N
				text_to_commit = String.format("%c", PREF_ENGLISH_S[13]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) { // O
				text_to_commit = String.format("%c", PREF_ENGLISH_S[14]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) { // P
				text_to_commit = String.format("%c", PREF_ENGLISH_S[15]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) { // Q
				text_to_commit = String.format("%c", PREF_ENGLISH_S[16]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) { // R
				text_to_commit = String.format("%c", PREF_ENGLISH_S[17]);
			} else if (finger[INDEX_FINGER] == DIRECTION_LEFT && finger[MIDLE_FINGER] == DIRECTION_LEFT) { // S
				text_to_commit = String.format("%c", PREF_ENGLISH_S[18]);
			} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[RING__FINGER] == DIRECTION_UP) { // U
				text_to_commit = String.format("%c", PREF_ENGLISH_S[20]);
			} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[MIDLE_FINGER] == DIRECTION_UP) { // V
				text_to_commit = String.format("%c", PREF_ENGLISH_S[21]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_UP) { // X
				text_to_commit = String.format("%c", PREF_ENGLISH_S[23]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP && finger[RING__FINGER] == DIRECTION_UP) { // Y
				text_to_commit = String.format("%c", PREF_ENGLISH_S[24]);
			} else if (finger[MIDLE_FINGER] == DIRECTION_LEFT && finger[RING__FINGER] == DIRECTION_LEFT) { // Z
				text_to_commit = String.format("%c", PREF_ENGLISH_S[25]);
			}
			else {
				text_to_commit="";
			}
			break;

		case 3: // [E, M, W]
			if(finger[THUMB_FINGER] == DIRECTION_DOT){
				if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT) { // F
					text_to_commit = String.format("%c", PREF_ENGLISH_B[5]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_RIGHT && finger[RING__FINGER] == DIRECTION_RIGHT) { // G
					text_to_commit = String.format("%c", PREF_ENGLISH_B[6]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[RING__FINGER] == DIRECTION_DOWN) { // H
					text_to_commit = String.format("%c", PREF_ENGLISH_B[7]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOWN) { // K
					text_to_commit = String.format("%c", PREF_ENGLISH_B[10]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN) { // N
					text_to_commit = String.format("%c", PREF_ENGLISH_B[13]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) { // O
					text_to_commit = String.format("%c", PREF_ENGLISH_B[14]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) { // P
					text_to_commit = String.format("%c", PREF_ENGLISH_B[15]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) { // Q
					text_to_commit = String.format("%c", PREF_ENGLISH_B[16]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) { // R
					text_to_commit = String.format("%c", PREF_ENGLISH_B[17]);
				} else if (finger[INDEX_FINGER] == DIRECTION_LEFT && finger[MIDLE_FINGER] == DIRECTION_LEFT) { // S
					text_to_commit = String.format("%c", PREF_ENGLISH_B[18]);
				} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[RING__FINGER] == DIRECTION_UP) { // U
					text_to_commit = String.format("%c", PREF_ENGLISH_B[20]);
				} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[MIDLE_FINGER] == DIRECTION_UP) { // V
					text_to_commit = String.format("%c", PREF_ENGLISH_B[21]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_UP) { // X
					text_to_commit = String.format("%c", PREF_ENGLISH_B[23]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_UP && finger[RING__FINGER] == DIRECTION_UP) { // Y
					text_to_commit = String.format("%c", PREF_ENGLISH_B[24]);
				} else if (finger[MIDLE_FINGER] == DIRECTION_LEFT && finger[RING__FINGER] == DIRECTION_LEFT) { // Z
					text_to_commit = String.format("%c", PREF_ENGLISH_B[25]);
				}
				else {
					text_to_commit="";
				}
			}else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
				&& finger[RING__FINGER] == DIRECTION_RIGHT) { // E
					text_to_commit = String.format("%c", PREF_ENGLISH_S[4]);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
				&& finger[RING__FINGER] == DIRECTION_DOWN) { // M
					text_to_commit = String.format("%c", PREF_ENGLISH_S[12]);
			} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[MIDLE_FINGER] == DIRECTION_UP
				&& finger[RING__FINGER] == DIRECTION_UP) { // W
					text_to_commit = String.format("%c", PREF_ENGLISH_S[22]);
			}
			else {
				text_to_commit="";
			}
			break;
		case 4:
			if(finger[THUMB_FINGER] == DIRECTION_DOT){
				if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
					&& finger[RING__FINGER] == DIRECTION_RIGHT) { // E
						text_to_commit = String.format("%c", PREF_ENGLISH_B[4]);
				} else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
					&& finger[RING__FINGER] == DIRECTION_DOWN) { // M
						text_to_commit = String.format("%c", PREF_ENGLISH_B[12]);
				} else if (finger[INDEX_FINGER] == DIRECTION_UP && finger[MIDLE_FINGER] == DIRECTION_UP
					&& finger[RING__FINGER] == DIRECTION_UP) { // W
						text_to_commit = String.format("%c", PREF_ENGLISH_B[22]);
				}
				else {
					text_to_commit="";
				}
			}
			else {
				text_to_commit="";
			}
		default:
			text_to_commit="";
			break;
		}
	}


	public String execute(int[] finger_array, InputConnection input_connection) {


		// kim // 150516 // init values
		int idx = 5;
		finger = finger_array;
		ic = input_connection;
		count_finger = 0;

		// kim // 150412 // count finger
		while (idx-- > 0)
			if (finger[idx] != DIRECTION_EMPTY)
				count_finger++;


		// kim // 150507 // functional keys
		if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_RIGHT) {
			return " ";
		}

		else if (count_finger == 1 && finger[PINKY_FINGER] == DIRECTION_LEFT) {
			ic.deleteSurroundingText(1, 0);
			return "";
		}

		else if (count_finger == 2 && finger[THUMB_FINGER] == DIRECTION_DOT
			&& finger[PINKY_FINGER] == DIRECTION_DOT) {
				return "\n";
		}
		else {
			MODE_ENGLISH();
		}// kim // 150413 // switch by automata level


		return text_to_commit;
	}
}
