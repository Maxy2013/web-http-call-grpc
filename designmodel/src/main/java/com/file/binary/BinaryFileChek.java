package com.file.binary;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author liuzheng
 * 2019/1/17 9:47
 */
public class BinaryFileChek {

    public static boolean isBinary(File file) {
        boolean isBinary = false;
        try {
            FileInputStream fin = new FileInputStream(file);
            long len = file.length();
            for (int j = 0; j < (int) len; j++) {
                int t = fin.read();
                if (t < 32 && t != 9 && t != 10 && t != 13) {
                    isBinary = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isBinary;
    }

    public static void main(String[] args) {
        String path = "D:/safemon/";
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f : files){
            boolean binary = isBinary(f);
            System.out.println(f.getName() + "---->>>>" + binary);
        }

    }
}
