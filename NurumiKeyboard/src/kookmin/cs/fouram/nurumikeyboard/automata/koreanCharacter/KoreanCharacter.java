package kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter;

public abstract class KoreanCharacter {

	private char name;
	private int unicode;

	// Constructor
	public KoreanCharacter(char name, int unicode) {
		this.name = name;
		this.unicode = unicode;
	}
	// getter
	public char getName() {
		return name;
	}

	public int getUnicode() {
		return unicode;
	}

	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}

	public abstract int getCharNum();
}
