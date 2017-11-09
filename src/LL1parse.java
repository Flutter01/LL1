import java.util.*;

public class LL1parse {
    private static ArrayList<Token> tokens;
    private static ArrayList<String> output = new ArrayList<>();
    private static Stack<Token> stack;
    public LL1parse(ArrayList<Token> tokenList) {
        this.tokens = tokenList;
    }
    private static String[] generations = {
/**
 * 用来判断一个函数的文法 可以匹配的范围是声明函数，if else while循环 定义变量 *+运算 函数返回值只能是一个标识符
*/
          //  "Program->FuncBlock", //可以去掉
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
            "S->Exp",//15
            "Exp->id=E;",//16
            "E->TE'",//17
            "E'->+TE'",//18
            "E'->ε",//19
            "T->FT'",//20
            "T'->*FT'",//21
            "T'->ε",//22
            "F->(E)",//23
            "F->num",//24
            "F->id",///25
            "C->F Op F",//26
            "Op->>",//27
            "Op->>=",//28
            "Op-><=",//29
            "Op-><",//30
            "Op->==",//31
            "Op->!=",//32
            "R->return F;"//33
    };

    private static int[][] ppt = {
        // void int double char String id   (    )   ,    {   } return   ;   +   *   <   >  <=  >=  ==  !=  num if while =  else   $
          //  {0,  0,   0,    0,    0,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},//Program
            {1,  2,   2,    2,    2,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//FuncBlock
            {-1, 3,   4,    5,    6,   -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//DataType
            {-1, -1,  -1,   -1,   -1,   7,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//FuncName
            {-1, 8,   8,    8,    8,   -1,  -1, 10, 9,   -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Paras
            {-1, 11,  11,   11,   11,  -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Para
            {-1, 12,  12,   12,   12,  15,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 14, -1, -1, -1 },//S
            {-1, -1,  -1,   -1,   -1,  16,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//Exp
            {-1, -1,  -1,   -1,   -1,  17,  17, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1 },//E
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   19, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//E'
            {-1, -1,  -1,   -1,   -1,  20,  20, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1 },//T
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   22, 22, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },//T'
            {-1, -1,  -1,   -1,   -1,  25,  22, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 24, -1, -1, -1, -1, -1 },//F
            {-1, -1,  -1,   -1,   -1,  26,  26, -1, -1,  -1, -1,  -1,   -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, -1, -1, -1, -1, -1 },//C
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  -1,   -1, -1, -1, 30, 27, 29, 28, 31, 32, -1, -1, -1, -1, -1, -1 },//Op
            {-1, -1,  -1,   -1,   -1,  -1,  -1, -1, -1,  -1, -1,  33,   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 } //R

    };


    private void parse(){
        Token stackTop = null; //栈顶对应的词法单元
        Token readHead ;//读头指向的词法单元
        stack = new Stack<Token>();
        stack.push(new Token(Token.FuncBlock, "FuncBlock")); //开始符压栈
        while(tokens.get(0).getCode()!= Token.END){
            stackTop = stack.peek();
            readHead = tokens.get(0);
            if(stackTop.getCode()>99){  //如果当前栈顶是非终结符
                if(!shift(stackTop, readHead.getCode())){  //通过ppt寻找对应表达式替换该终结符
                    System.out.print("没有对应文法或输入错误词法单元");
                    return;
                }
            }
            else{//终结符
                if(stackTop.getCode()==readHead.getCode()){//匹配成功
                    stack.pop();
                    tokens.remove(0);
                }
                else{//否则报错
                    System.out.println("词法错误，终结符不匹配"+stackTop.getCode()+"!!!"+readHead.toString());
                    return;
                }
            }
        }
        System.out.println("语法分析完成！！！");
    }
    private static boolean pushProduce(int code) {
        PPT table = new PPT();
        if(table.getPPTCell(code)==null) {
            return false;
        }
        if(table.getPPTCell(code).size()>0) {  //如果产生式右部是ε 弹出后不压栈
            for(Token t:table.getPPTCell(code)){
                stack.push(t);
            }
        }
        return true;
    }
    private static boolean shift(Token currentToken, int terminalCode){  //根据PPT寻找非终结符的替换表达式
        PPT p = new PPT();
        try {
            int shift = ppt[currentToken.getCode()-100][p.getRowNo(terminalCode)];
            if(shift<0){
                System.out.println("未定义该种表达式");
                return false;
            }
            output.add(generations[shift-1]);//产生式添加到输出队列
            stack.pop();//弹出栈顶非终结符
            return pushProduce(shift);
        } catch (Exception e) {
            System.out.println("Error5!");
            return false;
        }
    }


    public ArrayList<String> getOutput() {
        parse();
        return output;
    }

}
