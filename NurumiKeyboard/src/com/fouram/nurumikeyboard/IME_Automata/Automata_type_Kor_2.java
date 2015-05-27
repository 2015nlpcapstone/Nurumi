package com.fouram.nurumikeyboard.IME_Automata;

import android.util.Log;
import android.view.inputmethod.InputConnection;

public class Automata_type_Kor_2 extends IME_Automata {

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

    private String text_to_commit = null;
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
                    buffer[CHO_SEONG] = 11;// 'し'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[MIDLE_FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 2; // 'い'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 0; // 'ぁ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                }


                // yoon // 150413 // Conditional Statements for 'mo-eum letter'
                else if (finger[INDEX_FINGER] != DIRECTION_EMPTY)
                    switch (finger[INDEX_FINGER]) {

                        case DIRECTION_UP:
                            text_to_commit( "で" ); // 'で'
                            break;
                        case DIRECTION_RIGHT:
                            text_to_commit( "た"  );  //'た'
                            break;
                        case DIRECTION_DOWN:
                            text_to_commit( "ぬ" );  //'ぬ'
                            break;
                        case DIRECTION_LEFT:
                            text_to_commit( "っ" ); //'っ'
                            break;
                    }
                break; // yoon // 150413 // break for single finger

            case 2: // yoon // 150424 // case for two fingers
                // 150517 // mod ipt method
                if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 9;// 'さ'
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
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 1;// 'あ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                }

