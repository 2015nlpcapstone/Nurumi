package com.fouram.nurumikeyboard.IME_Automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_Kor_3 extends IME_Automata {

	private final int LEVEL_CHO_SEONG = 0;
	private final int LEVEL_JUNG_SEONG = 1;
	private final int LEVEL_JUNG_SEONG_TO_JONG_SEONG = 2;
	private final int LEVEL_JONG_SEONG = 3;
	private final int LEVEL_JONG_SEONG_TO_CHO_SEONG = 4;

	private final int CHO_SEONG = 0;
	private final int JUNG_SEONG = 1;
	private final int JONG_SEONG = 2;
	private final int WISP_FLARE = 3;

	private int buffer[] = {'\0', '\0', '\0', '\0'};
	private int automata_level = 0;
	private int bok_ja_eum_jong_seong = 0;
	private Boolean ready_to_commit_text;


	// yoon // 150516 // get a Korean character code key value
	private int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
		return ((AC00 + ((cho_seong * 21) + jung_seong) * 28) + jong_seong);
	}

	// yoon // 150517 // replace character macro
	private int replace_to(int ret) {
		ic.deleteSurroundingText(1, 0);
		return ret;
	}  

	// yoon // 150518 // assign text to commit &  & validate for committing
	private void text_to_commit(String str) {
		text_to_commit = str;
		ready_to_commit_text = true;
	}

	// yoon // 150517 // clean character buffer
	private void buffer_clean() {
		int i = 4;
		while (i-- > 0)
			buffer[i] = 0;
		bok_ja_eum_jong_seong = 0;
	}

	// yoon // 150517 // log print for debugging
	private void print_log(String str) {
		Log.d(str, "-------------------------------------------");
		Log.d(str, "automata level : " + automata_level);
		Log.d(str, "finger count : " + count_finger);
		Log.d(str, "motion : " + finger[THUMB_FINGER] + " " + finger[INDEX_FINGER] + " "
				+ finger[MIDLE_FINGER] + " " + finger[RING__FINGER] + " " + finger[PINKY_FINGER]);
		Log.d(
				str,
				"current buffer : " + buffer[0] + " " + buffer[1] + " " + buffer[2] + " " + buffer[3]
						+ " ["
						+ String.format("%c", generate_korean_char_code(buffer[0], buffer[1], buffer[2])) + "]");
		Log.d(str, "-------------------------------------------");
	}

	// yoon // 150516 // div&conq.

	private void LEVEL_CHO_SEONG() {


		// yoon // 150517 // buffer clean
		buffer_clean();

		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150413 // case for single finger

			// yoon // 150413 // Conditional Statements for 'ja-eum cho-seong'
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 11;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 6; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 2; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 5; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}


			// yoon // 150413 // Conditional Statements for 'mo-eum letter'
			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
				automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					text_to_commit( "��" );
					break;
				case DIRECTION_RIGHT:
					text_to_commit( "��" );
					break;
				case DIRECTION_DOWN:
					text_to_commit( "��" );
					break;
				case DIRECTION_LEFT:
					text_to_commit( "��" );
					break;
				}
			}
			else automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.

			break; // yoon // 150413 // break for single finger

		case 2: // yoon // 150424 // case for two fingers
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 0;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 3;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 7;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
				automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					text_to_commit( "��" );
					break;
				case DIRECTION_RIGHT:
					text_to_commit( "��" );
					break;
				case DIRECTION_DOWN:
					text_to_commit( "��" );
					break;
				case DIRECTION_LEFT:
					text_to_commit( "��" );
					break;
				}
			}
			else automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.

			break; // yoon // 150413 // break for two fingers

		case 3: // yoon // 150507 // case for three fingers
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 12; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
					&& finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 9; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}
			// yoon // 150520 // cond. statements for 'doen-so-ri' 
			else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 1;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 4;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 8;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}
			else automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.


			break;// yoon // 150507 // break for three fingers

		case 4: // yoon // 150507 // case for four fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
				buffer[CHO_SEONG] = 18;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}
			// yoon // 150520 // cond. statements for 'doen-so-ri'
			else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 13; // '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 10; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}
			else {
				automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.

				switch (finger[INDEX_FINGER]) {
				case DIRECTION_RIGHT:
					text_to_commit( "��" );
					break;
				case DIRECTION_DOWN:
					text_to_commit( "��" );
					break;
				}
			}
			break;// yoon // 150507 // break for four fingers
		}
	};

	private void LEVEL_JUNG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150413 // case for single finger
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 11;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 6; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 2; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 5; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150413 // Conditional Statements for 'hout-mo-eum jung_seong'

			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					buffer[JUNG_SEONG] = 8; // '��'
					break;
				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 0; // '��'
					break;
				case DIRECTION_DOWN:
					buffer[JUNG_SEONG] = 13;// '��'
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 4; // '��'
					break;
				}
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}

			break; // yoon // 150413 // break for single finger

		case 2: // yoon // 150413 // case for two fingers
			// 150517 // mod ipt method

			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 0 ? replace_to(15) : 0);// '��' or '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 3 ? replace_to(16) : 3);// '��' or '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 7 ? replace_to(17) : 7);// '��' or '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150507 // Conditional Statements for 'hout-mo-eum jung_seong'

			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					buffer[JUNG_SEONG] = 12;// '��'
					break;
				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 2; // '��'
					break;
				case DIRECTION_DOWN:
					buffer[JUNG_SEONG] = 17; // '��'
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 6; // '��'
					break;
				}

				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}

			// yoon // 150517 // Conditional Statements for 'bok-mo-eum jung_seong'

			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 1; // '��'

					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 5; // '��'

					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;

					break;
				}

			}

			break; // yoon // 150413 // break for two fingers

		case 3: // yoon // 150507 // case for three fingers
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 12 ? replace_to(14) : 12); // '��' or '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
					&& finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 9 ? replace_to(10) : 9);;  // '��' or '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}


			// yoon // 150517 // for bok-mo-eum jung-seong
			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY
					&& finger[RING__FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 3; // '��'
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 7; // '��'
					break;
				}
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			}
			// yoon // 150520 // cond. statements for 'doen-so-ri' 
			else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 1;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 4;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 8;// '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			else automata_level = LEVEL_CHO_SEONG; // yoon // 150525 // for wrong motion ipt.
			break;

		case 4: // yoon // 150516 // case for four fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
				buffer[CHO_SEONG] = 18;// '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
					&& finger[MIDLE_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY
					&& finger[PINKY_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {
				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 18; // '��'
					break;
				case DIRECTION_DOWN:
					buffer[JUNG_SEONG] = 20; // '��'
					break;
				}
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}
			// yoon // 150520 // cond. statements for 'doen-so-ri'
			else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 13; // '��'
				text_to_commit(String.format("%c", PREF_CHO[buffer[CHO_SEONG]]));
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 10; // '��'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}
		}
	};

	private void LEVEL_JUNG_SEONG_TO_JONG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150517 // case for single finger

			if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT) { // '��' + '��'
				buffer[JUNG_SEONG] = 9; // '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT) { // '��' +
				// '��'
				buffer[JUNG_SEONG] = 14; // '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else
				LEVEL_JONG_SEONG();

			break;

		case 2: // yoon // 150517 // case for two fingers

			if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT
			&& finger[RING__FINGER] != DIRECTION_EMPTY) { // '��' + '��'
				buffer[JUNG_SEONG] = 10; // '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT
					&& finger[RING__FINGER] != DIRECTION_EMPTY) { // '��' + '��'
				buffer[JUNG_SEONG] = 15; // '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else
				LEVEL_JONG_SEONG();

			break;

		case 3: // yoon // 150517 // case for three fingers

			LEVEL_JONG_SEONG();
			break;

		case 4: // yoon // 150517 // case for four fingers
			if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
			&& finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
				if (buffer[JUNG_SEONG] == 8) { // '��'
					buffer[JUNG_SEONG] = 11; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 13) { // '��'
					buffer[JUNG_SEONG] = 16; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 0) { // '��'
					buffer[JUNG_SEONG] = 1; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 2) { // '��'
					buffer[JUNG_SEONG] = 3; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 4) { // '��'
					buffer[JUNG_SEONG] = 5; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 6) { // '��'
					buffer[JUNG_SEONG] = 7; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 18) { // '��'
					buffer[JUNG_SEONG] = 19; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				}
				// yoon // 150520 // triple lutz
				else if (buffer[JUNG_SEONG] == 9) { // '��' + '��'
					buffer[JUNG_SEONG] = 10; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 14) { // '��' + '��'
					buffer[JUNG_SEONG] = 15; // '��'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				}
				else LEVEL_CHO_SEONG();
			}
			else LEVEL_CHO_SEONG();

			break;
		}
	};

	private void LEVEL_JONG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150413 // case for single finger

			// yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 21;// '��'
				buffer[WISP_FLARE] = 11;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 16;// '��'
				buffer[WISP_FLARE] = 6;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 4;// '��'
				buffer[WISP_FLARE] = 2;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 8;// '��'
				buffer[WISP_FLARE] = 5;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else LEVEL_CHO_SEONG();
			break;
		case 2: // yoon // 150424 // case for two fingers

			// yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 1;// '��'
				buffer[WISP_FLARE] = 0;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 7;// '��'
				buffer[WISP_FLARE] = 3;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 17;// '��'
				buffer[WISP_FLARE] = 7;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} 
			else LEVEL_CHO_SEONG();
			break;

		case 3: // yoon // 150521 // case for three fingers

			// yoon // 150527 // Conditional Statements for 'ja-eum jong-seong'
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 22;// '��'
				buffer[WISP_FLARE] = 12;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			else if(finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 19;// '��'
				buffer[WISP_FLARE] = 9;// '��'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}

			else LEVEL_CHO_SEONG();
			break;
		}
	};

	private void LEVEL_JONG_SEONG_TO_CHO_SEONG() {

		// yoon // 150517 // Conditional Statements for Wisp phenomenon, jong-seong becomes to cho-seong 
		if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[INDEX_FINGER] != DIRECTION_DOT) {

			text_to_commit(
					String.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									bok_ja_eum_jong_seong)) );

			ic.deleteSurroundingText(1, 0);
			ic.commitText(String.valueOf(text_to_commit + '_'), 2) ;

			int wisp_flare = buffer[WISP_FLARE];
			buffer_clean();
			buffer[CHO_SEONG] = wisp_flare;
			LEVEL_JUNG_SEONG();

		}

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 2 && finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 1) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 2;
				buffer[WISP_FLARE] = 0;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 9;
				buffer[WISP_FLARE] = 0;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else
				LEVEL_CHO_SEONG();
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 1 && finger[MIDLE_FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 10;
				buffer[WISP_FLARE] = 6;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else
				LEVEL_CHO_SEONG();
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 2 && finger[INDEX_FINGER] == DIRECTION_DOT 
				&& finger[RING__FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 11;
				buffer[WISP_FLARE] = 7;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (buffer[JONG_SEONG] == 11) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 14;
				buffer[WISP_FLARE] = 7;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else
				LEVEL_CHO_SEONG();
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 3 && finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
				&& finger[PINKY_FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 1) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 3;
				buffer[WISP_FLARE] = 9;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 12;
				buffer[WISP_FLARE] = 9;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (buffer[JONG_SEONG] == 17) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 18;
				buffer[WISP_FLARE] = 9;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (buffer[JONG_SEONG] == 19) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 20;
				buffer[WISP_FLARE] = 9;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			else
				LEVEL_CHO_SEONG();
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 3 && finger[INDEX_FINGER] == DIRECTION_DOT 
				&& finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 4) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 5;
				buffer[WISP_FLARE] = 12;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			else
				LEVEL_CHO_SEONG();
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 2 && finger[MIDLE_FINGER] == DIRECTION_DOT 
				&& finger[RING__FINGER] == DIRECTION_DOT) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 13;
				buffer[WISP_FLARE] = 3;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else
				LEVEL_CHO_SEONG();     
		} // bok ja eum + '��' end.

		// yoon // 150527 // conditional statement for bok-ja-eum : () + '��'
		else if (count_finger == 4 && finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT 
				&& finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT ) { // bok ja eum + '��'

			if (buffer[JONG_SEONG] == 4) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 6;
				buffer[WISP_FLARE] = 18;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			else if (buffer[JONG_SEONG] == 8) { // '��' + '��'
				bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 15;
				buffer[WISP_FLARE] = 18;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else
				LEVEL_CHO_SEONG();     
		} // bok ja eum + '��' end.

		else
			LEVEL_CHO_SEONG();
	};


	// yoon // THIS IS WHAT I'M REALLY WANT TO DO !!

	public String execute(int[] finger_array, InputConnection input_connection) {

		// yoon // 150516 // init values
		int idx = 5;
		finger = finger_array;
		ic = input_connection;
		count_finger = 0;
		ready_to_commit_text = false; // yoon // 150518 // for validate motion to text functionality 

		// yoon // 150412 // count finger
		while (idx-- > 0)
			if (finger[idx] != DIRECTION_EMPTY)
				count_finger++;

		if (ENABLE_DEBUG) // yoon // 150413 // for debug
			print_log("Automata bgn ...");

		// yoon // 150507 // functional keys

		if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_RIGHT) {
			automata_level = LEVEL_CHO_SEONG;
			return " ";
		}

		else if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_LEFT) {
			automata_level = LEVEL_CHO_SEONG;
			ic.deleteSurroundingText(1, 0);
			return "";
		}

		else if (count_finger == 2 && finger[THUMB_FINGER] == DIRECTION_DOT
				&& finger[PINKY_FINGER] == DIRECTION_DOT) {
			automata_level = LEVEL_CHO_SEONG;
			return "\n";
		}

		// yoon // 150413 // switch by automata level
		// yoon // 150516 // Code refactoring : Devide & Conquer

		switch (automata_level) { // yoon // step 1. switch by automata level

		case LEVEL_CHO_SEONG:
			LEVEL_CHO_SEONG();
			break;

		case LEVEL_JUNG_SEONG:
			LEVEL_JUNG_SEONG();
			break;

		case LEVEL_JUNG_SEONG_TO_JONG_SEONG:
			LEVEL_JUNG_SEONG_TO_JONG_SEONG();
			break;

		case LEVEL_JONG_SEONG:
			LEVEL_JONG_SEONG();
			break;

		case LEVEL_JONG_SEONG_TO_CHO_SEONG:
			LEVEL_JONG_SEONG_TO_CHO_SEONG();
			break;
		}

		if (ENABLE_DEBUG) // yoon // 150413 // for debug
			print_log("Automata ... end");
		return (ready_to_commit_text == true ? text_to_commit : "");
	}


}
