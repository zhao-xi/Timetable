package evostar;

import java.io.*;

public class Log {
    private String file = "src/log";
    private FileWriter fw = null;
    private PrintWriter pw = null;
    public void wrirteIn(String content) {
        try {
            File f=new File(this.file);
            fw = new FileWriter(f, true);
        } catch (Exception e) {
            System.out.println();
        }
        pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
    }

    //检查文件是否存在，不存在创建
    private void checkFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