                // yoon // 150507 // Conditional Statements for 'mo-eum jung_seong'
                else if (finger[INDEX_FINGER] != DIRECTION_EMPTY && finger[MIDLE_FINGER] != DIRECTION_EMPTY) {
                    switch (finger[INDEX_FINGER]) {

                        case DIRECTION_UP:
                            text_to_commit( "に" ); //'に'
                            break;
                        case DIRECTION_RIGHT:
                            text_to_commit( "ち"); //'ち'
                            break;
                        case DIRECTION_DOWN:
                            text_to_commit( "ば" ); //'ば'
                            break;
                        case DIRECTION_LEFT:
                            text_to_commit( "づ" ); //'づ'
                            break;

                    }
                } else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
                && finger[RING__FINGER] == DIRECTION_RIGHT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
                    text_to_commit( "だ" ); //'だ'
                }else if (finger[INDEX_FINGER] == DIRECTION_LEFT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
                && finger[RING__FINGER] == DIRECTION_LEFT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
                    text_to_commit( "つ" ); //'つ'
                }
                break; // yoon // 150413 // break for two fingers

            case 3: // yoon // 150507 // case for three fingers
                // 150517 // mod ipt method
                if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
                        && finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 12; // 'じ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
                        && finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 4; // 'え'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
                        && finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 8; // 'こ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
                        && finger[MIDLE_FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 10; // 'ざ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
                && finger[RING__FINGER] == DIRECTION_RIGHT && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
                    text_to_commit( "ぱ" ); //'ぱ'
                }else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
                && finger[RING__FINGER] == DIRECTION_DOWN && finger[THUMB_FINGER] == DIRECTION_EMPTY) {
                    text_to_commit( "び" ); //'び'
                }

                break;// yoon // 150507 // break for three fingers

            case 4: // yoon // 150507 // case for four fingers

                if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
                        && finger[RING__FINGER] == DIRECTION_DOT && finger[THUMB_FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = 13;// 'す'
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
                    buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 11 ? replace_to(6) : 11);// 'し' or 'け'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[MIDLE_FINGER] == DIRECTION_DOT ) {
                    buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 2 ? replace_to(5) : 2);// 'い' or 'ぉ'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                } else if (finger[RING__FINGER] == DIRECTION_DOT) {
                    buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 0 ? replace_to(15) : 0);// 'ぁ' or 'せ'
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
                    buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 9 ? replace_to(18) : 9);// 'さ' or 'ぞ'
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
                }else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[RING__FINGER] == DIRECTION_RIGHT) {

                    buffer[JUNG_SEONG] = 1; // 'だ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format("%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
                    automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
                }else if(finger[INDEX_FINGER] == DIRECTION_LEFT && finger[RING__FINGER] == DIRECTION_LEFT) {

                    buffer[JUNG_SEONG] = 5; // 'つ'
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
                    buffer[CHO_SEONG] = (buffer[CHO_SEONG] == 12 ? replace_to(14) : 12);// 'じ' or 'ず'
                    text_to_commit( String.format("%c", PREF_CHO[buffer[CHO_SEONG]]) );
                    automata_level = LEVEL_JUNG_SEONG;
                }else if (finger[INDEX_FINGER] == DIRECTION_RIGHT && finger[MIDLE_FINGER] == DIRECTION_RIGHT
                        && finger[RING__FINGER] == DIRECTION_RIGHT) {

                    buffer[JUNG_SEONG] = 18; // 'ぱ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format("%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
                    automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
                }else if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
                        && finger[RING__FINGER] == DIRECTION_DOWN) {
                    buffer[JUNG_SEONG] = 20; // 'び'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format("%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
                    automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;


                } else if (finger[THUMB_FINGER] == DIRECTION_RIGHT && finger[INDEX_FINGER] == DIRECTION_RIGHT
                        && finger[RING__FINGER] == DIRECTION_RIGHT) {
                    buffer[JUNG_SEONG] = 3; // 'ぢ'

                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format("%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG], 0)));
                    automata_level = LEVEL_JUNG_SEONG_TO_JONG_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_LEFT && finger[INDEX_FINGER] == DIRECTION_LEFT
                        && finger[RING__FINGER] == DIRECTION_LEFT) {
                    buffer[JUNG_SEONG] = 7; // 'て'
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
                if (finger[INDEX_FINGER] == DIRECTION_DOWN && finger[MIDLE_FINGER] == DIRECTION_DOWN
                        && finger[RING__FINGER] == DIRECTION_DOWN) {
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
                    } else if (buffer[JUNG_SEONG] == 18) { // 'ぱ'
                        buffer[JUNG_SEONG] = 19; // 'ひ'
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
                    buffer[JONG_SEONG] = 21;// 'し'
                    buffer[WISP_FLARE] = 11;// 'し'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_EMPTY
                        && finger[RING__FINGER] == DIRECTION_EMPTY) {
                    buffer[JONG_SEONG] = 4;// 'い'
                    buffer[WISP_FLARE] = 2;// 'い'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[RING__FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_EMPTY
                        && finger[INDEX_FINGER] == DIRECTION_EMPTY) {
                    buffer[JONG_SEONG] = 1;// 'ぁ'
                    buffer[WISP_FLARE] = 0;// 'ぁ'
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
                    buffer[JONG_SEONG] = 19;// 'さ'
                    buffer[WISP_FLARE] = 9;// 'さ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
                        && finger[INDEX_FINGER] == DIRECTION_EMPTY) {
                    buffer[JONG_SEONG] = 7;// 'ぇ'
                    buffer[WISP_FLARE] = 3;// 'ぇ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT
                        && finger[MIDLE_FINGER] == DIRECTION_EMPTY) {
                    buffer[JONG_SEONG] = 17;// 'げ'
                    buffer[WISP_FLARE] = 7;// 'げ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
                    print_log("ok");
                    buffer[JONG_SEONG] = 2;// 'あ'
                    buffer[WISP_FLARE] = 1;// 'あ'
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
                    buffer[JONG_SEONG] = 22;// 'じ'
                    buffer[WISP_FLARE] = 12;// 'じ'
                    ic.deleteSurroundingText(1, 0);
                    text_to_commit(
                            String.format(
                                    "%c",
                                    generate_korean_char_code(buffer[CHO_SEONG], buffer[JUNG_SEONG],
                                            buffer[JONG_SEONG])) );
                    automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
                } else if (finger[THUMB_FINGER] == DIRECTION_DOT && finger[INDEX_FINGER] == DIRECTION_DOT
                        && finger[MIDLE_FINGER] == DIRECTION_DOT) {
                    buffer[JONG_SEONG] = 20;// 'ざ'
                    buffer[WISP_FLARE] = 10;// 'ざ'
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

                    }
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

                    }
                    break;
                case 3:
                    if (finger[MIDLE_FINGER] == DIRECTION_RIGHT || finger[MIDLE_FINGER] == DIRECTION_DOWN
                            || finger[RING__FINGER] == DIRECTION_RIGHT || finger[RING__FINGER] == DIRECTION_LEFT) {
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

                    }
                    break;

            }

        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
                && finger[RING__FINGER]==DIRECTION_EMPTY && finger[THUMB_FINGER]==DIRECTION_EMPTY) { // 'さ'

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
            } else if (buffer[JONG_SEONG] == 8) { // 'ぉ' + 'さ'
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
            }  else if( buffer[JONG_SEONG] == 19) { // 'さ'->'ぞ'
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
            }else if(buffer[JONG_SEONG] == 12) { // 'き'->'ぐ'

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
                && finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 4 ) { // 'じ'-> 'ぅ'

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
                && finger[MIDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 8) { // 'ぉ'->'お'

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
                && finger[MIDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8) { // 'ぉ'->'が'

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
                finger[MIDLE_FINGER]== DIRECTION_EMPTY && finger[RING__FINGER]== DIRECTION_EMPTY) { // 'し'->'け'

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
                && finger[RING__FINGER] == DIRECTION_EMPTY  && finger[INDEX_FINGER] == DIRECTION_EMPTY) { // 'い'->'ぉ'

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
        }  else if( finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 1) { // 'ぁ'->'せ'

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
                && finger[INDEX_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 7) { // 'ぇ'->'ぜ'

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
                && finger[MIDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 17 ) { // 'げ'->'そ'

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
                && finger[RING__FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 22) { // 'じ'->'ず'

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
                && finger[MIDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 11 ) { // 'が'->'ぉそ'

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
                && finger[INDEX_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8 ) { // 'ぉ'->'ぉぜ'

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
                && finger[RING__FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8 ) { // 'ぉ'->'ぉけ'

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
                && finger[RING__FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 4 ) { // 'い'->いぞ'

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
        } else
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


}
