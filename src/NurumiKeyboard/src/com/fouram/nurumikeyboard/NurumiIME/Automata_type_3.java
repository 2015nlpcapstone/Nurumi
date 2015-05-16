package com.fouram.nurumikeyboard.NurumiIME;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_3 {

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
  public static final int MIDLE_FINGER = 2;
  public static final int RING__FINGER = 3;
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
  
  public static int count_finger = 0;
  public static String str_to_write = null;
  public static int[] finger;
  public static InputConnection ic;
 
  // yoon // 150516 // div&conq.
  
  public static void LEVEL_CHO_SEONG() {
    
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

        // yoon // 150413 // Conditional Statements for 'ja-eum cho-seong'

        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 11;// 'ㅇ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 2; // 'ㄴ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 0; // 'ㄱ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }


        // yoon // 150413 // Conditional Statements for 'mo-eum letter'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY)
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
        break; // yoon // 150413 // break for single finger

      case 2: // yoon // 150424 // case for two fingers
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 9;// 'ㅅ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } 
        else if (finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 3;// 'ㄷ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        } 
        else if (finger[INDEX_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 7;// 'ㅂ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }
        
        // yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
            && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

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
        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for three fingers
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[LEVEL_CHO_SEONG] = 12;// 'ㅈ'
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
          automata_level = LEVEL_JUNG_SEONG;
        }

        break;// yoon // 150507 // break for three fingers
      case 4: // yoon // 150507 // case for four fingers

        if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY
            && finger[RING__FINGER] != DIRECTION_EMPTY&& finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {
            case DIRECTION_RIGHT:
              str_to_write = "ㅡ";
              break;
            case DIRECTION_DOWN:
              str_to_write = "ㅣ";
              break;
              
            default :
              str_to_write="";
              break;
                
          }
          
        }
        break;// yoon // 150507 // break for four fingers

      default :
        str_to_write="";
    }
  };
  public static void LEVEL_JUNG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

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

        else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          if (buffer[LEVEL_CHO_SEONG] == 2) { // 'ㄴ' to 'ㄹ'
            ic.deleteSurroundingText(1, 0);
            buffer[LEVEL_CHO_SEONG] = 5; // 'ㄹ'
          } else {
            buffer[LEVEL_CHO_SEONG] = 2; // 'ㄴ'
          }
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
        }

        else if (finger[RING__FINGER] == DIRECTION_DOT) {
          if (buffer[LEVEL_CHO_SEONG] == 0) { // 'ㄱ' to 'ㅋ'
            ic.deleteSurroundingText(1, 0);
            buffer[LEVEL_CHO_SEONG] = 15; // 'ㅋ'
          } else {
            buffer[LEVEL_CHO_SEONG] = 0; // 'ㄱ'
          }
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
        }

        // yoon // 150413 // Conditional Statements for 'mo-eum jung_seong'
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

        break; // yoon // 150413 // break for single finger

      case 2: // yoon // 150413 // case for two fingers

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          if (buffer[LEVEL_CHO_SEONG] == 9) { // 'ㅅ' to 'ㅎ'
            ic.deleteSurroundingText(1, 0);
            buffer[LEVEL_CHO_SEONG] = 18; // 'ㅎ'
          } else {
            buffer[LEVEL_CHO_SEONG] = 9;// 'ㅅ'
          }
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
        }

        else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          if (buffer[LEVEL_CHO_SEONG] == 3) { // 'ㄷ' to 'ㅌ'
            ic.deleteSurroundingText(1, 0);
            buffer[LEVEL_CHO_SEONG] = 16; // 'ㅎ'
          } else {
            buffer[LEVEL_CHO_SEONG] = 3;// 'ㄷ'
          }
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
        }

        else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          if (buffer[LEVEL_CHO_SEONG] == 7) { // 'ㅂ' to 'ㅍ'
            ic.deleteSurroundingText(1, 0);
            buffer[LEVEL_CHO_SEONG] = 17; // 'ㅂ'
          } else {
            buffer[LEVEL_CHO_SEONG] = 7; // 'ㅍ'
          }
          str_to_write = String.format("%c", PREF_CHO[buffer[LEVEL_CHO_SEONG]]);
        }

        // yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
            && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              buffer[LEVEL_JUNG_SEONG] = 12;// 'ㅛ'
              break;
            case DIRECTION_RIGHT:
              buffer[LEVEL_JUNG_SEONG] = 2; // 'ㅑ'
              break;
            case DIRECTION_DOWN:
              buffer[LEVEL_JUNG_SEONG] = 17; // 'ㅠ'
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

        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for two fingers

        break;

    }

  };
  public static void LEVEL_BOK_MO_EUM_JUNG_SEONG() {
    
  };
  public static void LEVEL_HOUT_JA_EUM_JONG_SEONG() {
    
  };
  public static void LEVEL_BOK_JA_EUM_JONG_SEONG() {
    
  };

  public static String execute(int[] finger_array, InputConnection input_connection) {
    int idx = 5;

    // yoon // 150516 // init values
    finger = finger_array;
    ic = input_connection;
    count_finger = 0;
    
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
          + finger[MIDLE_FINGER] + " " + finger[RING__FINGER] + " " + finger[PINKY_FINGER]);
    }

    // yoon // 150507 // functional keys

    if (count_finger == 1 && finger[THUMB_FINGER] == DIRECTION_RIGHT) {
      automata_level = LEVEL_CHO_SEONG;
      return " ";
    }

    else if (count_finger == 1 && finger[PINKY_FINGER] == DIRECTION_LEFT) {
      automata_level = LEVEL_CHO_SEONG;
      ic.deleteSurroundingText(1, 0);
      return "";
    }

    else if (count_finger == 2 && finger[PINKY_FINGER] == DIRECTION_DOT
        && finger[PINKY_FINGER] == DIRECTION_DOT) {
      automata_level = LEVEL_CHO_SEONG;
      return "\n";
    }

    // yoon // 150413 // switch by automata level
    
    switch (automata_level) { // yoon // step 1. switch by automata level

      case LEVEL_CHO_SEONG:
        LEVEL_CHO_SEONG();
        break;
        
      case LEVEL_JUNG_SEONG:
        LEVEL_JUNG_SEONG();
        break; // yoon // 150413 // break for 'LEVEL_JUNG_SEONG'

      case LEVEL_BOK_MO_EUM_JUNG_SEONG:

        automata_level = 0;
        break;

      case LEVEL_HOUT_JA_EUM_JONG_SEONG:

        automata_level = 0;
        break;

      case LEVEL_BOK_JA_EUM_JONG_SEONG:

        automata_level = LEVEL_CHO_SEONG;
        break;
    }
    
    return str_to_write;
  }


}
