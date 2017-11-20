
public class Token {

	public static final int VOID = 1;
	public static final int INT = 2;
	public static final int CHAR = 3;
	public static final int DOUBLE = 4;
	public static final int STRING = 5;
	public static final int IF = 6;
	public static final int ELSE = 7;
	public static final int RETURN = 8;
	public static final int WHILE = 9;
	public static final int ADD = 10;    //+
	public static final int MUL = 11;    //*
	public static final int EQUAL = 12;  //=
	public static final int DOUBLE_EQUAL = 13; //==
	public static final int NOT_EQUAL = 14;    //!=
	public static final int LESS = 15;         //<
	public static final int LESS_EQUAL = 16;   //<=
	public static final int GREATER = 17;      //>
	public static final int GREATER_EQUAL = 18;//>=
	public static final int LEFT_PARENTHESE = 19; //(
	public static final int RIGHT_PARENTHESE = 20;//)
	public static final int LEFT_BRACE = 21;      //{
	public static final int RIGHT_BRACE = 22;     //}
	public static final int COMMA = 23;        //,
	public static final int SEMICOLON = 24;    //;
	public static final int ID = 25;
	public static final int NUMBER = 26;
	public static final int END = 0;
	public static final int ERROR = -1;

	public static final int FuncBlock = 100;
	public static final int DataType = 101;
	public static final int FuncName = 102;
	public static final int Paras = 103;
	public static final int Para = 104;
	public static final int S = 105;
	public static final int Exp = 106;
	public static final int E = 107;
	public static final int E1= 108;
	public static final int T = 109;
	public static final int T1 = 110;
	public static final int F = 111;
	public static final int C = 112;
	public static final int Op = 113;
	public static final int R = 114;
	
	private int code;
	private String str;
	private String error;
	
	public Token(int c,String s){
		this.code = c;
		this.str = s;
		this.error = null;
	}
	
	public Token(String error){
		this.error = error;
		this.code = ERROR;
	}
	
	public String toString(){
		if(this.error != null)
			return "Error:" + this.error;
		return "<" + this.code + "," + this.str + ">";
	}


	public int getCode(){
		return code;
	}
	
}
