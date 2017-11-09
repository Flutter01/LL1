import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LL1parse {
    private static ArrayList<Token> tokens;
    private static ArrayList<String> output = new ArrayList<>();
    private static Stack<Token> stack;
    private static String[] generations = {
/**
 * 用来判断一个函数的文法 可以匹配的范围是声明函数，if else while循环 定义变量 *+运算 函数返回值只能是一个标识符
*/
          //  "Program->FuncBlock", //可以去掉？？？0
            "FuncBlock->void FuncName(Paras){S}",//1
            "FuncBlock->DataType FuncName(Paras){SR}",//2
            "DataType->int",//3
            "DataType->double",//4
            "DataType->char",//5
            "DataType->String",//6
            "FuncName->id",//7
            "Paras->Para Paras",//8
            "Paras->,Para",//9
            "Paras->ε",//10
            "Para->DataType id",//11
            "S->DataType Exp",//12
            "S->if(C){S}else{S}",//13
            "S->while(C){S}",//14
            "Exp->id=E;",//15
            "E->TE'",//16
            "E'->+TE'",//17
            "E'->ε",//18
            "T->FT'",//19
            "T'->*FT'",//20
            "T'->ε",//21
            "F->(E)",//22
            "F->num",//23
            "F->id",///24
            "C->F Op F",//25
            "Op->>",//26
            "Op->>=",//27
            "Op-><=",//28
            "Op-><",//29
            "Op->==",//30
            "Op->!=",//31
            "R->return F;"//32
    };

    private static int[][] ppt = {
        // void int double char String id   (    )   ,    {   } return   ;   +   *   <   >  <=  >=  ==  !=  num if while =  else   $
          //  {0,  0,   0,    0,    0,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},//Program
            {1,  2,   2,    2,    2,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//FuncBlock
            {-1, 3,   4,    5,    6,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//DataType
            {-1, -1,  -1,   -1,   -1,   7,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//FuncName
            {-1, 8,   8,    8,    8,   -1,  -1, 10, 9,   -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Paras
            {-1, 11,  11,   11,   11,  -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Para
            {-1, 12,  12,   12,   12,  -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 14, -1, -1, -1 },//S
            {-1, -1,  -1,   -1,   -1,  15,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Exp
            {-1, -1,  -1,   -1,   -1,  16,  16, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1 },//E
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   18, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//E'
            {-1, -1,  -1,   -1,   -1,  19,  19, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1 },//T
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   21, 21, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//T'
            {-1, -1,  -1,   -1,   -1,  24,  22, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, -1, -1, -1 },//F
            {-1, -1,  -1,   -1,   -1,  25,  25, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1 },//C
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, 29, 26, 28, 27, 30, 31, -1, -1, -1, -1, -1, -1 },//Op
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  32,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 } //R

    };

    public void output(){
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

    public void parse(){
        Token t1 = null;
        Token t2 = null;
        stack = new Stack<Token>();
        stack.push(new Token(Token.FuncBlock, "FuncBlock"));
        while(tokens.get(0).getCode()!= Token.END){
            t1 = stack.peek();
            t2 = tokens.get(0);
            System.out.println(t1.getCode()+"@@@"+t2.getCode());
            if(t1.getCode()>99){//非终结符
                if(!generate(t1, t2.getCode())){
                    System.out.print("Error1!");
                    return;
                }
            }
            else{//终结符
                if(t1.getCode()==t2.getCode()){//匹配成功
                    stack.pop();
                    tokens.remove(0);
                }
                else{//否则报错
                    System.out.println("Error2!");
                    return;
                }
            }
        }
        System.out.println("Done!");
    }
    private static boolean pushProduce(int code) {
        PPT table = new PPT();
        if(table.getPPTCell(code)==null) {
            System.out.println("Error4!");
            return false;
        }
        if(table.getPPTCell(code).size()>0) {
            for(Token t:table.getPPTCell(code)){
                stack.push(t);
            }
        }
        return true;
    }
    private static boolean generate(Token currentToken, int next){
        PPT p = new PPT();
        try {
            int shift = ppt[currentToken.getCode()-100][p.getNonTerminal(next)];//查表 3
            if(shift<0){
                System.out.println("未定义该种表达式");
                return false;
            }
            output.add(generations[shift-1]);//产生式添加到输出队列
            stack.pop();
            return pushProduce(shift);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error5!");
            return false;
        }
    }
    public void setTokens(ArrayList<Token> tokenList) {
        tokens = tokenList;
    }

}
