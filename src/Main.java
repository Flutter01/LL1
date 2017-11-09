import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by asus1 on 2017/11/9.
 */
public class main {
    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList = analyzer.getTokens();
        tokenList.add(new Token(Token.END,"$"));
        LL1parse ll1parse = new LL1parse();
        ll1parse.setTokens(tokenList);
        for(Token t : tokenList) {
            System.out.println(t.toString());
        }
        ll1parse.parse();
        ll1parse.output();
    }
}
