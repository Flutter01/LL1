import java.util.ArrayList;

/**
 * Created by asus1 on 2017/11/9.
 */
public class main {
    public static void main(String[] args)throws Exception {
        InfoHandle infoHandle = new InfoHandle();
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokens = analyzer.getTokens(infoHandle.getInput());
        LL1parse ll1parse = new LL1parse(tokens);
        infoHandle.output(ll1parse.getOutput());
    }
}
