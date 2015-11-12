package kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter;

public class Consonant extends KoreanCharacter {

	private int charNumCho;
	private int charNumJong;

	public Consonant(char name, int charNumCho, int charNumJong, int unicode) {
		super(name, unicode);
		this.charNumCho = charNumCho;
		this.charNumJong = charNumJong;
	}

	public int getCharNumCho() {
		return charNumCho;
	}

	public int getCharNumJong() {
		return charNumJong;
	}

	public int getCharNum() {
		return charNumCho;
	}

}
