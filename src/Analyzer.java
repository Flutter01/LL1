import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 一个简单的词法分析器 输入程序 返回词法单元
 */
public class Analyzer {

	private static ArrayList<Character> input = new ArrayList<>();// 存储输入的字符数组
	private static ArrayList<Token> tokens = new ArrayList<>();//输出
	private static int code;// Token的类别用种别码表示
	// 以下是可识别的单词符号
	private static String[] reservedWords = {"void", "int", "char", "double",
			"String", "if", "else", "return", "while" };

	public Analyzer(ArrayList<Character> input){
		this.input = input;
	}

	public ArrayList<Token> scanner() {
		int currentPoint = 0;

		while(input.get(currentPoint)!='$') {
			int temp = currentPoint;
			if (Character.isLetter(input.get(currentPoint))) {
				currentPoint = matchKeyWord(currentPoint);
				if (currentPoint == temp) {
					currentPoint = matchID(currentPoint);
				}
			}else if(Character.isDigit((input.get(currentPoint)))) {
				currentPoint = matchNUM(currentPoint);
			}else {
				currentPoint = matchOthers(currentPoint);
			}
		}
		tokens.add(new Token(Token.END,"$"));
		return tokens;
	}

	private static int matchKeyWord(int start) {
		int point = start;
		int p = start;
		String result = "";
		for(int i = 0;i<reservedWords.length;i++) {
			char[] keys = reservedWords[i].toCharArray();
			for(int j=0;j<keys.length;j++) {
				if (input.get(p) == keys[j]) {
					result = result + input.get(p);
					p++;
					if ((p-start)==keys.length){
						code = i + 1;
						tokens.add(new Token(code, result));
						point = p;
						break;
					}
					if (input.get(p) != keys[j + 1]) {
						p--;
						result = "";
						continue;
					}
				} else {
					break;
				}

			}

		}
		return point;
	}

	private static int matchID(int start) {
		int point = start;
		String result = "";
		while(Character.isLetter(input.get(point))||Character.isDigit(input.get(point))) {
			result = result + input.get(point);
			point++;
		}
		tokens.add(new Token(25, result));
		return  point;
	}

	private static int matchNUM(int start) {
		int point = start;
		String result = "";
		while(Character.isDigit(input.get(point))) {
			result = result + input.get(point);
			point++;
		}
		tokens.add(new Token(26, result));
		return  point;
	}

	private static int matchOthers(int start) {
		int point = start;
		char c = input.get(point);
		switch (c) {
			case '+':
				code = 10;
				tokens.add(new Token(code,"+"));
				break;
			case '*':
				code = 11;
				tokens.add(new Token(code,"*"));
				break;
			case '=':
				c = input.get(++point);
				if (c == '=') { // ==
					code = 13;
					tokens.add(new Token(code,"=="));
				} else { // =
					code = 12;
					tokens.add(new Token(code,"="));
					point--;
				}
				break;
			case '<':
				c = input.get(++point);
				if (c == '=') { // <=
					code = 16;
					tokens.add(new Token(code,"<="));
				} else { // <
					code = 15;
					tokens.add(new Token(code,"<"));
					point--;
				}
				break;
			case '>':
				c = input.get(++point);
				if (c == '=') { // >=
					code = 18;
					tokens.add(new Token(code,">="));
				} else { // >
					code = 17;
					tokens.add(new Token(code,">"));
					point--;
				}
				break;
			case '!':
				c = input.get(++point);
				if (c == '=') { // !=
					code = 14;
					tokens.add(new Token(code,"!="));
				} else { // !
					tokens.add(new Token("语法分析不能分析！"));
				}
				break;

			case '(':code = 19; tokens.add(new Token(code,"(")); break;
			case ')':code = 20; tokens.add(new Token(code,")"));break;
			case '{':code = 21; tokens.add(new Token(code,"{"));break;
			case '}':code = 22; tokens.add(new Token(code,"}"));break;
			case ',':code = 23; tokens.add(new Token(code,",")); break;
			case ';':code = 24; tokens.add(new Token(code,";"));break;
			case '\n':code = -1; break;
			default:code = -3; break;
		}

		return  point+1;
	}

}
