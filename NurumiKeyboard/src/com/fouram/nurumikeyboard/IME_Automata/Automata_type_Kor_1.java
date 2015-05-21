package com.fouram.nurumikeyboard.IME_Automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_Kor_1 extends IME_Automata {

	private final int area_a=12685;
	private final int ssangarea_a=4514;

	private final int LEVEL_CHO_SEONG = 0;
	private final int LEVEL_JUNG_SEONG = 1;
	private final int LEVEL_JONG_SEONG = 2;
	private final int LEVEL_BOK_JA_EUM_JONG_SEONG = 3;
	private final int LEVEL_JONG_SEONG_TO_CHO_SEONG = 4;

	private final int CHO_SEONG = 0;
	private final int JUNG_SEONG = 1;
	private final int JONG_SEONG = 2;

	private int buffer[] = {'\0', '\0', '\0', '\0', '\0'};
	private int Moeumbuffer[] = {'\0', '\0', '\0', '\0', '\0'};

	private int lastbuffer=-1;  //save cho_seong and use when moeum is area_a
	private int firstjaeum_bokjaeum=-1;    //save firstjaeum and use to make last buffer when LEVEL_JONG_SEONG_TO_CHO_SEONG
	private int nextjaeum=-1;    //save last jaeum and use to make next buffer  when LEVEL_JONG_SEONG_TO_CHO_SEONG
	private boolean checkmoeumcomplete=true;

	private int Moeumautomata_level=0;
	private int automata_level=0;

	//kim // 150516 // get a Korean character code key value

	private int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
		return ((AC00 + ((cho_seong * 21) + jung_seong) * 28)+jong_seong);
	}
	private int choseong_moeum(int moeum) {  //get a Korean characher code when only use Moeum
		return (moeum + 12623 );
	}
	private void variable_initial(){
		firstjaeum_bokjaeum=-1;
		lastbuffer=-1;
		nextjaeum=-1;
		checkmoeumcomplete=true;
	}
	private void buffer_clear()
	{
		int buffercount=4;
		while(buffercount>-1)
		{
			buffer[buffercount]='\0';
			buffercount--;
		}

	}
	private void Moeumbuffer_clear()
	{
		int buffercount=4;
		while(buffercount>-1)
		{
			Moeumbuffer[buffercount]='\0';
			buffercount--;
		}

	}

	// kim // 150516 // div&conq.
	private void CHO_SEONG_JAEUM(int jaeum){ //use when choseong is jaeum
		buffer[LEVEL_CHO_SEONG] = jaeum;
		lastbuffer=buffer[LEVEL_CHO_SEONG];
		str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
		automata_level += 1;
	}
	private void CHO_SEONG_MOEUM(int moeum){ //use when choseong is moeum
		Moeumbuffer[Moeumautomata_level] = moeum;
		Moeumautomata_level += 1;
		buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
		ic.deleteSurroundingText(1, 0);
		checkmoeumcomplete=true;
		str_to_write = String.format("%c", choseong_moeum(Moeumbuffer[Moeumautomata_level-1]));
	}
	private void JONG_SEONG_JAEUM(int jaeum){    //use when jongseong is jaeum
		buffer[LEVEL_JONG_SEONG] = jaeum;
		buffer[LEVEL_BOK_JA_EUM_JONG_SEONG] = jaeum;
		ic.deleteSurroundingText(1, 0);
		str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],buffer[JONG_SEONG]));
		automata_level += 1;
	}
	private void JUNG_SEONG_MOEUM(int moeum){    //use when jungseong is moeum
		Moeumbuffer[Moeumautomata_level] = moeum;
		Moeumautomata_level += 1;
		buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
		ic.deleteSurroundingText(1, 0);
		checkmoeumcomplete=true;
		str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
	}
	private void BOK_JAEUM(){ //use when bokjaeum
		ic.deleteSurroundingText(1, 0);
		str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], buffer[JONG_SEONG]));
		automata_level += 1;
	}



	private void LEVEL_CHO_SEONG() {
		buffer_clear();
		Moeumbuffer_clear();
		variable_initial();
		Moeumautomata_level=0;
		automata_level=0;

		switch (count_finger) { // kim // step 2. switch by finger counts for accuracy

		case 1:
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(0);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(2);
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(3);
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(5);
			} else if (finger[INDEX_FINGER] == DIRECTION_UP) {
				CHO_SEONG_JAEUM(15);
			} else if (finger[RING__FINGER] == DIRECTION_UP) {
				CHO_SEONG_JAEUM(16);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN) {
				CHO_SEONG_JAEUM(1);
			} else if (finger[RING__FINGER] == DIRECTION_DOWN) {
				CHO_SEONG_JAEUM(4);
			} else {
				str_to_write="";
			}
			break; // kim // 150413 // break for single finger

		case 2: // kim // 150413 // case for two fingers

			if (finger[MIDLE_FINGER] == DIRECTION_DOT&&finger[RING__FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(7);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT) {
				CHO_SEONG_JAEUM(6);
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP&&finger[RING__FINGER] == DIRECTION_UP) {
				CHO_SEONG_JAEUM(17);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOWN&&finger[RING__FINGER] == DIRECTION_DOWN) {
				CHO_SEONG_JAEUM(8);
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT) {
				str_to_write = String.format("%c", area_a);//'.'
				Moeumbuffer[Moeumautomata_level]=area_a;
				Moeumautomata_level += 1;
				automata_level += 1;
			}else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				str_to_write = String.format("%c", 12641);//'คั'
				Moeumbuffer[Moeumautomata_level]=18;
				Moeumautomata_level += 1;
				automata_level += 1;
			}else {
				str_to_write="";
			}
			break; // kim // 150413 // break for two fingers

		case 3:// kim // 150413 // case for three fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT) {
					CHO_SEONG_JAEUM(9);
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT&&finger[PINKY_FINGER] == DIRECTION_DOT) {
					CHO_SEONG_JAEUM(11);
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP
				&&finger[RING__FINGER] == DIRECTION_UP&&finger[PINKY_FINGER] == DIRECTION_UP) {
					CHO_SEONG_JAEUM(18);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN
				&&finger[MIDLE_FINGER] == DIRECTION_DOWN&&finger[RING__FINGER] == DIRECTION_DOWN) {
					CHO_SEONG_JAEUM(10);
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
				&& finger[MIDLE_FINGER] == DIRECTION_DOT) {
					str_to_write = String.format("%c", 12643);//'คำ'
					Moeumbuffer[Moeumautomata_level]=20;
					Moeumautomata_level += 1;
					automata_level += 1;
			} else {
				str_to_write="";

			}
			break;// kim // 150519 // break for three fingers

		case 4:// kim // 150413 // case for four fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT&&finger[PINKY_FINGER] == DIRECTION_DOT) {
					CHO_SEONG_JAEUM(12);
			} else if (finger[INDEX_FINGER] == DIRECTION_UP&&finger[MIDLE_FINGER] == DIRECTION_UP
				&&finger[RING__FINGER] == DIRECTION_UP&&finger[PINKY_FINGER] == DIRECTION_UP) {
					CHO_SEONG_JAEUM(14);
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN&&finger[MIDLE_FINGER] == DIRECTION_DOWN
				&&finger[RING__FINGER] == DIRECTION_DOWN&&finger[PINKY_FINGER] == DIRECTION_DOWN) {
					CHO_SEONG_JAEUM(13);
			} else {
				str_to_write="";
			}
			break;// kim // 150519 // break for four fingers
		default :
			str_to_write="";

		}
	};
	private void LEVEL_JUNG_SEONG() {
		switch (count_finger) { // kim // step 2. switch by finger counts for accuracy

		case 2: // kim // 150413 // case for single finger

			// kim // 150413 // Conditional Statements for 'mo-eum jung_seong'
			if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT) {
				switch (Moeumautomata_level) {
				case 0:
					Moeumbuffer[Moeumautomata_level] = area_a;
					Moeumautomata_level += 1;
					buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level-1];
					str_to_write = String.format("%c", (area_a));
					checkmoeumcomplete=false;
					break;
				case 1:
					switch(Moeumbuffer[Moeumautomata_level-1]){

					case area_a: {
						Moeumbuffer[Moeumautomata_level] = ssangarea_a;
						Moeumautomata_level += 1;
						buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
						str_to_write = String.format("%c", (area_a));
						checkmoeumcomplete=false;
								 }
								 break;
					case 18:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(13);
						}
						else{
							CHO_SEONG_MOEUM(13);
						}
						break;
					case 20:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(0);
						}
						else{
							CHO_SEONG_MOEUM(0);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				case 2:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case ssangarea_a:{
						lastbuffer=-1;
						Moeumbuffer[Moeumautomata_level] = ssangarea_a;
						Moeumautomata_level = 2;
						buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level];
						str_to_write = String.format("%c", (area_a));
						checkmoeumcomplete=false;
									 }
									 break;
					case 13:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(17);
						}
						else{
							CHO_SEONG_MOEUM(17);
						}
						break;
					case 0:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(2);
						}
						else{
							CHO_SEONG_MOEUM(2);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				case 3:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case 11:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(9);
						}
						else {
							CHO_SEONG_MOEUM(9);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				default:
					LEVEL_CHO_SEONG();
					break;

				}
				//buffer[LEVEL_JUNG_SEONG] = area_a;// '.'
				//str_to_write = String.format("%c", PREF_JUNG[buffer[LEVEL_JUNG_SEONG]]);
				//automata_level += 1;
			} else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
				switch (Moeumautomata_level) {
				case 0:

					Moeumbuffer[Moeumautomata_level] = 18;
					Moeumautomata_level += 1;
					buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level-1];
					ic.deleteSurroundingText(1, 0);
					str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],0));
					break;
				case 1:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case area_a:
						if(lastbuffer!=-1) {
							Moeumbuffer[Moeumautomata_level] = 8;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
						}else{
							CHO_SEONG_MOEUM(8);
						}

						break;
					default:
						LEVEL_CHO_SEONG();
						break;
					}
					break;
				case 2:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case ssangarea_a:
						if(lastbuffer!=-1) {
							Moeumbuffer[Moeumautomata_level] = 12;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
						}
						else {
							Moeumbuffer[Moeumautomata_level] = 12;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", choseong_moeum(Moeumbuffer[Moeumautomata_level-1]));
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				default:
					LEVEL_CHO_SEONG();
					break;
				}

			}else if(Moeumautomata_level!=0&&lastbuffer!=-1){
				automata_level += 1;
				Moeumautomata_level=0;
				LEVEL_JONG_SEONG();
				break;
			}
			else
				LEVEL_CHO_SEONG();
			break; // kim // 150413 // break for two finger

		case 3: // kim // 150413 // case for three fingers
			if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT) {
				switch (Moeumautomata_level) {
				case 0:

					Moeumbuffer[Moeumautomata_level] = 20;
					Moeumautomata_level += 1;
					buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level-1];
					ic.deleteSurroundingText(1, 0);
					checkmoeumcomplete=true;
					str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG],buffer[JUNG_SEONG],0));
					break;
				case 1:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case area_a:
						if(lastbuffer!=-1) {
							Moeumbuffer[Moeumautomata_level] = 4;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
						}
						else {
							CHO_SEONG_MOEUM(4);
						}
						break;

					case 18:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(19);
						}
						else {
							CHO_SEONG_MOEUM(19);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;
					}
					break;
				case 2:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case ssangarea_a:
						if(lastbuffer!=-1) {
							Moeumbuffer[Moeumautomata_level] = 6;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0));
						}
						else {
							Moeumbuffer[Moeumautomata_level] = 6;
							Moeumautomata_level += 1;
							buffer[LEVEL_JUNG_SEONG] = Moeumbuffer[Moeumautomata_level - 1];
							ic.deleteSurroundingText(1, 0);
							ic.deleteSurroundingText(1, 0);
							checkmoeumcomplete=true;
							str_to_write = String.format("%c", choseong_moeum(Moeumbuffer[Moeumautomata_level - 1]));
						}
						break;
					case 8:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(11);
						}
						else{
							CHO_SEONG_MOEUM(11);
						}
						break;
					case 4:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(5);
						}
						else{
							CHO_SEONG_MOEUM(5);
						}
						break;
					case 13:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(16);
						}
						else{
							CHO_SEONG_MOEUM(16);
						}
						break;
					case 0:
						if(lastbuffer!=-1) {
							JUNG_SEONG_MOEUM(1);
						}
						else{
							CHO_SEONG_MOEUM(1);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				case 3:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case 6:
						if(lastbuffer != -1) {
							JUNG_SEONG_MOEUM(7);
						}
						else{
							CHO_SEONG_MOEUM(7);
						}
						break;
					case 17:
						if(lastbuffer != -1) {
							JUNG_SEONG_MOEUM(14);
						}
						else{
							CHO_SEONG_MOEUM(14);
						}
						break;
					case 2:
						if(lastbuffer != -1) {
							JUNG_SEONG_MOEUM(3);
						}
						else{
							CHO_SEONG_MOEUM(3);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				case 4:
					switch(Moeumbuffer[Moeumautomata_level-1]){
					case 9:
						if(lastbuffer != -1) {
							JUNG_SEONG_MOEUM(10);
						}
						else{
							CHO_SEONG_MOEUM(10);
						}
						break;
					case 14:
						if(lastbuffer != -1) {
							JUNG_SEONG_MOEUM(15);
						}
						else{
							CHO_SEONG_MOEUM(15);
						}
						break;
					default:
						LEVEL_CHO_SEONG();
						break;

					}
					break;
				default:
					LEVEL_CHO_SEONG();
					break;

				}

			}
			else if(Moeumautomata_level != 0 && lastbuffer != -1) {
				automata_level += 1;
				Moeumautomata_level=0;
				LEVEL_JONG_SEONG();
				break;
			}
			else
				LEVEL_CHO_SEONG();
			break;
		default:
			if(Moeumautomata_level != 0 && lastbuffer != -1) {
				automata_level += 1;
				Moeumautomata_level=0;
				LEVEL_JONG_SEONG();
				break;
			}
			else
				LEVEL_CHO_SEONG();
			break;

		}

	};

	private void LEVEL_JONG_SEONG() {
		if(checkmoeumcomplete != true)
		{
			LEVEL_CHO_SEONG();
			return;
		}
		switch (count_finger) { // kim // step 2. switch by finger counts for accuracy

		case 1:
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(1);
				firstjaeum_bokjaeum=0;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(4);
				firstjaeum_bokjaeum=2;
			} else if (finger[RING__FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(7);
				firstjaeum_bokjaeum=3;
			} else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(8);
				firstjaeum_bokjaeum=5;
			} else if (finger[INDEX_FINGER] == DIRECTION_UP) {
				JONG_SEONG_JAEUM(24);
				firstjaeum_bokjaeum=15;
			} else if (finger[RING__FINGER] == DIRECTION_UP) {
				JONG_SEONG_JAEUM(25);
				firstjaeum_bokjaeum=16;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN) {
				JONG_SEONG_JAEUM(2);
				firstjaeum_bokjaeum=1;
			}else {
				LEVEL_CHO_SEONG();
			}
			break; // kim // 150413 // break for single finger

		case 2: // kim // 150413 // case for two fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(16);
				firstjaeum_bokjaeum=6;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT&&finger[RING__FINGER] == DIRECTION_DOT) {
				JONG_SEONG_JAEUM(17);
				firstjaeum_bokjaeum=7;
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP&&finger[RING__FINGER] == DIRECTION_UP) {
				JONG_SEONG_JAEUM(26);
				firstjaeum_bokjaeum=17;
			} else {
				LEVEL_CHO_SEONG();
			}
			break; // kim // 150413 // break for two fingers

		case 3:// kim // 150413 // case for three fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT) {
					JONG_SEONG_JAEUM(19);
					firstjaeum_bokjaeum=9;
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT&&finger[PINKY_FINGER] == DIRECTION_DOT) {
					JONG_SEONG_JAEUM(21);
					firstjaeum_bokjaeum=11;
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP
				&&finger[RING__FINGER] == DIRECTION_UP&&finger[PINKY_FINGER] == DIRECTION_UP) {
					JONG_SEONG_JAEUM(27);
					firstjaeum_bokjaeum=18;
			} else if (finger[INDEX_FINGER] == DIRECTION_DOWN
				&&finger[MIDLE_FINGER] == DIRECTION_DOWN&&finger[RING__FINGER] == DIRECTION_DOWN) {
					JONG_SEONG_JAEUM(20);
					firstjaeum_bokjaeum=10;
			}else {
				LEVEL_CHO_SEONG();
			}
			break;

		case 4:// kim // 150413 // case for four fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT&&finger[PINKY_FINGER] == DIRECTION_DOT) {
					JONG_SEONG_JAEUM(22);
					firstjaeum_bokjaeum=12;
			} else if (finger[INDEX_FINGER] == DIRECTION_UP&&finger[MIDLE_FINGER] == DIRECTION_UP
				&&finger[RING__FINGER] == DIRECTION_UP&&finger[PINKY_FINGER] == DIRECTION_UP) {
					JONG_SEONG_JAEUM(23);
					firstjaeum_bokjaeum=14;
			}else {
				LEVEL_CHO_SEONG();
			}
			break;
		default :
			str_to_write="";
		}
	};
	private void LEVEL_BOK_JA_EUM_JONG_SEONG() {

		switch (count_finger) { // kim // step 2. switch by finger counts for accuracy

		case 1:
			if (finger[INDEX_FINGER] == DIRECTION_DOT) {
				if(buffer[LEVEL_JONG_SEONG]==8) {
					buffer[LEVEL_JONG_SEONG] = 9;
					nextjaeum=0;
					BOK_JAEUM();
				}
				else
					LEVEL_CHO_SEONG();
			}  else if (finger[RING__FINGER] == DIRECTION_UP) {
				if(buffer[LEVEL_JONG_SEONG]==8) {
					buffer[LEVEL_JONG_SEONG] = 13;
					nextjaeum=16;
					BOK_JAEUM();
				}
				else
					LEVEL_CHO_SEONG();
			}
			else
				LEVEL_CHO_SEONG();
			break; // kim // 150413 // break for single finger

		case 2: // kim // 150413 // case for two fingers

			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT) {
				if(buffer[LEVEL_JONG_SEONG]==8) {
					buffer[LEVEL_JONG_SEONG] = 10;
					nextjaeum=6;
					BOK_JAEUM();
				}
				else
					LEVEL_CHO_SEONG();
			} else if (finger[MIDLE_FINGER] == DIRECTION_DOT&&finger[RING__FINGER] == DIRECTION_DOT) {
				if(buffer[LEVEL_JONG_SEONG]==8) {
					buffer[LEVEL_JONG_SEONG] = 11;
					nextjaeum=7;
					BOK_JAEUM();
				}
				else
					LEVEL_CHO_SEONG();
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP&&finger[RING__FINGER] == DIRECTION_UP) {
				if(buffer[LEVEL_JONG_SEONG]==8) {
					buffer[LEVEL_JONG_SEONG] = 14;
					nextjaeum=17;
					BOK_JAEUM();
				}
				else
					LEVEL_CHO_SEONG();
			}else if (finger[THUMB_FINGER] == DIRECTION_DOT&&finger[INDEX_FINGER] == DIRECTION_DOT){
				ic.deleteSurroundingText(1,0);
				str_to_write = String.format("%c%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],0),PREF_CHO[firstjaeum_bokjaeum],area_a);
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG]=firstjaeum_bokjaeum;
				Moeumbuffer[CHO_SEONG]=area_a;
				buffer[LEVEL_JUNG_SEONG]=area_a;
				lastbuffer=buffer[LEVEL_CHO_SEONG];
				checkmoeumcomplete=false;
				Moeumautomata_level=1;
				automata_level=1;
			}else if(finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT){
				ic.deleteSurroundingText(1,0);
				str_to_write = String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],0),generate_korean_char_code(firstjaeum_bokjaeum,18,0));
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG]=firstjaeum_bokjaeum;
				Moeumbuffer[CHO_SEONG]=18;
				buffer[LEVEL_JUNG_SEONG]=18;
				lastbuffer=buffer[LEVEL_CHO_SEONG];
				Moeumautomata_level=1;
				automata_level=1;
			}
			else
				LEVEL_CHO_SEONG();
			break; // kim // 150413 // break for two fingers

		case 3:// kim // 150413 // case for three fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT) {
					if(buffer[LEVEL_JONG_SEONG]==1) {
						buffer[LEVEL_JONG_SEONG] = 3;
						nextjaeum=9;
						BOK_JAEUM();
					}
					else if(buffer[LEVEL_JONG_SEONG]==8) {
						buffer[LEVEL_JONG_SEONG] = 12;
						nextjaeum=9;
						BOK_JAEUM();
					}
					else if(buffer[LEVEL_JONG_SEONG]==17) {
						buffer[LEVEL_JONG_SEONG] = 18;
						nextjaeum=9;
						BOK_JAEUM();
					}
					else
						LEVEL_CHO_SEONG();
			} else if (finger[MIDLE_FINGER] == DIRECTION_UP
				&&finger[RING__FINGER] == DIRECTION_UP&&finger[PINKY_FINGER] == DIRECTION_UP) {
					if(buffer[LEVEL_JONG_SEONG]==4) {
						buffer[LEVEL_JONG_SEONG] = 6;
						nextjaeum=18;
						BOK_JAEUM();
					}
					else if(buffer[LEVEL_JONG_SEONG]==8) {
						buffer[LEVEL_JONG_SEONG] = 15;
						nextjaeum=18;
						BOK_JAEUM();
					}
					else
						LEVEL_CHO_SEONG();
			} else if(finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT&& finger[MIDLE_FINGER] == DIRECTION_DOT){
				ic.deleteSurroundingText(1,0);
				str_to_write = String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],0),generate_korean_char_code(firstjaeum_bokjaeum,20,0));
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG]=firstjaeum_bokjaeum;
				Moeumbuffer[CHO_SEONG]=20;
				buffer[LEVEL_JUNG_SEONG]=20;
				lastbuffer=buffer[LEVEL_CHO_SEONG];
				Moeumautomata_level=1;
				automata_level=1;
			}
			else
				LEVEL_CHO_SEONG();
			break;

		case 4:// kim // 150413 // case for four fingers


			if (finger[INDEX_FINGER] == DIRECTION_DOT&&finger[MIDLE_FINGER] == DIRECTION_DOT
				&&finger[RING__FINGER] == DIRECTION_DOT&&finger[PINKY_FINGER] == DIRECTION_DOT) {
					if(buffer[LEVEL_JONG_SEONG]==4) {
						buffer[LEVEL_JONG_SEONG] = 5;
						nextjaeum=12;
						BOK_JAEUM();
					}
					else
						LEVEL_CHO_SEONG();
			}
			else
				LEVEL_CHO_SEONG();
			break;
		default :
			str_to_write="";
		}

	};
	private void LEVEL_JONG_SEONG_TO_CHO_SEONG(){
		switch (count_finger){

		case 2:
			if (finger[THUMB_FINGER] == DIRECTION_DOT&&finger[INDEX_FINGER] == DIRECTION_DOT) {
				ic.deleteSurroundingText(1, 0);
				str_to_write = String.format("%c%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], buffer[LEVEL_BOK_JA_EUM_JONG_SEONG]), PREF_CHO[nextjaeum], area_a);
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG] = nextjaeum;
				Moeumbuffer[CHO_SEONG] = area_a;
				buffer[LEVEL_JUNG_SEONG] = area_a;
				lastbuffer = buffer[LEVEL_CHO_SEONG];
				checkmoeumcomplete=false;
				Moeumautomata_level = 1;
				automata_level = 1;
			}
			else if(finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT){
				ic.deleteSurroundingText(1,0);
				str_to_write = String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],buffer[LEVEL_BOK_JA_EUM_JONG_SEONG]),generate_korean_char_code(nextjaeum,18,0));
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG]=nextjaeum;
				Moeumbuffer[CHO_SEONG]=18;
				buffer[LEVEL_JUNG_SEONG]=18;
				lastbuffer=buffer[LEVEL_CHO_SEONG];
				Moeumautomata_level=1;
				automata_level=1;
			}
			else
				LEVEL_CHO_SEONG();
			break;
		case 3:
			if(finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT&& finger[MIDLE_FINGER] == DIRECTION_DOT){
				ic.deleteSurroundingText(1,0);
				str_to_write = String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],buffer[LEVEL_BOK_JA_EUM_JONG_SEONG]),generate_korean_char_code(nextjaeum,20,0));
				buffer_clear();
				Moeumbuffer_clear();
				buffer[LEVEL_CHO_SEONG]=nextjaeum;
				Moeumbuffer[CHO_SEONG]=20;
				buffer[LEVEL_JUNG_SEONG]=20;
				lastbuffer=buffer[LEVEL_CHO_SEONG];
				Moeumautomata_level=1;
				automata_level=1;
			}
			else
				LEVEL_CHO_SEONG();
			break;
		default:
			LEVEL_CHO_SEONG();
			break;
		}

	};
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

		if (ENABLE_DEBUG) { // kim // 150413 // for debug

			Log.d("Automata bgn", "automata level : " + automata_level);
			Log.d("Automata bgn", "finger count : " + count_finger);
			Log.d("Automata bgn", "current buffer : " + buffer[CHO_SEONG] + " " + buffer[JUNG_SEONG] + " " + buffer[JONG_SEONG]
			+ " " + buffer[3]);
			Log.d("Automata bgn", "motion : " + finger[THUMB_FINGER] + " " + finger[INDEX_FINGER] + " "
				+ finger[MIDLE_FINGER] + " " + finger[RING__FINGER] + " " + finger[PINKY_FINGER]);
			Log.d("Automata bgn", "moeum level : " + Moeumautomata_level);
			Log.d("Automata bgn", "moeum level : " + Moeumbuffer[CHO_SEONG] + " " + Moeumbuffer[JUNG_SEONG] + " " + Moeumbuffer[JONG_SEONG]);
		}

		// kim // 150507 // functional keys

		if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_RIGHT) {
			LEVEL_CHO_SEONG();
			return " ";
		}

		else if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_LEFT) {
			ic.deleteSurroundingText(1, 0);
			LEVEL_CHO_SEONG();
			return "";
		}

		else if (count_finger == 2 && finger[THUMB_FINGER] == DIRECTION_DOT
			&& finger[PINKY_FINGER] == DIRECTION_DOT) {
				LEVEL_CHO_SEONG();
				return "\n";
		}

		// kim // 150413 // switch by automata level

		switch (automata_level) { // kim // step 1. switch by automata level

		case LEVEL_CHO_SEONG:
			LEVEL_CHO_SEONG();
			break;

		case LEVEL_JUNG_SEONG:
			LEVEL_JUNG_SEONG();
			break;

		case LEVEL_JONG_SEONG:
			LEVEL_JONG_SEONG();
			break;

		case LEVEL_BOK_JA_EUM_JONG_SEONG:
			LEVEL_BOK_JA_EUM_JONG_SEONG();
			break;

		case LEVEL_JONG_SEONG_TO_CHO_SEONG:
			LEVEL_JONG_SEONG_TO_CHO_SEONG();
			break;
		}

		return str_to_write;
	}


}
