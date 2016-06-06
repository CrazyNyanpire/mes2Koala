package help;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yuxiang Que
 * @version 2015/12/3
 */
public class FilenameHelper {


    /**
     * 从文件路径中filePath提取文件名fileName
     *
     * @param filePath
     * @return
     */
    public static String extractFileNamByFilePath(String filePath) {
        String fileName = filePath;
        if ((fileName != null) && (fileName.length() > 0)) {
            int dir = fileName.lastIndexOf("\\");
            fileName = fileName.substring(dir + 1);
            dir = fileName.lastIndexOf("/");
            fileName = fileName.substring(dir + 1);
        }
        return fileName;
    }

    /**
     * 从文件名fileName获取不带扩展名的文件名
     *
     * @param fileName
     * @return
     */
    public static String extractFilenameNoExtensionByFileName(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    /**
     * 从文件名fileName获取扩展名
     *
     * @param fileName
     * @return
     */
    public static String extractExtensionByFileName(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }

    public static String generateXlsxFilename(String prefix) {
        return generateDatetimeBasedFilename(prefix, ".xlsx");
    }

    public static String generateXlsFilename(String prefix) {
        return generateDatetimeBasedFilename(prefix, ".xls");
    }

    public static String generateDatetimeBasedFilename(String prefix, String suffix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String now = sdf.format(new Date());
        return prefix + "_" + now + suffix;
    }


    /**
     * 替换掉文件名的后缀，但是保持文件名不变
     *
     * @param filename 原文件名
     * @param newEx    新的后缀名
     * @return
     */
    public static String replaceFilenameWithEx(String filename, String newEx) {
        return extractFilenameNoExtensionByFileName(filename) + newEx;
    }


    /**
     * 读取文件内容
     *
     * @param inputStream
     * @return
     * @throws UnsupportedEncodingException
     */

    public static String readFileToString(InputStream inputStream) {
        BufferedReader bufferedReader = null;
        StringBuffer fileString = new StringBuffer();
        String readLine = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while ((readLine = bufferedReader.readLine()) != null) {
                fileString.append(readLine);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return fileString.toString();
    }

}
