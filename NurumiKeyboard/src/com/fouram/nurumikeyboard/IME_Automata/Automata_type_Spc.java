package com.fouram.nurumikeyboard.IME_Automata;

import android.view.inputmethod.InputConnection;

/**
 * Created by kimminho on 15. 5. 21..
 */
public class Automata_type_Spc extends IME_Automata {

    private void MODE_SPECIAL() {
        switch (count_finger) {
            case 1:
                if (finger[INDEX_FINGER] != DIRECTION_EMPTY) {
                    switch(finger[INDEX_FINGER]){
                        case DIRECTION_DOT:
                            str_to_write = String.format("%c", 49);
                            break;
                        case DIRECTION_DOWN:
                            str_to_write = String.format("%c", 44);
                            break;
                        case DIRECTION_LEFT:
                            str_to_write = String.format("%c", 63);
                            break;
                        case DIRECTION_UP:
                            str_to_write = String.format("%c", 46);
                            break;
                        case DIRECTION_RIGHT:
                            str_to_write = String.format("%c", 33);
                            break;
                    }
                }
                else if (finger[MIDLE_FINGER] != DIRECTION_EMPTY){
                    switch(finger[MIDLE_FINGER]){
                        case DIRECTION_DOT:
                            str_to_write = String.format("%c", 50);
                            break;
                        case DIRECTION_DOWN:
                            str_to_write = String.format("%c", 126);
                            break;
                        case DIRECTION_LEFT:
                            str_to_write = String.format("%c", 59);
                            break;
                        case DIRECTION_UP:
                            str_to_write = String.format("%c", 94);
                            break;
                        case DIRECTION_RIGHT:
                            str_to_write = String.format("%c", 47);
                            break;
                    }
                }
                else if (finger[RING__FINGER] != DIRECTION_EMPTY){
	                switch(finger[RING__FINGER]){
	                    case DIRECTION_DOT:
	                        str_to_write = String.format("%c", 51);
	                        break;
	                    case DIRECTION_DOWN:
	                        str_to_write = String.format("%c", 95);
	                        break;
	                    case DIRECTION_LEFT:
	                        str_to_write = String.format("%c", 41);
	                        break;
	                    case DIRECTION_UP:
	                        str_to_write = String.format("%c", 45);
	                        break;
	                    case DIRECTION_RIGHT:
	                        str_to_write = String.format("%c", 40);
	                        break;
                	}
                }
                else if (finger[PINKY_FINGER] != DIRECTION_EMPTY){
	                switch(finger[PINKY_FINGER]){
	                    case DIRECTION_DOT:
	                        str_to_write = String.format("%c", 52);
	                        break;
	                    case DIRECTION_DOWN:
	                        str_to_write = String.format("%c", 42);
	                        break;
	                    case DIRECTION_LEFT:
	                        str_to_write = String.format("%c", 61);
	                        break;
	                    case DIRECTION_UP:
	                        str_to_write = String.format("%c", 43);
	                        break;
	                    case DIRECTION_RIGHT:
	                        str_to_write = String.format("%c", 64);
	                        break;
	                }
               }
               else{
                    str_to_write="";
               }
               break;

            case 2:
                if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT) {
                    str_to_write = String.format("%c", 53);
                }
                else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
                    str_to_write = String.format("%c", 54);
                }
                else if (finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT) {
                    str_to_write = String.format("%c", 55);
                }
                else{
                    str_to_write="";
                }
                break;

            case 3:
                if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT) {
                    str_to_write = String.format("%c", 56);
                }
                else if (finger[MIDLE_FINGER] == DIRECTION_DOT && finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT ) {
                    str_to_write = String.format("%c", 57);
                }
                else{
                    str_to_write="";
                }
                break;

            case 4:
                if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDLE_FINGER] == DIRECTION_DOT
                        && finger[RING__FINGER] == DIRECTION_DOT && finger[PINKY_FINGER] == DIRECTION_DOT){
                    str_to_write = String.format("%c", 48);
                }
                else{
                    str_to_write="";
                }
                break;
                
            default:
                    str_to_write="";
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
        else{
            MODE_SPECIAL();
        }
        // kim // 150507 // functional keys
        // kim // 150413 // switch by automata level
        return str_to_write;
    }
}
