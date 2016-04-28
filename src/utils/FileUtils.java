package utils;

import java.io.*;

public class FileUtils {


    /**
     * 向文件追加内容
     * @param fileName 文件名
     * @param content 要追加的内容
     */
    public static void writeFileAppend(String fileName, String content) {
        try {

            // 打开一个随机访问文件流，按读写方式

            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");

            // 文件长度，字节数

            long fileLength = randomFile.length();
            
            //将写文件指针移到文件尾

            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
