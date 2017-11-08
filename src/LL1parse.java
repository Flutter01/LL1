import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LL1parse {
    private static ArrayList<Token> tokens;
    private static ArrayList<String> output = new ArrayList<>();
    private static Queue queue;
    private static Stack stack;
    private static String[] generations = {
/*
            "S->id=E;",
            "S->if(C){S}else{S}",
            "S->while(C){S}",
            "E->TE'",
            "E'->+TE'",
            "E'->ε",
            "T->FT'",
            "T'->*FT'",
            "T'->ε",
            "F->(E)",
            "F->num",
            "F->id",
            "C->DC'",
            "C'->||DC'",
            "C'->ε",
            "D->(C)",
            "D->id==num"

*/
/**
 * 用来判断一个函数的文法 可以匹配的范围是声明函数，if else while循环 定义变量 *+运算
*/
            "Program->FuncBlock", //可以去掉？？？
            "FuncBlock->void FuncName(Paras){S}",
            "FuncBlock->DataType FuncName(Paras){SR}",
            "DataType->int",
            "DataType->double",
            "DataType->char",
            "DataType->String",
            "FuncName->id",
            "Paras->Para Paras",
            "Paras->,Para",
            "Paras->ε",
            "Para->DataType id",
            "S->DataType Exp",
            "S->if{S}else{S}",
            "S->while(C){S}",
            "Exp->id=E;",
            "E->TE'",
            "E'->+TE'",
            "E'->ε",
            "T->FT'",
            "T'->*FT'",
            "T'->ε",
            "F->(E)",
            "F->num",
            "F->id",
            "C->F Op F",
            "Op->>",
            "Op->>=",
            "Op-><=",
            "Op-><",
            "Op->==",
            "Op->!=",
            "R->return F"
    };
    private static int[][] table = {
            //   id	=	;	if	(	)	{	}	e	w	+	*	n	||	==	$
            {0,	-1,	-1,	1,	-1,	-1,	-1,	-1,	-1,	2,	-1,	-1,	-1,	-1,	-1,	-1},//S
            {3,	-1,	-1,	-1,	3,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	3,	-1,	-1,	-1},//E
            {-1,-1,	5,	-1,	-1,	5,	-1,	-1,	-1,	-1,	4,	-1,	-1,	-1,	-1,	5},//E'
            {6,	-1,	-1,	-1,	6,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	6,	-1,	-1,	-1},//T
            {-1,-1,	8,	-1,	-1,	8,	-1,	-1,	-1,	-1,	8,	7,	-1,	-1,	-1,	8},//T'
            {11,-1,	-1,	-1,	9,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	10,	-1,	-1,	-1},//F
            {12,-1,	-1,	-1,	12,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1},//C
            {-1,-1,	-1,	-1,	-1,	14,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	13,	-1,	14},//C'
            {16,-1,	-1,	-1,	15,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1}//D
    };

    private static void output(){
        try {
            File outputFile = new File("output.txt");
            if (!outputFile.exists())
                outputFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
            for(String s:output){
                System.out.println(s);
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void parse(){
        Token t1 = null;
        Token t2 = null;

        while(queue.get().getCode()!= Token.END){
            t1 = stack.get();
            t2 = queue.get();
            if(t1.getCode()>99){//非终结符
                if(!generate(t1, t2.getCode())){
                    System.out.println("Error1!");
                    return;
                }
            }
            else{//终结符
                if(t1.getCode()==t2.getCode()){//匹配成功
                    stack.pop();
                    queue.dequeue();
                }
                else{//否则报错
                    System.out.println("Error2!");
                    return;
                }
            }
        }
        System.out.println("Done!");
    }

    private static boolean generate(Token nts, int next){
        try {
            int gi = table[nts.getCode()-100][getHeadIndex(next)];//查表
            if(gi<0){
                System.out.println("Error3!");
                return false;
            }
            output.add(generations[gi]);//产生式添加到输出队列
            stack.pop();
            switch (gi) {
                case 0:
                    stack.push(new Token(Token.SEMICOLON,";"));
                    stack.push(new Token(Token.E, "E"));
                    stack.push(new Token(Token.EQUAL, "="));
                    stack.push(new Token(Token.ID, "id"));
                    break;
                case 1:
                    stack.push(new Token(Token.RIGHT_BRACE,"}"));
                    stack.push(new Token(Token.S,"S"));
                    stack.push(new Token(Token.LEFT_BRACE,"{"));
                    stack.push(new Token(Token.ELSE, "else"));
                    stack.push(new Token(Token.RIGHT_BRACE,"}"));
                    stack.push(new Token(Token.S,"S"));
                    stack.push(new Token(Token.LEFT_BRACE,"{"));
                    stack.push(new Token(Token.RIGHT_PARENTHESE,")"));
                    stack.push(new Token(Token.C,"C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE,"("));
                    stack.push(new Token(Token.IF,"if"));
                    break;
                case 2:
                    stack.push(new Token(Token.RIGHT_BRACE,"}"));
                    stack.push(new Token(Token.S,"S"));
                    stack.push(new Token(Token.LEFT_BRACE,"{"));
                    stack.push(new Token(Token.RIGHT_PARENTHESE,")"));
                    stack.push(new Token(Token.C,"C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE,"("));
                    stack.push(new Token(Token.WHILE,"while"));
                    break;
                case 3:
                    stack.push(new Token(Token.E1,"E'"));
                    stack.push(new Token(Token.T,"T"));
                    break;
                case 4:
                    stack.push(new Token(Token.E1,"E'"));
                    stack.push(new Token(Token.T,"T"));
                    stack.push(new Token(Token.ADD,"+"));
                    break;
                case 5:
                    break;
                case 6:
                    stack.push(new Token(Token.T1,"T'"));
                    stack.push(new Token(Token.F,"F"));
                    break;
                case 7:
                    stack.push(new Token(Token.T1,"T'"));
                    stack.push(new Token(Token.F,"F"));
                    stack.push(new Token(Token.MUL,"*"));
                    break;
                case 8:
                    break;
                case 9:
                    stack.push(new Token(Token.RIGHT_PARENTHESE,")"));
                    stack.push(new Token(Token.E,"E"));
                    stack.push(new Token(Token.LEFT_PARENTHESE,"("));
                    break;
                case 10:
                    stack.push(new Token(Token.NUMBER,"num"));
                    break;
                case 11:
                    stack.push(new Token(Token.ID,"id"));
                    break;
                case 12:
                    stack.push(new Token(Token.C1,"C'"));
                    stack.push(new Token(Token.D,"D"));
                    break;
                case 13:
                    stack.push(new Token(Token.C1,"C'"));
                    stack.push(new Token(Token.D,"D"));
                    stack.push(new Token(Token.DOUBLE_OR,"||"));
                    break;
                case 14:
                    break;
                case 15:
                    stack.push(new Token(Token.RIGHT_PARENTHESE,")"));
                    stack.push(new Token(Token.C,"C"));
                    stack.push(new Token(Token.LEFT_PARENTHESE,"("));
                    break;
                case 16:
                    stack.push(new Token(Token.NUMBER,"num"));
                    stack.push(new Token(Token.DOUBLE_EQUAL,"=="));
                    stack.push(new Token(Token.ID,"id"));
                    break;
                default:
                    System.out.println("Error4!");
                    return false;
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error5!");
            return false;
        }
    }
}
