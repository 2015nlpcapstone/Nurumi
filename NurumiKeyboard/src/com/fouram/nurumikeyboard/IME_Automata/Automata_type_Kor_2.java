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
                    if (finger[MIDDLE_FINGER] == DIRECTION_RIGHT || finger[MIDDLE_FINGER] == DIRECTION_LEFT ||
                            finger[MIDDLE_FINGER] == DIRECTION_UP || finger[MIDDLE_FINGER] == DIRECTION_DOWN ||
                            finger[RING_FINGER] == DIRECTION_LEFT || finger[RING_FINGER] == DIRECTION_RIGHT) {
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
                    if (finger[MIDDLE_FINGER] == DIRECTION_RIGHT || finger[MIDDLE_FINGER] == DIRECTION_DOWN
                            || finger[RING_FINGER] == DIRECTION_RIGHT || finger[RING_FINGER] == DIRECTION_LEFT) {
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

        } else if (finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER]==DIRECTION_EMPTY && finger[THUMB_FINGER]==DIRECTION_EMPTY) { // 'ㅅ'

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

        } else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 4 ) { // 'ㅈ'-> 'ㄵ'

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

        } else if(finger[RING_FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_EMPTY
                && finger[MIDDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 8) { // 'ㄹ'->'ㄺ'

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
        } else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8) { // 'ㄹ'->'ㄼ'

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
                finger[MIDDLE_FINGER]== DIRECTION_EMPTY && finger[RING_FINGER]== DIRECTION_EMPTY) { // 'ㅇ'->'ㅁ'

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
        } else if(finger[MIDDLE_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 4
                && finger[RING_FINGER] == DIRECTION_EMPTY  && finger[INDEX_FINGER] == DIRECTION_EMPTY) { // 'ㄴ'->'ㄹ'

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
        }  else if( finger[RING_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 1) { // 'ㄱ'->'ㅋ'

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
        }  else if(finger[MIDDLE_FINGER] == DIRECTION_DOT &&  finger[RING_FINGER] == DIRECTION_DOT
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
        }  else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_EMPTY && buffer[JONG_SEONG] == 17 ) { // 'ㅂ'->'ㅍ'

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
        } else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_DOT && buffer[JONG_SEONG] == 22) { // 'ㅈ'->'ㅊ'

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
        } else if(finger[INDEX_FINGER] == DIRECTION_DOT && finger[RING_FINGER] == DIRECTION_DOT
                && finger[MIDDLE_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 11 ) { // 'ㄼ'->'ㄹㅍ'

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
        } else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[RING_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT
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
        }  else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_EMPTY
                && finger[RING_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 8 ) { // 'ㄹ'->'ㄹㅁ'

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
        }  else if(finger[THUMB_FINGER] == DIRECTION_DOT &&finger[INDEX_FINGER] == DIRECTION_DOT && finger[MIDDLE_FINGER] == DIRECTION_DOT
                && finger[RING_FINGER] == DIRECTION_EMPTY &&buffer[JONG_SEONG] == 4 ) { // 'ㄴ'->ㄴㅎ'
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
            LEVEL_CHO_SEONG();

    };
