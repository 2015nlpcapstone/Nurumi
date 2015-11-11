package kookmin.cs.fouram.nurumikeyboard.automata;

import java.util.HashMap;

import kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter.KoreanCharacter;
import android.util.Log;
import android.view.inputmethod.InputConnection;
public class KoreanAdvancedNaratgul extends Korean {

	
	HashMap<Long, KoreanCharacter> kMap = new HashMap<Long, KoreanCharacter>();
	
	public KoreanAdvancedNaratgul() {
		kMap.put(32L, YIEUNG);
		kMap.put(1024L, MIEUM);
		kMap.put(32768L, NIEUN);
		kMap.put(1048576L, LIEUL);
		
		kMap.put(1056L, GIYEOK);
		kMap.put(33792L, DIGEUT);
		kMap.put(32800L, BIEUP);
		kMap.put(1082368L, SIOT);
		kMap.put(33824L, JIEUT);
		kMap.put(1082400L, HIEUT);
		
		kMap.put(3L, KIEUK); // 임의의 KEY값
		kMap.put(5L, TIEUT); 
		kMap.put(9L, PIEUP);
		kMap.put(17L, CHIEUT);
		
		kMap.put(1057L, SSANG_GIYEOK);
		kMap.put(33793L, SSANG_DIGEUT);
		kMap.put(32801L, SSANG_BIEUP);
		kMap.put(1082369L, SSANG_SIOT);
		kMap.put(33825L, SSANG_JIEUT);
		
		kMap.put(64L, OH);
		kMap.put(512L, AH);
		kMap.put(128L, WOO);
		kMap.put(256L, UH);
		kMap.put(2112L, YO);
		kMap.put(16896L, YA);
		kMap.put(4224L, YOO);
		kMap.put(8448L, YUH);
		kMap.put(17318400L, EU);
		kMap.put(4329600L, YI);
		kMap.put(524800L, AE);
		kMap.put(262400L, E);
		kMap.put(541184L, YAE);
		kMap.put(270592L, YE);
		
		kMap.put(18L, WA);
		kMap.put(19L, WAE);
		kMap.put(20L, OE);
		kMap.put(21L, WUH);
		kMap.put(22L, WEH);
		kMap.put(23L, WUI);
		kMap.put(24L, EUI);

		kMap.put(25L, GIYEOK_SIOT);
		kMap.put(26L, NIEUN_JIEUT);
		kMap.put(27L, NIEUN_HIEUT);
		kMap.put(28L, LIEUL_GIYEOK);
		kMap.put(29L, LIEUL_MIEUM);
		kMap.put(30L, LIEUL_BIEUP);
		kMap.put(31L, LIEUL_SIOT);
		kMap.put(35L, LIEUL_TIEUT);
		kMap.put(37L, LIEUL_PIEUP);
		kMap.put(38L, LIEUL_HIEUT);
		kMap.put(39L, BIEUP_SIOT);
		buffer = new KoreanCharacter[5];
	}
	
	private final int LEVEL_CHO_SEONG = 0;
	private final int LEVEL_JUNG_SEONG = 1;
	private final int LEVEL_JONG_SEONG = 2;
	private final int LEVEL_BOK_JA_EUM_JONG_SEONG= 3;
	private final int LEVEL_JONG_SEONG_TO_CHO_SEONG = 4;

	private final int CHO_SEONG = 0;
	private final int JUNG_SEONG = 1;
	private final int JONG_SEONG = 2;
	private final int WISP_FLARE = 3;
	private final int BF_JONG_SEONG = 4;

	private KoreanCharacter buffer[];
	
	//private int buffer[] = {'\0', '\0', '\0', '\0'};
	private int automata_level = 0;
	//private int bok_ja_eum_jong_seong = 0;

	private Boolean ready_to_commit_text;

	// yoon // 150516 // get a Korean character code key value
	private int generate_korean_char_code(int cho_seong, int jung_seong, int jong_seong) {
		return ((AC00 + ((cho_seong * 21) + jung_seong) * 28) + jong_seong);
	}
	private int choseong_moeum(int moeum) {return (moeum + 12623 );}

	// yoon // 150518 // assign text to commit &  & validate for committing
	private void setText(String str) {
		text_to_commit = str;
		ready_to_commit_text = true;
	}

