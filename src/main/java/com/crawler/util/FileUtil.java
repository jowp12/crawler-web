package com.crawler.util;

import java.io.*;

public class FileUtil {


    public static final String CONTENT_PATH = "/www/crawler/content/";


    public static void writeFile(String filePath, String content) throws IOException {
        File file = validFile(filePath);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file));
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static void writeFile(String filePath, InputStream inputStream) throws IOException {
        File file = validFile(filePath);

        if(inputStream != null){
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int len;
            while((len = inputStream.read(buffer) )!= -1){
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        }

    }

    private static File validFile(String path){
        File file = new File(path);
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static String readFile(File file) {
        if (!file.exists()) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                result.append("\n");
                result.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return result.toString().replaceFirst("\n", "");
    }
}
