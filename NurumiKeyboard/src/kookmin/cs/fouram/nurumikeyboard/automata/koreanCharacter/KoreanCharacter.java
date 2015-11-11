package kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter;

public abstract class KoreanCharacter {
	
	protected final int JAEUM = 0;
	protected final int MOEUM = 1;
	protected final int SPC_JAEUM = 2;
	
	private char name;
	private int type;
	private int unicode;
	
	// Constructor
	public KoreanCharacter(char name, int unicode) {
		this.name = name;
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
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public abstract int getCharNum();
}
