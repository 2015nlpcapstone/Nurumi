package com.fouram.nurumikeyboard.NurumiIME;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_Kor_3 extends IME_Automata {

  public static final boolean ENABLE_DEBUG = true;
  // 0.ぁ 1.あ 2.い 3.ぇ 4.え 5.ぉ 6.け 7.げ 8.こ 9.さ 10.ざ 11.し 12.じ 13.す 14.ず 15.せ 16.ぜ 17.そ 18ぞ
  private static final int[] PREF_CHO = {12593, 12594, 12596, 12599, 12600, 12601, 12609, 12610,
      12611, 12613, 12614, 12615, 12616, 12617, 12618, 12619, 12620, 12621, 12622};
  // 0.た 1.だ 2.ち 3.ぢ 4.っ 5.つ 6.づ 7.て 8.で 9.と 10.ど 11.な 12.に 13.ぬ 14.ね 15.の 16.は 17.ば 18.ぱ 19.ひ 20.び
  private static final int[] PREF_JUNG = {12623, 12624, 12625, 12626, 12627, 12628, 12629, 12630,
      12631, 12632, 12633, 12634, 12635, 12636, 12637, 12638, 12639, 12640, 12641, 12642, 12643};
  // 0.ぁ 1.あ 2.ぃ 3.い 4.ぅ 5.う 6.ぇ 7.ぉ 8.お 9.か 10.が 11.き 12.ぎ 13.く 14.ぐ 15.け 16.げ 17.ご
  // 18.さ 19.ざ 20.し 21.じ 22.ず 23.せ 24.ぜ 25.そ 26.ぞ
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
  public static final int LEVEL_JUNG_SEONG_TO_JONG_SEONG = 2;
  public static final int LEVEL_JONG_SEONG = 3;
  public static final int LEVEL_JONG_SEONG_TO_CHO_SEONG = 4;

  public static final int CHO_SEONG = 0;
  public static final int JUNG_SEONG = 1;
  public static final int JONG_SEONG = 2;
  public static final int WISP_FLARE = 3;

  public static int buffer[] = {'\0', '\0', '\0', '\0'};
  public static int automata_level = 0;
  public static int bok_ja_eum_jong_seong = 0;

  public static int count_finger = 0;
  public static String text_to_commit = null;
  public static Boolean ready_to_commit_text;
  public static int[] finger;
  public static InputConnection ic;

  // yoon // 150516 // get a Korean character code key value
  public static int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
    return ((AC00 + ((cho_seong * 21) + jung_seong) * 28) + jong_seong);
  }

  // yoon // 150517 // replace character macro
  public static int replace_to(int ret) {
    ic.deleteSurroundingText(1, 0);
    return ret;
  }  
  
  // yoon // 150518 // assign text to commit &  & validate for committing
  public static void text_to_commit(String str) {
    text_to_commit = str;
    ready_to_commit_text = true;
  }

  // yoon // 150517 // clean character buffer
  public static void buffer_clean() {
    int i = 4;
    while (i-- > 0)
      buffer[i] = 0;
    bok_ja_eum_jong_seong = 0;
  }

  // yoon // 150517 // log print for debugging
  public static void print_log(String str) {
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

  public static void LEVEL_CHO_SEONG() {


    // yoon // 150517 // buffer clean
    buffer_clean();

    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

        // yoon // 150413 // Conditional Statements for 'ja-eum cho-seong'
        // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 11;// 'し'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 6; // 'け'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 2; // 'い'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 5; // 'ぉ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }


        // yoon // 150413 // Conditional Statements for 'mo-eum letter'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY)
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              text_to_commit( "で" );
              break;
            case DIRECTION_RIGHT:
              text_to_commit( "た" );
              break;
            case DIRECTION_DOWN:
              text_to_commit( "ぬ" );
              break;
            case DIRECTION_LEFT:
              text_to_commit( "っ" );
              break;
          }
        break; // yoon // 150413 // break for single finger

      case 2: // yoon // 150424 // case for two fingers
              // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 0;// 'ぁ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 3;// 'ぇ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 7;// 'げ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              text_to_commit( "に" );
              break;
            case DIRECTION_RIGHT:
              text_to_commit( "ち" );
              break;
            case DIRECTION_DOWN:
              text_to_commit( "ば" );
              break;
            case DIRECTION_LEFT:
              text_to_commit( "づ" );
              break;

          }
        }
        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for three fingers
              // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 12; // 'じ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
            && finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 9; // 'さ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }

        break;// yoon // 150507 // break for three fingers

      case 4: // yoon // 150507 // case for four fingers

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          buffer[CHO_SEONG] = 18;// 'ぞ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }

        else {

          switch (finger[INDEX_FINGER]) {
            case DIRECTION_RIGHT:
              text_to_commit( "ぱ" );
              break;
            case DIRECTION_DOWN:
              text_to_commit( "び" );
              break;
            default:
              text_to_commit( "" );
              break;
          }
        }
        break;// yoon // 150507 // break for four fingers

      default:
        text_to_commit( "" );
    }
  };

  public static void LEVEL_JUNG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger
              // 150517 // mod ipt method
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 11;// 'し'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 6; // 'け'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 2; // 'い'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 5; // 'ぉ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150413 // Conditional Statements for 'hout-mo-eum jung_seong'

        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              buffer[JUNG_SEONG] = 8; // 'で'
              break;
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 0; // 'た'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 13;// 'ぬ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 4; // 'っ'
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
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 0 ? replace_to(15) : 0);// 'ぁ' or 'せ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 3 ? replace_to(16) : 3);// 'ぇ' or 'ぜ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 7 ? replace_to(17) : 7);// 'げ' or 'そ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }

        // yoon // 150507 // Conditional Statements for 'hout-mo-eum jung_seong'

        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_UP:
              buffer[JUNG_SEONG] = 12;// 'に'
              break;
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 2; // 'ち'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 17; // 'ば'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 6; // 'づ'
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
              buffer[JUNG_SEONG] = 1; // 'だ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 5; // 'つ'
              break;
          }

          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        }

        break; // yoon // 150413 // break for two fingers

      case 3: // yoon // 150507 // case for three fingers
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 12 ? replace_to(14) : 12); // 'じ' or 'ず'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
            && finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[CHO_SEONG] = 9; // 'さ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        }


        // yoon // 150517 // for bok-mo-eum jung-seong
        else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY
            && finger[RING__FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {

            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 3; // 'ぢ'
              break;
            case DIRECTION_LEFT:
              buffer[JUNG_SEONG] = 7; // 'て'
              break;
          }
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
          automata_level = JONG_SEONG;
        }
        break;

      case 4: // yoon // 150516 // case for four fingers

        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
            && finger[RING__FINGER] != DIRECTION_EMPTY && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          buffer[CHO_SEONG] = 18;// 'ぞ'
          text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
          automata_level = LEVEL_JUNG_SEONG;
        } else if (finger[INDEX_FINGER] != DIRECTION_EMPTY
            && finger[MIDLE_FINGER] != DIRECTION_EMPTY && finger[RING__FINGER] != DIRECTION_EMPTY
            && finger[PINKY_FINGER] != DIRECTION_EMPTY) {
          switch (finger[INDEX_FINGER]) {
            case DIRECTION_RIGHT:
              buffer[JUNG_SEONG] = 18; // 'ぱ'
              break;
            case DIRECTION_DOWN:
              buffer[JUNG_SEONG] = 20; // 'び'
              break;
          }
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
          automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
        }
    }
  };

  public static void LEVEL_JUNG_SEONG_TO_JONG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150517 // case for single finger

        if (buffer[JUNG_SEONG] == 8 && finger[INDEX_FINGER] == DIRECTION_RIGHT) { // 'で' + 'た'
          buffer[JUNG_SEONG] = 9; // 'と'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
          automata_level = JONG_SEONG;
        } else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT) { // 'ぬ' +
                                                                                         // 'っ'
          buffer[JUNG_SEONG] = 14; // 'ね'
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
            && finger[RING__FINGER] != DIRECTION_EMPTY) { // 'で' + 'だ'
          buffer[JUNG_SEONG] = 10; // 'な'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format("%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
          automata_level = JONG_SEONG;
        } else if (buffer[JUNG_SEONG] == 13 && finger[INDEX_FINGER] == DIRECTION_LEFT
            && finger[RING__FINGER] != DIRECTION_EMPTY) { // 'ぬ' + 'つ'
          buffer[JUNG_SEONG] = 15; // 'の'
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
          if (buffer[JUNG_SEONG] == 8) { // 'で'
            buffer[JUNG_SEONG] = 11; // 'な'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 13) { // 'ぬ'
            buffer[JUNG_SEONG] = 16; // 'は'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 0) { // 'た'
            buffer[JUNG_SEONG] = 1; // 'だ'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 2) { // 'ち'
            buffer[JUNG_SEONG] = 3; // 'ぢ'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 4) { // 'っ'
            buffer[JUNG_SEONG] = 5; // 'つ'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 6) { // 'づ'
            buffer[JUNG_SEONG] = 7; // 'て'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          } else if (buffer[JUNG_SEONG] == 18) { // 'ぱ'
            buffer[JUNG_SEONG] = 19; // 'ひ'
            ic.deleteSurroundingText(1, 0);
            text_to_commit(
                String.format("%c",
                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)) );
            automata_level = JONG_SEONG;
          }
        }
    }

  };

  public static void LEVEL_JONG_SEONG() {
    switch (count_finger) { // yoon // step 2. switch by finger counts for accuracy

      case 1: // yoon // 150413 // case for single finger

        // yoon // 150517 // Conditional Statements for 'ja-eum jong-seong'
        if (finger[INDEX_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 21;// 'し'
          buffer[WISP_FLARE] = 11;// 'し'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 16;// 'け'
          buffer[WISP_FLARE] = 6;// 'け'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 4;// 'い'
          buffer[WISP_FLARE] = 2;// 'い'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[PINKY_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 8;// 'ぉ'
          buffer[WISP_FLARE] = 5;// 'ぉ'
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
        if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 1;// 'ぁ'
          buffer[WISP_FLARE] = 0;// 'ぁ'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 7;// 'ぇ'
          buffer[WISP_FLARE] = 3;// 'ぇ'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
          buffer[JONG_SEONG] = 17;// 'げ'
          buffer[WISP_FLARE] = 7;// 'げ'
          ic.deleteSurroundingText(1, 0);
          text_to_commit(
              String.format(
                  "%c",
                  generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                      buffer[JONG_SEONG])) );
          automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
        }
    }
  };

  public static void LEVEL_JONG_SEONG_TO_CHO_SEONG() {

    // yoon // 150517 // Conditional Statements for Wisp phenomenon
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

    } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
        && finger[PINKY_FINGER] == DIRECTION_DOT) { // 'さ'

      if (buffer[JONG_SEONG] == 1) { // 'ぁ' + 'さ'
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
      } else if (buffer[JONG_SEONG] == 17) { // 'げ' + 'さ'
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
      } else
        LEVEL_CHO_SEONG();
    } else
      LEVEL_CHO_SEONG();
  };


  // yoon // THIS IS WHAT I'M REALLY WANT TO DO !!
  protected String execute(int[] finger_array, InputConnection input_connection) {


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

    if (ENABLE_DEBUG == true) // yoon // 150413 // for debug
      print_log("Automata bgn ...");

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

    if (ENABLE_DEBUG == true) // yoon // 150413 // for debug
      print_log("Automata ... end");
    return (ready_to_commit_text == true ? text_to_commit : "");
  }


}
