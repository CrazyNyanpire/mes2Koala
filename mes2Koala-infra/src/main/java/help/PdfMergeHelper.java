package help;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pdf 合并工具类
 *
 * @author Yuxiang Que
 * @version 2015/12/13
 */
public class PdfMergeHelper {

    /**
     * Merge excel files
     *
     * @param inputStream
     * @param outputStream
     */
    public static void mergePdfFiles(List<InputStream> inputStream, OutputStream outputStream) {
        try {
            PDFMergerUtility ut = new PDFMergerUtility();
            for (InputStream inputFilePath : inputStream) {
                ut.addSource(inputFilePath);
            }
            ut.setDestinationStream(outputStream);
            ut.mergeDocuments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }

    /**
     * Merge excel files
     *
     * @param inputFilePaths
     * @param outputFilePath
     */
    public static void mergePdfFiles(List<String> inputFilePaths, String outputFilePath) {
        try {
            PDFMergerUtility ut = new PDFMergerUtility();
            for (String inputFilePath : inputFilePaths) {
                InputStream inputStream = new FileInputStream(inputFilePath);
                ut.addSource(inputStream);
            }
            ut.setDestinationFileName(outputFilePath);
            ut.mergeDocuments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputFilePath1 = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\BGA-模板.pdf";
        String inputFilePath2 = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\FT-模板.pdf";
        String outputFilePath = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\BGA-模板_FT-模板_合并.pdf";
        try {
            List<InputStream> inputStreams = new ArrayList<InputStream>();
            inputStreams.add(new FileInputStream(inputFilePath1));
            inputStreams.add(new FileInputStream(inputFilePath2));
            OutputStream outputStream = new FileOutputStream(outputFilePath);
            mergePdfFiles(inputStreams, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
