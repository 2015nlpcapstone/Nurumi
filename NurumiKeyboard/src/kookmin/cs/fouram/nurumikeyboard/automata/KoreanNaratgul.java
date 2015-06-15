package kookmin.cs.fouram.nurumikeyboard.automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class KoreanNaratgul extends IME_Automata {

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
				buffer[CHO_SEONG] = 11;// 'ㅇ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 2; // 'ㄴ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 0; // 'ㄱ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}


			// yoon // 150413 // Conditional Statements for 'mo-eum letter'
			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY)
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					text_to_commit( "ㅗ" ); // 'ㅗ'
					break;
				case DIRECTION_RIGHT:
					text_to_commit( "ㅏ"  );  //'ㅏ'
					break;
				case DIRECTION_DOWN:
					text_to_commit( "ㅜ" );  //'ㅜ'
					break;
				case DIRECTION_LEFT:
					text_to_commit( "ㅓ" ); //'ㅓ'
					break;
				}
			break; // yoon // 150413 // break for single finger

		case 2: // yoon // 150424 // case for two fingers
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 9;// 'ㅅ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 3;// 'ㄷ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 7;// 'ㅂ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 1;// 'ㄲ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					text_to_commit( "ㅛ" ); //'ㅛ'
					break;
				case DIRECTION_RIGHT:
					text_to_commit( "ㅑ"); //'ㅑ'
					break;
				case DIRECTION_DOWN:
					text_to_commit( "ㅠ" ); //'ㅠ'
					break;
				case DIRECTION_LEFT:
					text_to_commit( "ㅕ" ); //'ㅕ'
					break;

				}
			} else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
					&& finger[RING__FINGER] == DIRECTION_RIGHT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
				text_to_commit( "ㅐ" ); //'ㅐ'
			}else if (finger[INDEX_FINGER] == DIRECTION_LEFT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
					&& finger[RING__FINGER] == DIRECTION_LEFT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
				text_to_commit( "ㅔ" ); //'ㅔ'
			}
			break; // yoon // 150413 // break for two fingers

		case 3: // yoon // 150507 // case for three fingers
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 12; // 'ㅈ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 4; // 'ㄸ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 8; // 'ㅃ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 10; // 'ㅆ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
					&& finger[RING__FINGER] == DIRECTION_RIGHT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
				text_to_commit( "ㅡ" ); //'ㅡ'
			}else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
					&& finger[RING__FINGER] == DIRECTION_DOWN && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
				text_to_commit( "ㅣ" ); //'ㅣ'
			}

			break;// yoon // 150507 // break for three fingers

		case 4: // yoon // 150507 // case for four fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_DOT && finger[THUMB_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = 13;// 'ㅉ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			break;// yoon // 150507 // break for four fingers

		default:
			text_to_commit( "" );
		}
	};

	private void LEVEL_JUNG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150413 // case for single finger
			// 150517 // mod ipt method
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 11 ? replace_to(6) : 11);// 'ㅇ' or 'ㅁ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT ) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 2 ? replace_to(5) : 2);// 'ㄴ' or 'ㄹ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 0 ? replace_to(15) : 0);// 'ㄱ' or 'ㅋ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150413 // Conditional Statements for 'hout-mo-eum jung_seong'

			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					buffer[JUNG_SEONG] = 8; // 'ㅗ'
					break;
				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 0; // 'ㅏ'
					break;
				case DIRECTION_DOWN:
					buffer[JUNG_SEONG] = 13;// 'ㅜ'
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 4; // 'ㅓ'
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
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 9 ? replace_to(18) : 9);// 'ㅅ' or 'ㅎ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 3 ? replace_to(16) : 3);// 'ㄷ' or 'ㅌ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 7 ? replace_to(17) : 7);// 'ㅂ' or 'ㅍ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}

			// yoon // 150507 // Conditional Statements for 'hout-mo-eum jung_seong'

			else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
				switch (finger[INDEX_FINGER]) {

				case DIRECTION_UP:
					buffer[JUNG_SEONG] = 12;// 'ㅛ'
					break;
				case DIRECTION_RIGHT:
					buffer[JUNG_SEONG] = 2; // 'ㅑ'
					break;
				case DIRECTION_DOWN:
					buffer[JUNG_SEONG] = 17; // 'ㅠ'
					break;
				case DIRECTION_LEFT:
					buffer[JUNG_SEONG] = 6; // 'ㅕ'
					break;
				}

				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[RING__FINGER] == DIRECTION_RIGHT) {

				buffer[JUNG_SEONG] = 1; // 'ㅐ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}else if(finger[INDEX_FINGER] == DIRECTION_LEFT && finger[RING__FINGER] == DIRECTION_LEFT) {

				buffer[JUNG_SEONG] = 5; // 'ㅔ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}

			// yoon // 150517 // Conditional Statements for 'bok-mo-eum jung_seong'


			break; // yoon // 150413 // break for two fingers

		case 3: // yoon // 150507 // case for three fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_DOT ) {
				buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 12 ? replace_to(14) : 12);// 'ㅈ' or 'ㅊ'
				text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
				automata_level = LEVEL_JUNG_SEONG;
			}else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
					&& finger[RING__FINGER] == DIRECTION_RIGHT) {

				buffer[JUNG_SEONG] = 18; // 'ㅡ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
					&& finger[RING__FINGER] == DIRECTION_DOWN) {
				buffer[JUNG_SEONG] = 20; // 'ㅣ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;


			} else if (finger[THUMB_FINGER] == DIRECTION_RIGHT && finger[INDEX_FINGER] == DIRECTION_RIGHT
					&& finger[RING__FINGER] == DIRECTION_RIGHT) {
				buffer[JUNG_SEONG] = 3; // 'ㅒ'

				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_LEFT && finger[INDEX_FINGER] == DIRECTION_LEFT
					&& finger[RING__FINGER] == DIRECTION_LEFT) {
				buffer[JUNG_SEONG] = 7; // 'ㅖ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
				automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
			}

		}
	};

	private void LEVEL_JUNG_SEONG_TO_JONG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150517 // case for single finger

			if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT) { // 'ㅗ' + 'ㅏ'
				buffer[JUNG_SEONG] = 9; // 'ㅘ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT) { // 'ㅜ' +
				// 'ㅓ'
				buffer[JUNG_SEONG] = 14; // 'ㅝ'
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
			&& finger[RING__FINGER] != DIRECTION_EMPTY) { // 'ㅗ' + 'ㅐ'
				buffer[JUNG_SEONG] = 10; // 'ㅚ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT
					&& finger[RING__FINGER] != DIRECTION_EMPTY) { // 'ㅜ' + 'ㅔ'
				buffer[JUNG_SEONG] = 15; // 'ㅞ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format("%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
				automata_level = JONG_SEONG;
			} else
				LEVEL_JONG_SEONG();

			break;

		case 3: // yoon // 150517 // case for three fingers
			if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
			&& finger[RING__FINGER] == DIRECTION_DOWN) {
				if (buffer[JUNG_SEONG] == 8) { // 'ㅗ'
					buffer[JUNG_SEONG] = 11; // 'ㅚ'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 13) { // 'ㅜ'
					buffer[JUNG_SEONG] = 16; // 'ㅟ'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				} else if (buffer[JUNG_SEONG] == 18) { // 'ㅡ'
					buffer[JUNG_SEONG] = 19; // 'ㅢ'
					ic.deleteSurroundingText(1, 0);
					text_to_commit(
							String.format("%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
					automata_level = JONG_SEONG;
				}else
					LEVEL_JONG_SEONG();
			}else
				LEVEL_JONG_SEONG();

			break;

		case 4: // yoon // 150517 // case for four fingers

			LEVEL_JONG_SEONG();
			break;

		}
	};

	private void LEVEL_JONG_SEONG() {
		switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

		case 1: // yoon // 150413 // case for single finger

			// yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
			&& finger[RING__FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 21;// 'ㅇ'
				buffer[WISP_FLARE] = 11;// 'ㅇ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_EMPTY
					&& finger[RING__FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 4;// 'ㄴ'
				buffer[WISP_FLARE] = 2;// 'ㄴ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[RING__FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
					&& finger[INDEX_FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 1;// 'ㄱ'
				buffer[WISP_FLARE] = 0;// 'ㄱ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			break;
		case 2: // yoon // 150424 // case for two fingers

			// yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
			&& finger[RING__FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 19;// 'ㅅ'
				buffer[WISP_FLARE] = 9;// 'ㅅ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
					&& finger[INDEX_FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 7;// 'ㄷ'
				buffer[WISP_FLARE] = 3;// 'ㄷ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_EMPTY) {
				buffer[JONG_SEONG] = 17;// 'ㅂ'
				buffer[WISP_FLARE] = 7;// 'ㅂ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
				print_log("ok");
				buffer[JONG_SEONG] = 2;// 'ㄲ'
				buffer[WISP_FLARE] = 1;// 'ㄲ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			break;
		case 3: // yoon // 150424 // case for three fingers
			print_log("ok");
			if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
					&& finger[RING__FINGER] == DIRECTION_DOT) {
				print_log("ok");
				buffer[JONG_SEONG] = 22;// 'ㅈ'
				buffer[WISP_FLARE] = 12;// 'ㅈ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
					&& finger[MIDLE_FINGER] == DIRECTION_DOT) {
				buffer[JONG_SEONG] = 20;// 'ㅆ'
				buffer[WISP_FLARE] = 10;// 'ㅆ'
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}
			break;

		case 4: // yoon // 150517 // case for four fingers

			LEVEL_JONG_SEONG_TO_CHO_SEONG();
			break;

		}
	};

	private void LEVEL_JONG_SEONG_TO_CHO_SEONG() {

		// yoon // 150517 // Conditional Statements for Wisp phenomenon
		if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[INDEX_FINGER] != DIRECTION_DOT) {
			switch(count_finger) {
			case 1:
				if (finger[INDEX_FINGER] == DIRECTION_RIGHT || finger[INDEX_FINGER] == DIRECTION_LEFT ||
				finger[INDEX_FINGER] == DIRECTION_UP || finger[INDEX_FINGER] == DIRECTION_DOWN) {
					text_to_commit(
							String.format(
									"%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
											bok_ja_eum_jong_seong)));

					ic.deleteSurroundingText(1, 0);
					ic.commitText(String.valueOf(text_to_commit + '_'), 2);

					int wisp_flare = buffer[WISP_FLARE];
					buffer_clean();
					buffer[CHO_SEONG] = wisp_flare;
					LEVEL_JUNG_SEONG();

				}else
					automata_level= LEVEL_CHO_SEONG;
				break;
			case 2:
				if (finger[MIDLE_FINGER] == DIRECTION_RIGHT || finger[MIDLE_FINGER] == DIRECTION_LEFT ||
				finger[MIDLE_FINGER] == DIRECTION_UP || finger[MIDLE_FINGER] == DIRECTION_DOWN ||
				finger[RING__FINGER] == DIRECTION_LEFT || finger[RING__FINGER] == DIRECTION_RIGHT) {
					text_to_commit(
							String.format(
									"%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
											bok_ja_eum_jong_seong)));

					ic.deleteSurroundingText(1, 0);
					ic.commitText(String.valueOf(text_to_commit + '_'), 2);

					int wisp_flare = buffer[WISP_FLARE];
					buffer_clean();
					buffer[CHO_SEONG] = wisp_flare;
					LEVEL_JUNG_SEONG();

				}else
					automata_level= LEVEL_CHO_SEONG;
				break;
			case 3:
				if (finger[MIDLE_FINGER] == DIRECTION_RIGHT || finger[MIDLE_FINGER] == DIRECTION_DOWN
				|| finger[RING__FINGER] == DIRECTION_RIGHT || (finger[RING__FINGER] == DIRECTION_LEFT &&finger[MIDLE_FINGER] == DIRECTION_EMPTY)  ) {
					text_to_commit(
							String.format(
									"%c",
									generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
											bok_ja_eum_jong_seong)));

					ic.deleteSurroundingText(1, 0);
					ic.commitText(String.valueOf(text_to_commit + '_'), 2);

					int wisp_flare = buffer[WISP_FLARE];
					buffer_clean();
					buffer[CHO_SEONG] = wisp_flare;
					LEVEL_JUNG_SEONG();

				}else
					automata_level= LEVEL_CHO_SEONG;
				break;

			}

		} else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
				&& finger[RING__FINGER]==DIRECTION_EMPTY && finger[THUMB_FINGER]==DIRECTION_EMPTY) { // 'ㅅ'

			if (buffer[JONG_SEONG] == 1) { // 'ㄱ' + 'ㅅ'
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
			} else if (buffer[JONG_SEONG] == 17) { // 'ㅂ' + 'ㅅ'
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
			} else if (buffer[JONG_SEONG] == 8) { // 'ㄹ' + 'ㅅ'
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
			}  else if( buffer[JONG_SEONG] == 19) { // 'ㅅ'->'ㅎ'
				//bok_ja_eum_jong_seong = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = 27;
				buffer[WISP_FLARE] = 18;
				ic.deleteSurroundingText(1, 0);
				text_to_commit(
						String
						.format(
								"%c",
								generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
										buffer[JONG_SEONG])) );
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if(buffer[JONG_SEONG] == 12) { // 'ㄽ'->'ㅀ'

				bok_ja_eum_jong_seong = 8;
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

		} else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
				&& finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 4 ) { // 'ㅈ'-> 'ㄵ'

			bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 5;
			buffer[WISP_FLARE] = 12;
			ic.deleteSurroundingText(1, 0);
			print_log("ok");

			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;

		} else if(finger[RING__FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_EMPTY
				&& finger[MIDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 8) { // 'ㄹ'->'ㄺ'

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
		} else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
				&& finger[MIDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8) { // 'ㄹ'->'ㄼ'

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
		} else if(finger[INDEX_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 21 &&
				finger[MIDLE_FINGER]== DIRECTION_EMPTY && finger[RING__FINGER]== DIRECTION_EMPTY) { // 'ㅇ'->'ㅁ'

			// bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 16;
			buffer[WISP_FLARE] = 6;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		} else if(finger[MIDLE_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 4
				&& finger[RING__FINGER] == DIRECTION_EMPTY  && finger[INDEX_FINGER] == DIRECTION_EMPTY) { // 'ㄴ'->'ㄹ'

			//bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 8;
			buffer[WISP_FLARE] = 5;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		}  else if( finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 1) { // 'ㄱ'->'ㅋ'

			//bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 24;
			buffer[WISP_FLARE] = 15;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		}  else if(finger[MIDLE_FINGER] == DIRECTION_DOT &&  finger[RING__FINGER] == DIRECTION_DOT
				&& finger[INDEX_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 7) { // 'ㄷ'->'ㅌ'

			//bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 25;
			buffer[WISP_FLARE] = 16;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		}  else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
				&& finger[MIDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 17 ) { // 'ㅂ'->'ㅍ'

			// bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 26;
			buffer[WISP_FLARE] = 17;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		} else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
				&& finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 22) { // 'ㅈ'->'ㅊ'

			//bok_ja_eum_jong_seong = buffer[JONG_SEONG];
			buffer[JONG_SEONG] = 23;
			buffer[WISP_FLARE] = 14;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		} else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
				&& finger[MIDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 11 ) { // 'ㄼ'->'ㄹㅍ'

			bok_ja_eum_jong_seong = 8;
			buffer[JONG_SEONG] = 14;
			buffer[WISP_FLARE] = 17;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		} else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[RING__FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
				&& finger[INDEX_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8 ) { // 'ㄹ'->'ㄹㅌ'

			bok_ja_eum_jong_seong = 8;
			buffer[JONG_SEONG] = 13;
			buffer[WISP_FLARE] = 16;
			ic.deleteSurroundingText(1, 0);
			text_to_commit(
					String
					.format(
							"%c",
							generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
									buffer[JONG_SEONG])) );
			automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
		}  else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
				&& finger[RING__FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8 ) { // 'ㄹ'->'ㄹㅁ'

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
		}  else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
				&& finger[RING__FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 4 ) { // 'ㄴ'->ㄴㅎ'
			print_log("Doo ...");
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
		}    else
			automata_level = LEVEL_CHO_SEONG;
	};


	// yoon // THIS IS WHAT I'M REALLY WANT TO DO !!
	public String execute(int[] finger_array, InputConnection input_connection) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_2 - execute()");

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
			if(automata_level == 4){

				automata_level = LEVEL_CHO_SEONG;
				return "";
			}else {
				automata_level = LEVEL_CHO_SEONG;
				return " ";
			}
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

	@Override
	public boolean isAllocatedMotion(int[] finger) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_2 - isAllocatedMotion()");
		count_finger = 0;
		int idx = 5;
		while (idx-- > 0)
			if (finger[idx] != DIRECTION_EMPTY)
				count_finger++;

		if(count_finger == 1 && ( (finger[INDEX_FINGER] == DIRECTION_DOT)  || (finger[MIDLE_FINGER] == DIRECTION_DOT)   || // ㅇ/ㅁ ㄴ/ㄹ
				(finger[RING__FINGER] == DIRECTION_DOT)  ||											   // ㄱ/ㅋ
				(finger[INDEX_FINGER] == DIRECTION_UP)   || (finger[INDEX_FINGER] == DIRECTION_DOWN)  || // ㅗ ㅜ
				(finger[INDEX_FINGER] == DIRECTION_RIGHT)|| (finger[INDEX_FINGER] == DIRECTION_LEFT)  || // ㅏ ㅓ
				(finger[THUMB_FINGER] == DIRECTION_RIGHT)|| (finger[THUMB_FINGER] == DIRECTION_LEFT)  )) // space backspace
			return true;
		else if(count_finger == 2 && (((finger[INDEX_FINGER] == DIRECTION_DOT)   && (finger[MIDLE_FINGER] == DIRECTION_DOT))  || // ㅅ/ㅎ
				((finger[MIDLE_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))  || // ㄷ/ㅌ
				((finger[INDEX_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))  || // ㅂ/ㅍ
				((finger[THUMB_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))  || // ㄲ
				((finger[INDEX_FINGER] == DIRECTION_UP)    && (finger[MIDLE_FINGER] == DIRECTION_UP))   || // ㅛ
				((finger[INDEX_FINGER] == DIRECTION_DOWN)  && (finger[MIDLE_FINGER] == DIRECTION_DOWN)) || // ㅠ
				((finger[INDEX_FINGER] == DIRECTION_RIGHT) && (finger[MIDLE_FINGER] == DIRECTION_RIGHT))|| // ㅑ
				((finger[INDEX_FINGER] == DIRECTION_LEFT)  && (finger[MIDLE_FINGER] == DIRECTION_LEFT)) || // ㅕ
				((finger[INDEX_FINGER] == DIRECTION_RIGHT) && (finger[RING__FINGER] == DIRECTION_RIGHT))|| // ㅐ
				((finger[INDEX_FINGER] == DIRECTION_LEFT)  && (finger[RING__FINGER] == DIRECTION_LEFT)) || // ㅔ
				((finger[THUMB_FINGER] == DIRECTION_DOT)   && (finger[PINKY_FINGER] == DIRECTION_DOT))  || //enter
				((finger[THUMB_FINGER] == DIRECTION_RIGHT) && (finger[MIDLE_FINGER] == DIRECTION_RIGHT))|| //right move
				((finger[THUMB_FINGER] == DIRECTION_LEFT)  && (finger[MIDLE_FINGER] == DIRECTION_LEFT)) )) //left move
			return true;
		else if(count_finger == 3 && ( ((finger[INDEX_FINGER] == DIRECTION_DOT)   && (finger[MIDLE_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))   || // ㅈ/ㅊ
				((finger[THUMB_FINGER] == DIRECTION_DOT)   && (finger[MIDLE_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))   || // ㄸ
				((finger[THUMB_FINGER] == DIRECTION_DOT)   && (finger[INDEX_FINGER] == DIRECTION_DOT)   && (finger[RING__FINGER] == DIRECTION_DOT))   || // ㅃ
				((finger[THUMB_FINGER] == DIRECTION_DOT)   && (finger[INDEX_FINGER] == DIRECTION_DOT)   && (finger[MIDLE_FINGER] == DIRECTION_DOT))   || // ㅆ
				((finger[INDEX_FINGER] == DIRECTION_RIGHT) && (finger[MIDLE_FINGER] == DIRECTION_RIGHT) && (finger[RING__FINGER] == DIRECTION_RIGHT)) || // ㅡ
				((finger[INDEX_FINGER] == DIRECTION_DOWN)  && (finger[MIDLE_FINGER] == DIRECTION_DOWN)  && (finger[RING__FINGER] == DIRECTION_DOWN))  || // ㅣ
				((finger[THUMB_FINGER] == DIRECTION_RIGHT) && (finger[INDEX_FINGER] == DIRECTION_RIGHT) && (finger[RING__FINGER] == DIRECTION_RIGHT)) || // ㅒ
				((finger[THUMB_FINGER] == DIRECTION_LEFT)  && (finger[INDEX_FINGER] == DIRECTION_LEFT)  && (finger[RING__FINGER] == DIRECTION_LEFT))  )) // ㅖ
			return true;
		else if(count_finger == 4 && ( (finger[THUMB_FINGER] == DIRECTION_DOWN) && (finger[INDEX_FINGER] == DIRECTION_DOWN) &&
				(finger[MIDLE_FINGER] == DIRECTION_DOWN) && (finger[RING__FINGER] == DIRECTION_DOWN) )) //ㅉ
			return true;
		else
			return false;
	}


}

