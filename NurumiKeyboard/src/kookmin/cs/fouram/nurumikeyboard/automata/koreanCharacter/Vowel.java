package kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter;

public class Vowel extends KoreanCharacter {

	private int charNum;
	
	public Vowel(char name, int unicode, int charNum) {
		super(name, unicode);
		setType(MOEUM);
		this.charNum = charNum;
	}

	public int getCharNum() {
		return charNum;
	}	
}
