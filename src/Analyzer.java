import java.io.*;
import java.util.ArrayList;

/**
 * 一个简单的词法分析器 输入程序 返回词法单元
 */
public class Analyzer {

	private static char input[] = new char[500];// 存储输入的字符数组
	private static ArrayList<Token> tokens = new ArrayList<>();//输出
	private static char word[];// 单词符号
	private static int num;// 常数
	private static int code;// Token的类别用种别码表示
	private static char ch;// 当前读的字符
	private static int p, sp, row;
	private static InfoHandle infoHandle = new InfoHandle();

	// 以下是可识别的单词符号
	private static String[] reservedWords = { "class", "public", "protected",
			"private", "void", "static", "int", "char", "float", "double",
			"String", "if", "else", "return", "while", "try", "catch", "switch",
			"case" ,"for" };

	private static void scanner() {
		word = new char[20];
		ch = input[p++];

		if (Character.isLetter(ch)) {
			sp = 0;
			while (Character.isDigit(ch)||Character.isLetter(ch)) {
				word[sp++] = ch;
				ch = input[p++];
				word[sp] = '\0';
				for (int i = 0; i < reservedWords.length; i++) {
					if (infoHandle.ch2String(word).equals(reservedWords[i])) {
						code = i + 1;
						p --;
						return;
					}
				}
			}
			word[sp++] = '\0';
			p--; // 放回多读的
			code = 56;

		} else if (ch >= '0' && ch <= '9') {
			num = 0;
			while (ch >= '0' && ch <= '9') {
				num = num * 10 + ch - '0';
				ch = input[p++];
			}
			p--;
			code = 57;
			if (num < 0)
				code = -2;// 正数超过最大值变成负数，报错
		} else { // 其他字符
			sp = 0;
			word[sp++] = ch;
			switch (ch) {
			case '+':
				ch = input[p++];
				if (ch == '=') {// +=
					code = 23;
					word[sp++] = ch;
				} else {// +
					code = 22;
					p--;
				}
				break;
			case '-':
				ch = input[p++];
				if (ch >= '0' && ch <= '9') { // 可能是负常数
					num = 0;
					while (ch >= '0' && ch <= '9') {
						num = num * 10 + ch - '0';
						ch = input[p++];
					}
					p--;
					code = 57;
					if (num < 0)
						code = -2;
					num *= -1;// 变成负数
				} else if (ch == '=') {// -=
					code = 25;
					word[sp++] = ch;
				} else {// -
					code = 24;
					p--;
				}
				break;
			case '*':
				ch = input[p++];
				if (ch == '=') {// *=
					code = 27;
					word[sp++] = ch;
				} else if (ch == '/') {// */
					code = 44;
					word[sp++] = ch;
				} else {// *
					code = 26;
					p--;
				}
				break;
			case '/':
				ch = input[p++];
				if (ch == '=') {// /=
					code = 29;
					word[sp++] = ch;
				} else if (ch == '/') {// //
					code = 42;
					word[sp++] = ch;
				} else if (ch == '*') {// /*
					code = 26;
					word[sp++] = ch;
				} else {// /
					code = 28;
					p--;
				}
				break;
			case '=':
				ch = input[p++];
				if (ch == '=') { // ==
					code = 31;
					word[sp++] = ch;
				} else { // =
					code = 30;
					p--;
				}
				break;
			case '<':
				ch = input[p++];
				if (ch == '=') { // <=
					code = 39;
					word[sp++] = ch;
				} else { // <
					code = 38;
					p--;
				}
				break;
			case '>':
				ch = input[p++];
				if (ch == '=') { // >=
					code = 41;
					word[sp++] = ch;
				} else { // >
					code = 40;
					p--;
				}
				break;
			case '|':
				ch = input[p++];
				if (ch == '|') { // ||
					code = 35;
					word[sp++] = ch;
				} else { // |
					code = 34;
					p--;
				}
				break;
			case '!':
				ch = input[p++];
				if (ch == '=') { // !=
					code = 37;
					word[sp++] = ch;
				} else { // !
					code = 36;
					p--;
				}
				break;

			case '(':code = 45; break;
			case ')':code = 46; break;
			case '{':code = 49; break;
			case '}':code = 50; break;
			case ',':code = 51; break;
			case ':':code = 52; break;
			case ';':code = 53; break;
			case '\'':code = 54; break;
			case '\"':code = 55; break;
			case '\n':code = -1; break;
			default:code = -3; break;
			}
		}
	}



	public ArrayList<Token> getTokens(char[] inputCharacter){
		input = inputCharacter;
		p = 0;
		row = 1;
		do {
			scanner();
			switch (code) {
			case 57: // 常数
				tokens.add(new Token(code, num+""));
				break;
			case -1: // 换行
				row++;
				break;
			case -2: // 整型过大
				tokens.add(new Token("integer overflow at row "+row));
				break;
			case -3: // 未定义字符
				tokens.add(new Token("undefined character at row "+row));
				break;
			default:// 一般单词符号
				tokens.add(new Token(code,infoHandle.ch2String(word)));
				break;
			}
		} while (input[p] != '$');
		tokens.add(new Token(Token.END,"$"));
		return tokens;
	}

}
