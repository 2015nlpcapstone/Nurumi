package kookmin.cs.fouram.nurumikeyboard.automata.koreanCharacter;

public class Consonant extends KoreanCharacter {

	private int charNumCho;
	private int charNumJong;
	private int position;
	
	public Consonant(char name, int charNumCho, int charNumJong, int unicode, int type) {
		super(name, unicode);
		setType(type);
		this.charNumCho = charNumCho;
		this.charNumJong = charNumJong;
	}

	public int getCharNumCho() {
		return charNumCho;
	}

	public int getCharNumJong() {
		return charNumJong;
	}

	public int getPosition() {
		return position;
	}
	
	public int getCharNum() {
		if(position == 1) // 초성
			return getCharNumCho();
		else
			return getCharNumJong();
	}
	
	public void setPosition(int position) {
		this.position = position;
	}	
}
