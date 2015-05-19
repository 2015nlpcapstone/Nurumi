package com.fouram.nurumikeyboard.NurumiIME;

import java.text.Normalizer;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_Kor_2 extends IME_Automata {

  public static final boolean ENABLE_DEBUG = true;
  // ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
  private static final int[] PREF_CHO = {12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610,
      12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622};
  // ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
  private static final int[] PREF_JUNG = {12623, 12624, 12625, 12626, 12627, 12628, 12629, 12630,
      12631, 12632, 12633, 12634, 12635, 12636, 12637, 12638, 12639, 12640, 12641, 12642, 12643};
  // ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
  private static final int[] PREF_JONG = {12593, 12594, 12595, 12596, 12597, 12598, 12599, 12601,
      12602, 12603, 12604, 12605, 12606, 12607, 12608, 12609, 12610, 12612, 12613, 12614, 12615,
      12616, 12618, 12619, 12620, 12621, 12622};
  private static final int AC00 = 44032;

  public static final int THUMB_FINGER = 0;
  public static final int INDEX_FINGER = 1;
  public static final int MIDDLE_FINGER = 2;
  public static final int RING_FINGER = 3;
  public static final int PINKY_FINGER = 4;

  public static final int DIRECTION_EMPTY = -1;
  public static final int DIRECTION_DOT = 0;
  public static final int DIRECTION_DOWN = 1;
  public static final int DIRECTION_LEFT = 2;
  public static final int DIRECTION_UP = 3;
  public static final int DIRECTION_RIGHT = 4;

  public static final int LEVEL_CHO_SEONG = 0;
  public static final int LEVEL_JUNG_SEONG = 1;
  public static final int LEVEL_BOK_MO_EUM_JUNG_SEONG = 2;
  public static final int LEVEL_HOUT_JA_EUM_JONG_SEONG = 3;
  public static final int LEVEL_BOK_JA_EUM_JONG_SEONG = 4;


  public static int buffer[] = {'\0', '\0', '\0', '\0'};
  public static int automata_level;

  protected String execute(int[] finger, InputConnection ic) {
    int idx = 5;
    int count_finger = 0;
    String str_to_write = null;

    // yoon // 150412 // count finger
    while (idx-- > 0)
      if (finger[idx] != DIRECTION_EMPTY)
        count_finger++;


    if (ENABLE_DEBUG == true) { // yoon // 150413 // for debug

      Log.d("Automata bgn", "automata level : " + automata_level);
      Log.d("Automata bgn", "finger count : " + count_finger);
      Log.d("Automata bgn", "current buffer : " + buffer[0] + " " + buffer[1] + " " + buffer[2]
          + " " + buffer[3]);
      Log.d("Automata bgn", "motion : " + finger[THUMB_FINGER] + " " + finger[INDEX_FINGER] + " "
          + finger[MIDDLE_FINGER] + " " + finger[RING_FINGER] + " " + finger[PINKY_FINGER]);
    }

    // yoon // 150413 // switch by automata level
    switch (automata_level) { // yoon // step 1. switch by automata level


      case LEVEL_CHO_SEONG:

        switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

          case 1: // yoon // 150413 // case for single finger

            // yoon // 150413 // Conditional Statements for 'ja-eum cho-seong'

            if (finger[INDEX_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 11;// 'ㅇ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[MIDDLE_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 2; // 'ㄴ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 0; // 'ㄱ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            }


            // doo // 150506 // Conditional Statements for 'mo-eum letter'
            else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
              switch (finger[INDEX_FINGER]) {

                case DIRECTION_UP:
                  str_to_write = "ㅗ";
                  break;
                case DIRECTION_RIGHT:
                  str_to_write = "ㅏ";
                  break;
                case DIRECTION_DOWN:
                  str_to_write = "ㅜ";
                  break;
                case DIRECTION_LEFT:
                  str_to_write = "ㅓ";
                  break;
              }
	    }
            else if (finger[MIDDLE_FINGER] != DIRECTION_EMPTY){
              switch (finger[MIDDLE_FINGER]) {

                case DIRECTION_UP:
                  str_to_write = "ㅡ";
                  break;
                case DIRECTION_RIGHT:
                  str_to_write = "ㅐ";
                  break;
                case DIRECTION_DOWN:
                  str_to_write = "ㅣ";
                  break;
                case DIRECTION_LEFT:
                  str_to_write = "ㅔ";
                  break;
              }
	    }
            break; // doo // 150506 // break for single finger

          case 2: // doo // 150506 // case for two fingers
            if (finger[INDEX_FINGER] == DIRECTION_DOT 
                && finger[MIDDLE_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 9;// 'ㅅ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[MIDDLE_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 3;// 'ㄷ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[INDEX_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 7;// 'ㅂ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[THUMB_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 1;// 'ㄲ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            }


            // doo // 150506 // Conditional Statements for 'mo-eum letter'
            else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDDLE_FINGER] != DIRECTION_EMPTY) {
              switch (finger[INDEX_FINGER]) {  // 어차피 같은 방향이니까 ㅇㅅㅇ

                case DIRECTION_UP:
                  str_to_write = "ㅛ";
                  break;
                case DIRECTION_RIGHT:
                  str_to_write = "ㅑ";
                  break;
                case DIRECTION_DOWN:
                  str_to_write = "ㅠ";
                  break;
                case DIRECTION_LEFT:
                  str_to_write = "ㅕ";
                  break;
              }
	    }
            break; // doo // 150506 // break for two fingers


          case 3: // doo // 150506 // case for three fingers
            if (finger[INDEX_FINGER] == DIRECTION_DOT 
                && finger[MIDDLE_FINGER] == DIRECTION_DOT
		&& finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 12;// 'ㅈ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[THUMB_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_DOT
		&& finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 4;// 'ㄸ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[THUMB_FINGER] == DIRECTION_DOT
		&& finger[INDEX_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 8;// 'ㅃ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } else if (finger[THUMB_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_DOT
		&& finger[INDEX_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 10;// 'ㅆ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            } 
            break; // doo // 150506 // break for three fingers


          case 4: // doo // 150506 // case for four fingers
            if (finger[THUMB_FINGER] == DIRECTION_DOT 
		&& finger[INDEX_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_DOT
		&& finger[RING_FINGER] == DIRECTION_DOT) {
              buffer[LEVEL_CHO_SEONG] = 13;// 'ㅉ'
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
              automata_level += 1;
            }
            break; // doo // 150506 // break for four fingers
        }
        break; // doo // 150506 // break for 'LEVEL_CHO_SEONG'



      case LEVEL_JUNG_SEONG:

        switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

          case 1: // doo // 150506 // case for single finger

            // yoon // 150424 // Conditional Statements for 'ja-eum cho-seong'
            if (finger[INDEX_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 11) { // 'ㅇ' to 'ㅁ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 6;// 'ㅁ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 11;// 'ㅇ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }

            else if (finger[MIDDLE_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 2) { // 'ㄴ' to 'ㄹ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 5; // 'ㄹ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 2; // 'ㄴ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }

            else if (finger[RING_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 0) { // 'ㄱ' to 'ㅋ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 15; // 'ㅋ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 0; // 'ㄱ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }

            // doo // 150506 // Conditional Statements for 'mo-eum jung_seong'
            else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
              switch (finger[INDEX_FINGER]) {

                case DIRECTION_UP:
                  buffer[LEVEL_JUNG_SEONG] = 8; // 'ㅗ'
                  break;
                case DIRECTION_RIGHT:
                  buffer[LEVEL_JUNG_SEONG] = 0; // 'ㅏ'
                  break;
                case DIRECTION_DOWN:
                  buffer[LEVEL_JUNG_SEONG] = 13;// 'ㅜ'
                  break;
                case DIRECTION_LEFT:
                  buffer[LEVEL_JUNG_SEONG] = 4; // 'ㅓ'
                  break;
              }

              ic.deleteSurroundingText(1, 0);
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    }

            else if (finger[MIDDLE_FINGER] != DIRECTION_EMPTY) {
              switch (finger[MIDDLE_FINGER]) {

                case DIRECTION_UP:
                 buffer[LEVEL_JUNG_SEONG] = 18; // 'ㅡ'
                  break;
                case DIRECTION_RIGHT:
                  buffer[LEVEL_JUNG_SEONG] = 1; // 'ㅐ'
                  break;
                case DIRECTION_DOWN:
                  buffer[LEVEL_JUNG_SEONG] = 20;// 'ㅣ'
                  break;
                case DIRECTION_LEFT:
                  buffer[LEVEL_JUNG_SEONG] = 5; // 'ㅔ'
                  break;
              }

              ic.deleteSurroundingText(1, 0);
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
            }

            break; // doo // 150506 // break for single finger

          case 2: // yoon // 150413 // case for two fingers

            if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 9) { // 'ㅅ' to 'ㅎ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 18; // 'ㅎ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 9;// 'ㅅ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }

            else if (finger[MIDDLE_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 3) { // 'ㄷ' to 'ㅌ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 16; // 'ㅌ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 3;// 'ㄷ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }
            
            else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 7) { // 'ㅂ' to 'ㅍ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 17; // 'ㅂ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 7; // 'ㅍ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            }

	    // doo // 150508
            else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDDLE_FINGER] != DIRECTION_EMPTY) {
              switch (finger[MIDDLE_FINGER]) {	// 어차피 2개인 경우니까 하나로만 체크해도 ㅇㅋ 

                case DIRECTION_UP:
                 buffer[LEVEL_JUNG_SEONG] = 12; // 'ㅛ'
                  break;
                case DIRECTION_RIGHT:
                  buffer[LEVEL_JUNG_SEONG] = 2; // 'ㅑ'
                  break;
                case DIRECTION_DOWN:
                  buffer[LEVEL_JUNG_SEONG] = 17;// 'ㅠ'
                  break;
                case DIRECTION_LEFT:
                  buffer[LEVEL_JUNG_SEONG] = 6; // 'ㅕ'
                  break;
              }

              ic.deleteSurroundingText(1, 0);
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    }
            else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] != DIRECTION_EMPTY) {
              switch (finger[MIDDLE_FINGER]) {	// 어차피 2개인 경우니까 하나로만 체크해도 ㅇㅋ 

                case DIRECTION_RIGHT:
                 buffer[LEVEL_JUNG_SEONG] = 3; // 'ㅒ'
                  break;
                case DIRECTION_LEFT:
                  buffer[LEVEL_JUNG_SEONG] = 7; // 'ㅖ'
                  break;
              }

              ic.deleteSurroundingText(1, 0);
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    }

            break; // yoon // 150413 // break for two fingers

          case 3: // doo // 150506 // case for three fingers
            if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT) {
              if (buffer[LEVEL_CHO_SEONG] == 12) { // 'ㅈ' to 'ㅊ'
                ic.deleteSurroundingText(1, 0);
                buffer[LEVEL_CHO_SEONG] = 14; // 'ㅊ'
              } else {
                buffer[LEVEL_CHO_SEONG] = 12; // 'ㅈ'
              }
              str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
            } 

            break; // doo // 150508// break for three fingers

        }
        break; // doo // 150506 // break for 'LEVEL_JUNG_SEONG'

      // doo //150508

      case LEVEL_BOK_MO_EUM_JUNG_SEONG:
	switch ( buffer[LEVEL_JUNG_SEONG]) {
	
	  case 8: // 'ㅗ' to 'ㅘ', 'ㅙ' , 'ㅚ'
            if (finger[INDEX_FINGER] == DIRECTION_RIGHT) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 9;
	    } else if( finger[MIDDLE_FINGER] == DIRECTION_RIGHT ) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 10;
	    } else if( finger[MIDDLE_FINGER] == DIRECTION_DOWN ) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 11;
	    }
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    }
	    break;
	 
	  case 13:  // 'ㅜ' to 'ㅝ', 'ㅞ' , 'ㅟ'
            if (finger[INDEX_FINGER] == DIRECTION_LEFT) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 14;
	    } else if( finger[MIDDLE_FINGER] == DIRECTION_LEFT ) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 15;
	    } else if( finger[MIDDLE_FINGER] == DIRECTION_DOWN ) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 16;
	    }
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    
	    break;

	  case 18:  // 'ㅡ' to 'ㅢ'
	    if( finger[MIDDLE_FINGER] == DIRECTION_DOWN ) {
		ic.deleteSurroundingText(1, 0);
		buffer[LEVEL_JUNG_SEONG] = 19;
	    }
              str_to_write =
                  String.format("%c",
                      (AC00 + ((buffer[LEVEL_CHO_SEONG] * 21) + buffer[LEVEL_JUNG_SEONG]) * 28));
              automata_level += 1;
	    
	    break;
	    
      case LEVEL_HOUT_JA_EUM_JONG_SEONG:

        automata_level = 0;
        break;

      case LEVEL_BOK_JA_EUM_JONG_SEONG:

        automata_level = LEVEL_CHO_SEONG;
        break;
    }

    Log.d("Automata end", "------------------------------");
    Log.d("Automata end", "automata level : " + automata_level);
    Log.d("Automata end", "finger count : " + count_finger);
    Log.d("Automata bgn", "current buffer : " + buffer[0] + " " + buffer[1] + " " + buffer[2] + " "
        + buffer[3]);
    return str_to_write;
  }


}
