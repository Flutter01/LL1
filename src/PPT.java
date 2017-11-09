import java.util.*;

/**
 * Created by asus1 on 2017/11/9.
 */
public class PPT {
    /**
     * 保存产生式右部 以从右向左方向保存 用于压栈
     */
    private Map<Integer, ArrayList<Token>> PPT = new HashMap<>();
    public PPT (){

        ArrayList<Token> production1 = new ArrayList<>();
        production1.add(new Token(Token.RIGHT_BRACE,"}"));
        production1.add(new Token(Token.S,"S"));
        production1.add(new Token(Token.LEFT_BRACE,"{"));
        production1.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production1.add(new Token(Token.Paras,"Paras"));
        production1.add(new Token(Token.LEFT_PARENTHESE,"("));
        production1.add(new Token(Token.FuncName,"FuncName"));
        production1.add(new Token(Token.VOID,"void"));
        PPT.put(1,production1);

        ArrayList<Token> production2 = new ArrayList<>();
        production2.add(new Token(Token.RIGHT_BRACE,"}"));
        production2.add(new Token(Token.R,"R"));
        production2.add(new Token(Token.S,"S"));
        production2.add(new Token(Token.LEFT_BRACE,"{"));
        production2.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production2.add(new Token(Token.Paras,"Paras"));
        production2.add(new Token(Token.LEFT_PARENTHESE,"("));
        production2.add(new Token(Token.FuncName,"FuncName"));
        production2.add(new Token(Token.DataType,"DataType"));
        PPT.put(2,production2);

        ArrayList<Token> production3 = new ArrayList<>();
        production3.add(new Token(Token.INT,"int"));
        PPT.put(3,production3);

        ArrayList<Token> production4 = new ArrayList<>();
        production4.add(new Token(Token.DOUBLE,"double"));
        PPT.put(4,production4);

        ArrayList<Token> production5 = new ArrayList<>();
        production5.add(new Token(Token.CHAR,"char"));
        PPT.put(5,production5);

        ArrayList<Token> production6 = new ArrayList<>();
        production6.add(new Token(Token.STRING,"String"));
        PPT.put(6,production6);

        ArrayList<Token> production7 = new ArrayList<>();
        production7.add(new Token(Token.ID,"id"));
        PPT.put(7,production7);

        ArrayList<Token> production8 = new ArrayList<>();
        production8.add(new Token(Token.Paras,"Paras"));
        production8.add(new Token(Token.Para,"Para"));
        PPT.put(8,production8);

        ArrayList<Token> production9 = new ArrayList<>();
        production9.add(new Token(Token.Para,"Para"));
        production9.add(new Token(Token.COMMA,","));
        PPT.put(9,production9);

        ArrayList<Token> production10 = new ArrayList<>();
        PPT.put(10,production10);

        ArrayList<Token> production11 = new ArrayList<>();
        production11.add(new Token(Token.ID,"id"));
        production11.add(new Token(Token.DataType,"DataType"));
        PPT.put(11,production11);

        ArrayList<Token> production12 = new ArrayList<>();
        production12.add(new Token(Token.Exp,"Exp"));
        production12.add(new Token(Token.DataType,"DataType"));
        PPT.put(12,production12);

        ArrayList<Token> production13 = new ArrayList<>();
        production13.add(new Token(Token.RIGHT_BRACE,"}"));
        production13.add(new Token(Token.S,"S"));
        production13.add(new Token(Token.LEFT_BRACE,"{"));
        production13.add(new Token(Token.ELSE, "else"));
        production13.add(new Token(Token.RIGHT_BRACE,"}"));
        production13.add(new Token(Token.S,"S"));
        production13.add(new Token(Token.LEFT_BRACE,"{"));
        production13.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production13.add(new Token(Token.C,"C"));
        production13.add(new Token(Token.LEFT_PARENTHESE,"("));
        production13.add(new Token(Token.IF,"if"));
        PPT.put(13,production13);

        ArrayList<Token> production14 = new ArrayList<>();
        production14.add(new Token(Token.RIGHT_BRACE,"}"));
        production14.add(new Token(Token.S,"S"));
        production14.add(new Token(Token.LEFT_BRACE,"{"));
        production14.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production14.add(new Token(Token.C,"C"));
        production14.add(new Token(Token.LEFT_PARENTHESE,"("));
        production14.add(new Token(Token.WHILE,"while"));
        PPT.put(14,production14);

        ArrayList<Token> production15 = new ArrayList<>();
        production15.add(new Token(Token.Exp,"Exp"));
        PPT.put(15,production15);

        ArrayList<Token> production16 = new ArrayList<>();
        production16.add(new Token(Token.SEMICOLON,";"));
        production16.add(new Token(Token.E,"E"));
        production16.add(new Token(Token.EQUAL,"="));
        production16.add(new Token(Token.ID,"id"));
        PPT.put(16,production16);

        ArrayList<Token> production17 = new ArrayList<>();
        production17.add(new Token(Token.E1,"E'"));
        production17.add(new Token(Token.T,"T"));
        PPT.put(17,production17);

        ArrayList<Token> production18= new ArrayList<>();
        production18.add(new Token(Token.E1,"E'"));
        production18.add(new Token(Token.T,"T"));
        production18.add(new Token(Token.ADD,"+"));
        PPT.put(18,production18);

        ArrayList<Token> production19 = new ArrayList<>();
        PPT.put(19,production19);

        ArrayList<Token> production20 = new ArrayList<>();
        production20.add(new Token(Token.T1,"T''"));
        production20.add(new Token(Token.F,"F"));
        PPT.put(20,production20);

        ArrayList<Token> production21 = new ArrayList<>();
        production21.add(new Token(Token.T1,"T''"));
        production21.add(new Token(Token.F,"F"));
        production21.add(new Token(Token.MUL,"*"));
        PPT.put(21,production21);

        ArrayList<Token> production22 = new ArrayList<>();
        PPT.put(22,production22);

        ArrayList<Token> production23 = new ArrayList<>();
        production23.add(new Token(Token.RIGHT_PARENTHESE,")"));
        production23.add(new Token(Token.E,"E"));
        production23.add(new Token(Token.LEFT_PARENTHESE,"("));
        PPT.put(23,production23);

        ArrayList<Token> production24 = new ArrayList<>();
        production24.add(new Token(Token.NUMBER,"num"));
        PPT.put(24,production24);

        ArrayList<Token> production25 = new ArrayList<>();
        production25.add(new Token(Token.ID,"id"));
        PPT.put(25,production25);

        ArrayList<Token> production26 = new ArrayList<>();
        production26.add(new Token(Token.F,"F'"));
        production26.add(new Token(Token.Op,"OP"));
        production26.add(new Token(Token.F,"F"));
        PPT.put(26,production26);

        ArrayList<Token> production27 = new ArrayList<>();
        production27.add(new Token(Token.GREATER,">"));
        PPT.put(27,production27);

        ArrayList<Token> production28 = new ArrayList<>();
        production28.add(new Token(Token.GREATER_EQUAL,">="));
        PPT.put(28,production28);

        ArrayList<Token> production29 = new ArrayList<>();
        production29.add(new Token(Token.LESS_EQUAL,"<="));
        PPT.put(29,production29);

        ArrayList<Token> production30 = new ArrayList<>();
        production30.add(new Token(Token.LESS,"<"));
        PPT.put(30,production30);

        ArrayList<Token> production31 = new ArrayList<>();
        production31.add(new Token(Token.DOUBLE_EQUAL,"=="));
        PPT.put(31,production31);

        ArrayList<Token> production32 = new ArrayList<>();
        production32.add(new Token(Token.NOT_EQUAL,"!="));
        PPT.put(32,production32);

        ArrayList<Token> production33 = new ArrayList<>();
        production33.add(new Token(Token.SEMICOLON,";"));
        production33.add(new Token(Token.F,"F"));
        production33.add(new Token(Token.RETURN,"return"));
        PPT.put(33,production33);

    }

    public ArrayList<Token> getPPTCell(int No) {
        return this.PPT.get(No);
    }

    /**
     *     根据输入流读头所读非终结符Token的种别码 返回对应列号
     */
    public int getRowNo(int code){
        switch (code) {
            // void int double char String id   (    )   ,    {   } return   ;   +   *   <   >  <=  >=  ==  !=  num if while =  else   $
            case 5: return 0;
            case 7: return 1;
            case 10: return 2;
            case 8: return 3;
            case 11: return 4;
            case 56: return 5;
            case 45: return 6;
            case 46: return 7;
            case 51: return 8;
            case 49: return 9;
            case 50: return 10;
            case 14: return 11;
            case 53: return 12;
            case 22: return 13;
            case 26: return 14;
            case 38: return 15;
            case 40: return 16;
            case 39: return 17;
            case 41: return 18;
            case 31: return 19;
            case 37: return 20;
            case 57: return 21;
            case 12: return 22;
            case 15: return 23;
            case 30: return 24;
            case 13: return 25;
            case 0: return 26;
            default: return -1;
        }
    }
}