	// yoon // 150517 // clean character buffer
	private void buffer_clean() {
		int i = 4;
		while (i-- > 0)
			buffer[i] = null;
		//bok_ja_eum_jong_seong = 0;
	}

	/*
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
*/
	private void LEVEL_CHO_SEONG() {
		
		// yoon // 150517 // buffer clean
		buffer_clean();
		setText(String.format("%c", generate_korean_char_code(0,0,0)));
		/*Log.d("AUTOMATA_LOG", "motion : " + motion);
		buffer[CHO_SEONG] = kMap.get(motion);
		if(buffer[CHO_SEONG].getType()==MOEUM){
			setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
		}
		else{
			setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),0,0)));
		}
		automata_level = LEVEL_JUNG_SEONG;*/
	};

	private void LEVEL_JUNG_SEONG() {

		KoreanCharacter temp = kMap.get(motion);

		if (temp.getType() == MOEUM) {
			if (buffer[CHO_SEONG].getType() == MOEUM) {
				if (buffer[CHO_SEONG].getCharNum() == OH.getCharNum()) {
					if (temp.getCharNum() == AH.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[CHO_SEONG] = kMap.get(18L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else if (temp.getCharNum() == AE.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[CHO_SEONG] = kMap.get(19L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else if (temp.getCharNum() == YI.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[CHO_SEONG] = kMap.get(20L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else {                    //변형되지 않는 모음 다음에 모음이 왔을경우
						buffer_clean();
						setText(String.format("%c", choseong_moeum(temp.getCharNum())));
						buffer[CHO_SEONG] = temp;
						automata_level = LEVEL_JUNG_SEONG;
					}
				} else if (buffer[JUNG_SEONG].getCharNum() == WOO.getCharNum()) {
					if (temp.getCharNum() == UH.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[JUNG_SEONG] = kMap.get(21L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else if (temp.getCharNum() == E.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[JUNG_SEONG] = kMap.get(22L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else if (temp.getCharNum() == YI.getCharNum()) {
						ic.deleteSurroundingText(1, 0);
						buffer[JUNG_SEONG] = kMap.get(23L); // 을 으로
						setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
						buffer_clean();
						automata_level = LEVEL_CHO_SEONG;
					} else {            //변형되지 않는 모음 다음에 모음이 왔을경우
						buffer_clean();
						setText(String.format("%c", choseong_moeum(temp.getCharNum())));
						buffer[CHO_SEONG] = temp;
						automata_level = LEVEL_JUNG_SEONG;
					}
				} else {            //변형 되지 않는 모음 다음에 모음이 왔을 경우
					buffer_clean();
					setText(String.format("%c", choseong_moeum(temp.getCharNum())));
					buffer[CHO_SEONG] = temp;
					automata_level = LEVEL_JUNG_SEONG;
				}
			}
			else{			//자음 다음에 모음이 왔을 경우
				buffer[JUNG_SEONG] = temp;
				Log.d("AUTOMATA_LOG", "211 line. unicode: " + buffer[CHO_SEONG].getCharNum() + " " + buffer[JUNG_SEONG].getCharNum());
				Log.d("AUTOMATA_LOG", "" + generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),buffer[JUNG_SEONG].getCharNum(),0));
				setText(String.format("%c",generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
						buffer[JUNG_SEONG].getCharNum(),0)));
				automata_level = LEVEL_JONG_SEONG;
			}
		}
		else {				//자음이 왔을때
			if (temp.getCharNum() == GIYEOK.getCharNum() && buffer[CHO_SEONG].getCharNum() == GIYEOK.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[CHO_SEONG] = kMap.get(3L); // ㄱ을 ㅋ으로					
			} else if (temp.getCharNum() == DIGEUT.getCharNum() && buffer[CHO_SEONG].getCharNum() == DIGEUT.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[CHO_SEONG] = kMap.get(5L); // ㄷ을 ㅌ으로	
			} else if (temp.getCharNum() == BIEUP.getCharNum() && buffer[CHO_SEONG].getCharNum() == BIEUP.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[CHO_SEONG] = kMap.get(9L); // ㅂ을 ㅍ으로
			} else if (temp.getCharNum() == JIEUT.getCharNum() && buffer[CHO_SEONG].getCharNum() == JIEUT.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[CHO_SEONG] = kMap.get(17L); // ㅈ을 ㅊ으로
			} else {
					buffer[CHO_SEONG] = temp;
			}
			setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), 0, 0)));
				automata_level = LEVEL_JUNG_SEONG;
		}
	};

	private void LEVEL_JONG_SEONG() {

		KoreanCharacter temp = kMap.get(motion);
		
		if(temp.getType() == MOEUM) {
			if(buffer[JUNG_SEONG].getCharNum() == OH.getCharNum()) {
				if(temp.getCharNum() == AH.getCharNum()){
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(18L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else if(temp.getCharNum() == AE.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(19L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else if(temp.getCharNum() == YI.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(20L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else {
					buffer_clean();
					buffer[CHO_SEONG] = temp;
					setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
					automata_level = LEVEL_JUNG_SEONG;
				}
			}
			
			else if(buffer[JUNG_SEONG].getCharNum() == WOO.getCharNum()) {
				if(temp.getCharNum() == UH.getCharNum()){
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(21L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else if(temp.getCharNum() == E.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(22L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else if(temp.getCharNum() == YI.getCharNum()) {
					ic.deleteSurroundingText(1, 0);
					buffer[JUNG_SEONG] = kMap.get(23L); // 을 으로
					setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
							buffer[JUNG_SEONG].getCharNum(), 0)));
					automata_level = LEVEL_JONG_SEONG;
				}
				else {
					buffer_clean();
					buffer[CHO_SEONG] = temp;
					setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
					automata_level = LEVEL_JUNG_SEONG;
				}
			}
			else if(buffer[JUNG_SEONG].getCharNum() == EU.getCharNum() && temp.getCharNum() == YI.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[JUNG_SEONG] = kMap.get(24L); // 을 으로
				setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
						buffer[JUNG_SEONG].getCharNum(), 0)));
				automata_level = LEVEL_JONG_SEONG;
			}
			
			else {
				buffer_clean();
				buffer[CHO_SEONG] = temp;
				setText(String.format("%c", choseong_moeum(buffer[CHO_SEONG].getCharNum())));
				automata_level = LEVEL_JUNG_SEONG;
			}
		}
		
		else {			//자음이 왔을때
			if(temp == kMap.get(33793L) || temp == kMap.get(32801L) || temp == kMap.get(33825L)) {		// 종성에 ㄸ,ㅃ,ㅉ이 올때
				buffer_clean();
				buffer[CHO_SEONG] = temp;
				setText(String.format("%c", generate_korean_char_code(temp.getCharNum(), 0, 0)));
				automata_level = JUNG_SEONG;
			} else {
				buffer[JONG_SEONG] = temp;
				ic.deleteSurroundingText(1, 0);
				setText(String.format("%c",
							   generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), 
														  buffer[JUNG_SEONG].getCharNum(),
														  buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_BOK_JA_EUM_JONG_SEONG;
			}
		}
		
	};

	private void LEVEL_BOK_JA_EUM_JONG_SEONG() {

		KoreanCharacter temp = kMap.get(motion);
		
		if(temp.getType() == MOEUM) {			//모음이 왔을때
			ic.deleteSurroundingText(1, 0);
			setText(
					String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), 0),
							generate_korean_char_code(buffer[JONG_SEONG].getCharNum(), temp.getCharNum(), 0)));
			buffer[CHO_SEONG] = buffer[JONG_SEONG];
			buffer[JUNG_SEONG] = temp;
			buffer[JONG_SEONG] = null;
			automata_level = LEVEL_JONG_SEONG;
		}
		else {					//자음이 왔을때
			if (temp.getCharNum() == GIYEOK.getCharNum() && buffer[JONG_SEONG].getCharNum() == GIYEOK.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[JONG_SEONG] = kMap.get(3L); // ㄱ을 ㅋ으로
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_BOK_JA_EUM_JONG_SEONG;
			} else if (temp.getCharNum() == DIGEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == DIGEUT.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[JONG_SEONG] = kMap.get(5L); // ㄷ을 ㅌ으로
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_BOK_JA_EUM_JONG_SEONG;
			} else if (temp.getCharNum() == BIEUP.getCharNum() && buffer[JONG_SEONG].getCharNum() == BIEUP.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[JONG_SEONG] = kMap.get(9L); // ㅂ을 ㅍ으로
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_BOK_JA_EUM_JONG_SEONG;
			} else if (temp.getCharNum() == JIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == JIEUT.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[JONG_SEONG] = kMap.get(17L); // ㅈ을 ㅊ으로
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_BOK_JA_EUM_JONG_SEONG;
			}else if (temp.getCharNum() == SIOT.getCharNum() && buffer[JONG_SEONG].getCharNum() == GIYEOK.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(25L); // ㄱ을 ㄳ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if (temp.getCharNum() == JIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == NIEUN.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(26L); // ㄴ을 ㄵ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == HIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == NIEUN.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(27L); // ㄴ을 ㄶ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == GIYEOK.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(28L); // ㄹ을 ㄺ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == MIEUM.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(29L); // ㄹ을 ㄻ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == BIEUP.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(30L); // ㄹ을 ㄼ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == SIOT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(31L); // ㄹ을 ㄽ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == TIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(35L); // ㄹ을 ㄾ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == PIEUP.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(37L); // ㄹ을 ㄿ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == HIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(38L); // ㄹ을 ㅀ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == SIOT.getCharNum() && buffer[JONG_SEONG].getCharNum() == BIEUP.getCharNum()) {
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = buffer[JONG_SEONG];
				buffer[JONG_SEONG] = kMap.get(39L); // ㅂ을 ㅄ으로
				buffer[WISP_FLARE] = temp;
				setText(String.format("%c",
						generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
								buffer[JUNG_SEONG].getCharNum(),
								buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else if (temp.getCharNum() == DIGEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) { //이것은 ㄹㄷ 이 ㄾ로 변형되는것을 대비하기 위한 if문이다.
				buffer[WISP_FLARE] = temp;		//ㄷ 저장
				setText(String.format("%c",
						generate_korean_char_code(buffer[WISP_FLARE].getCharNum(),0,0)));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else {			//그 외에 자음이 왔을때
				buffer_clean();
				buffer[CHO_SEONG] = temp;
				setText(String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), 0, 0)));
				automata_level = LEVEL_JUNG_SEONG;
			}
		}
	};

	private void LEVEL_JONG_SEONG_TO_CHO_SEONG(){

		KoreanCharacter temp = kMap.get(motion);

		if(temp.getType() == MOEUM){				//모음이 왔을때
			if(buffer[WISP_FLARE].getCharNum() == DIGEUT.getCharNum()){	//할ㄷ 인 상태에서 모음이 오면 할다 인 상태가 되도록 한다.
				ic.deleteSurroundingText(1, 0);
				buffer_clean();
				buffer[CHO_SEONG] = kMap.get(33792L);
				buffer[JUNG_SEONG] = temp;
				setText(String.format("%c",generate_korean_char_code(buffer[CHO_SEONG].getCharNum(),
						buffer[JUNG_SEONG].getCharNum(),0)));
				automata_level = LEVEL_JONG_SEONG;

			} else {
				ic.deleteSurroundingText(1, 0);
				setText(
						String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), buffer[BF_JONG_SEONG].getCharNum()),
								generate_korean_char_code(buffer[WISP_FLARE].getCharNum(), temp.getCharNum(), 0)));
				buffer[CHO_SEONG] = buffer[JONG_SEONG];
				buffer[JUNG_SEONG] = temp;
				buffer[JONG_SEONG] = null;
				buffer[WISP_FLARE] = null;
				buffer[BF_JONG_SEONG] = null;
				automata_level = LEVEL_JONG_SEONG;
			}
		}
		else{
			if (temp.getCharNum() == GIYEOK.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL_GIYEOK.getCharNum()) {		// 앞에 ㄺ 인데 ㄱ 이 오면 ㄹㅋ 이 되어야 된다.
				ic.deleteSurroundingText(1, 0);
				setText(
						String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), buffer[BF_JONG_SEONG].getCharNum()),
								generate_korean_char_code(buffer[WISP_FLARE].getCharNum(), 0, 0)));
				buffer[CHO_SEONG] = buffer[WISP_FLARE];
				buffer[JUNG_SEONG] = null;
				buffer[JONG_SEONG] = null;
				buffer[WISP_FLARE] = null;
				buffer[BF_JONG_SEONG] = null;
				automata_level = LEVEL_JUNG_SEONG;
			}  else if(temp.getCharNum() == BIEUP.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL_BIEUP.getCharNum()) {		// 앞에 ㄼ 인데 ㅂ 이 오면 ㄿ 이 되어야 된다.
				ic.deleteSurroundingText(1, 0);
				buffer[WISP_FLARE] = kMap.get(32800L);
				buffer[JONG_SEONG] = kMap.get(37L);
				setText(
						String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), buffer[JONG_SEONG].getCharNum())));
				automata_level = LEVEL_JONG_SEONG_TO_CHO_SEONG;
			} else if(temp.getCharNum() == JIEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL.getCharNum()) {		// 앞에 ㄵ 인데 ㅈ 이 오면 ㄴㅊ이 되어야 된다.
				ic.deleteSurroundingText(1, 0);
				setText(
						String.format("%c%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), buffer[BF_JONG_SEONG].getCharNum()),
								generate_korean_char_code(buffer[WISP_FLARE].getCharNum(), 0, 0)));
				buffer[CHO_SEONG] = buffer[WISP_FLARE];
				buffer[JUNG_SEONG] = null;
				buffer[JONG_SEONG] = null;
				buffer[WISP_FLARE] = null;
				buffer[BF_JONG_SEONG] = null;
				automata_level = LEVEL_JUNG_SEONG;
			} else if(temp.getCharNum() ==  DIGEUT.getCharNum() && buffer[JONG_SEONG].getCharNum() == LIEUL_BIEUP.getCharNum() && buffer[WISP_FLARE].getCharNum() == DIGEUT.getCharNum()){	//ㄹㄷ이 ㄾ이 될때
				ic.deleteSurroundingText(1, 0);
				ic.deleteSurroundingText(1, 0);
				buffer[BF_JONG_SEONG] = kMap.get(1048576L);		// ㄹ 저장
				buffer[WISP_FLARE] = kMap.get(5L); 	//ㅌ 저장
				buffer[JONG_SEONG] = kMap.get(35L);	//ㄾ 저장
				setText(
						String.format("%c", generate_korean_char_code(buffer[CHO_SEONG].getCharNum(), buffer[JUNG_SEONG].getCharNum(), buffer[JONG_SEONG].getCharNum())));
				automata_level=LEVEL_JONG_SEONG_TO_CHO_SEONG;
			}else{		//변형 할 필요가 없으면 그냥 LEVEL_CHO_SEONG으로 들어간다.
				LEVEL_CHO_SEONG();
			}
		}
	};

	// yoon // THIS IS WHAT I'M REALLY WANT TO DO !!

	public String execute(long motionValue, InputConnection input_connection) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - execute()");
		// yoon // 150516 // init values
		motion = motionValue;
		Log.d("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - execute(). motion : " + motion + ", motionValue : " + motionValue);
		ic = input_connection;
		ready_to_commit_text = false; // yoon // 150518 // for validate motion to text functionality 

		if(motion == 4L) {
			automata_level = LEVEL_CHO_SEONG;
			return " ";
		}
		else if(motion == 16L) {
			automata_level = LEVEL_CHO_SEONG;
			ic.deleteSurroundingText(1, 0);
			return "";
		}
		/*
		if (ENABLE_DEBUG) // yoon // 150413 // for debug
			print_log("Automata bgn ...");
*/
	
		// yoon // 150413 // switch by automata level
		// yoon // 150516 // Code refactoring : Devide & Conquer

		switch (automata_level) { // yoon // step 1. switch by automata level

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

		case  LEVEL_JONG_SEONG_TO_CHO_SEONG:
			LEVEL_JONG_SEONG_TO_CHO_SEONG();
			break;
		}

/*		if (ENABLE_DEBUG) // yoon // 150413 // for debug
			print_log("Automata ... end");
			*/
		return (ready_to_commit_text == true ? text_to_commit : "");
	}

	@Override
	public boolean isAllocatedMotion(long motion) {
		Log.i("AUTOMATA_LOG", "Location : Automata_type_Kor_3 - isAllocatedMotion()");
		if( motion == 32L || motion == 1024L || motion == 32768L || motion == 1048576L || motion == 1056L ||
			motion == 33792L || motion == 32800L || motion == 1082368L || motion == 33824L || motion == 1082400L ||
			motion == 1057L || motion == 33793L || motion == 32801L || motion == 1082369L || motion == 33825L ||
			motion == 64L || motion == 512L || motion == 128L || motion == 256L || motion == 2112L ||
			motion == 16896L || motion == 4224L || motion == 8448L || motion == 17318400L || motion == 4329600L ||
			motion == 524800L || motion == 262400L || motion == 541184L || motion == 270592L || motion == 4100L || motion == 16400L)
			return true;
		return false;
	}
}