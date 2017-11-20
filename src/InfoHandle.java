import java.io.*;
import java.util.ArrayList;

/**
 * 获取数据 对数据进行处理 输出等方法
 */
public class InfoHandle {

    /**
     * 输入程序 读取写入的txt文件 获得字符流 返回字符数组
     * @throws IOException
     */
    public ArrayList<Character> getInput() throws IOException {
        ArrayList<Character> inputCharacter = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("input.txt"))));
        String line = null;
        char[] tmp = null;
        while ((line = br.readLine()) != null) {
            tmp = line.toCharArray();
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i] == ' '|| tmp[i]=='\t')
                    continue;
                inputCharacter.add(tmp[i]);
            }
            inputCharacter.add('\n');
        }
        inputCharacter.add('$');
        br.close();
        return inputCharacter;
    }

    /**
     *
     * @param c
     * @return
     */
/*    public String ch2String(char[] c) {
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != '\0')
                len++;
        }
        return String.valueOf(c).substring(0, len);
    }*/


    /**
     * 输出匹配的所有表达式
     * @param output
     */
    public void output(ArrayList<String> output){
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
            e.printStackTrace();
        }
    }
}
