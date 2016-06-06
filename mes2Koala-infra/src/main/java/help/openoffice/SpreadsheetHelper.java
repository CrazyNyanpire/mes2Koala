package help.openoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.container.XIndexAccess;
import com.sun.star.document.XStorageBasedDocument;
import com.sun.star.embed.ElementModes;
import com.sun.star.embed.XStorage;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.*;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.script.provider.XScript;
import com.sun.star.script.provider.XScriptProvider;
import com.sun.star.script.provider.XScriptProviderSupplier;
import com.sun.star.sdbc.SQLException;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.*;
import com.sun.star.text.XText;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XMergeable;

import java.io.File;

/**
 * Excel 转换 Pdf 工具类
 * 需要安装
 * - Apache OpenOffice  http://www.openoffice.org/download/index.html
 * - Apache OpenOffice SDK  http://openoffice.apache.org/downloads.html
 * - 以及需要在项目中添加一些jar，在IJ中使用File-Project Structure-Modules-Dependencies内添加，Eclipse类似
 * 这些文件目前存放在tower的lib目录 lib.zip
 * 具体参考 http://blog.zhaojie.me/2010/05/convert-document-to-pdf-via-openoffice.html
 *
 * @author Yuxiang Que
 * @version 2015/12/13
 */
public class SpreadsheetHelper {

    /**
     * 新建XSpreadsheetDocument
     *
     * @param loader XComponentLoader
     * @return XSpreadsheetDocument
     */
    public static XSpreadsheetDocument newSpreadsheetDocument(XComponentLoader loader) {
        try {
            PropertyValue[] propertyValue = new PropertyValue[0];
            String strDoc = "private:factory/scalc";
            XComponent component = loader.loadComponentFromURL(strDoc, "_blank", 0, propertyValue);
            return UnoRuntime.queryInterface(XSpreadsheetDocument.class, component);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 打开XSpreadsheetDocument文档
     *
     * @param loader        XComponentLoader
     * @param inputFilePath String
     * @return XComponent
     */
    static XSpreadsheetDocument openSpreadsheetDocument(XComponentLoader loader, String inputFilePath) throws IOException, IllegalArgumentException {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.Name = "Hidden";
        propertyValue.Value = true;
        File inputFile = new File(inputFilePath);
        String inputUrl = "file:///" + inputFile.getAbsolutePath().replace('\\', '/');
        XComponent component = loader.loadComponentFromURL(inputUrl, "_blank", 0, new PropertyValue[]{propertyValue});
        return UnoRuntime.queryInterface(XSpreadsheetDocument.class, component);
    }

    /**
     * @param inputFilePath  String
     * @param outputFilePath String
     * @throws BootstrapException
     * @throws IOException
     */
    public static void toPdf(String inputFilePath, String outputFilePath) throws BootstrapException, IOException {
        // Getting the given type to convert to
        String convertType = "calc_pdf_Export"; //writer_pdf_Export
        try {
            XComponentContext context = OpenOfficeHelper.createContext();
            System.out.println("createContext");

            XComponentLoader loader = OpenOfficeHelper.createComponentLoader(context);
            System.out.println("createComponentLoader");

            XComponent document = OpenOfficeHelper.openDocumentComponent(loader, inputFilePath);
            System.out.println("openAnyDocument");

            OpenOfficeHelper.convertDocument(document, outputFilePath, convertType);
            System.out.println("convertDocument");

            OpenOfficeHelper.closeDocument(document);
            System.out.println("closeDocument");

        } catch (BootstrapException e) {  // 未找到OpenOffice
            throw e;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {  // 关闭文件后再试
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过Sheet名称取得Sheet对象
     *
     * @param xSpreadsheetDocument Calc文件对象
     * @param sheetName            Sheet名称
     * @return Sheet对象
     */
    public static XSpreadsheet getXSpreadsheetByName(XSpreadsheetDocument xSpreadsheetDocument, String sheetName) throws Exception {
        XSpreadsheets sheets = xSpreadsheetDocument.getSheets();
        Object sheet = sheets.getByName(sheetName);
        return UnoRuntime.queryInterface(XSpreadsheet.class, sheet);
    }


    /**
     * 通过Sheet序号取得Sheet对象
     *
     * @param xSpreadsheetDocument Calc文件对象
     * @param index                Sheet序号
     * @return Sheet对象
     */
    public static XSpreadsheet getXSpreadsheetByIndex(XSpreadsheetDocument xSpreadsheetDocument, int index) throws Exception {
        XSpreadsheets sheets = xSpreadsheetDocument.getSheets();
        // get via the index access the first sheet
        XIndexAccess indexAccess = UnoRuntime.queryInterface(XIndexAccess.class, sheets);
        // specify the first sheet from the spreadsheet
        return UnoRuntime.queryInterface(
                XSpreadsheet.class, indexAccess.getByIndex(index));
    }

    /**
     * 取得表行对象
     *
     * @param xSpreadsheet Sheet对象
     * @return 表行对象
     */
    public static XTableRows getXTableRows(XSpreadsheet xSpreadsheet) {
        XSpreadsheet xSpreadsheet1 = UnoRuntime.queryInterface(XSpreadsheet.class, xSpreadsheet);
        XColumnRowRange columnRowRange =
                UnoRuntime.queryInterface(
                        XColumnRowRange.class, xSpreadsheet1);

        return columnRowRange.getRows();
    }

    /**
     * 取得表列对象
     *
     * @param xSpreadsheet Sheet对象
     * @return 表列对象
     */
    public static XTableColumns getXTableColumns(XSpreadsheet xSpreadsheet) {
        XSpreadsheet xSpreadsheet1 = UnoRuntime.queryInterface(XSpreadsheet.class, xSpreadsheet);
        XColumnRowRange columnRowRange =
                UnoRuntime.queryInterface(
                        XColumnRowRange.class, xSpreadsheet1);

        return columnRowRange.getColumns();
    }

    /**
     * 插入列
     *
     * @param xTableColumns 列对象
     * @param index         插入序号(0-Based)
     * @param count         插入的列数
     */
    public static void insertColumns(XTableColumns xTableColumns, int index, int count) {
        xTableColumns.insertByIndex(index, count);
    }

    /**
     * 插入行
     *
     * @param xTableRows 行对象
     * @param index      插入序号(0-Based)
     * @param count      插入的行数
     */
    public static void insertRows(XTableRows xTableRows, int index, int count) {
        xTableRows.insertByIndex(index, count);
    }

    /**
     * 删除列
     *
     * @param xTableColumns 列对象
     * @param index         删除序号(0-Based)
     * @param count         删除的列数
     */
    public static void removeColumns(XTableColumns xTableColumns, int index, int count) {
        xTableColumns.removeByIndex(index, count);
    }

    /**
     * 删除行
     *
     * @param xTableRows 行对象
     * @param index      删除序号(0-Based)
     * @param count      删除的行数
     */
    public static void removeRows(XTableRows xTableRows, int index, int count) {
        xTableRows.removeByIndex(index, count);
    }

    /**
     * 写入单元格
     *
     * @param xSpreadsheet Sheet对象
     * @param col          列番号
     * @param row          行番号
     * @param value        写入的字符串
     */
    public static void setTextValueOfXCellAtPosition(XSpreadsheet xSpreadsheet,
                                                     int col,
                                                     int row,
                                                     String value) throws Exception {
        XCell xCell = xSpreadsheet.getCellByPosition(col, row);
        XText xCellText = UnoRuntime.queryInterface(XText.class, xCell);
        xCellText.setString(value);
    }

    /**
     * 写入单元格整形数据类型
     *
     * @param xSpreadsheet Sheet对象
     * @param col          列番号
     * @param row          行番号
     * @param value        写入的字符串
     */
    public static void setNumValueOfXCellAtPosition(XSpreadsheet xSpreadsheet,
                                                    int col,
                                                    int row,
                                                    double value) throws Exception {
        XCell xCell = xSpreadsheet.getCellByPosition(col, row);
        xCell.setValue(value);
    }

    /**
     * 取得单元格对象
     *
     * @param xSpreadsheet Sheet对象
     * @param col          列番号
     * @param row          行番号
     * @return 单元格对象
     */
    public static XCell getXCellByPosition(XSpreadsheet xSpreadsheet,
                                           int col,
                                           int row) throws Exception {
        return xSpreadsheet.getCellByPosition(col, row);
    }

    /**
     * 取得单元格（字符串类型）
     *
     * @param xSpreadsheet Sheet对象
     * @param col          列番号
     * @param row          行番号
     * @return 取得的字符串
     */
    public static String getTextValueOfXCellAtPosition(XSpreadsheet xSpreadsheet,
                                                       int col,
                                                       int row)
            throws Exception {
        XCell xCell = xSpreadsheet.getCellByPosition(col, row);
        XText xCellText = UnoRuntime.queryInterface(XText.class, xCell);
        return xCellText.getString();
    }

    /**
     * 取得单元格（整形数据类型）
     *
     * @param xSpreadsheet Sheet对象
     * @param col          列番号
     * @param row          行番号
     * @return 取得的字符串
     */
    public static double getNumValueOfXCellAtPosition(XSpreadsheet xSpreadsheet, int col, int row)
            throws Exception {
        XCell xCell = xSpreadsheet.getCellByPosition(col, row);
        return xCell.getValue();
    }

    /**
     * 获取域
     *
     * @param xSpreadsheet Sheet对象
     * @param col          开始列号
     * @param row          开始行号
     * @param col1         结束列号
     * @param row1         结束行号
     * @return 域对象
     */
    public static XCellRange getRange(XSpreadsheet xSpreadsheet, int col, int row, int col1,
                                      int row1) throws Exception {
        return xSpreadsheet.getCellRangeByPosition(col, row,
                col1, row1);
    }

    /**
     * 单元格合并
     *
     * @param xSpreadsheet sheet对象
     * @param col          开始列号
     * @param row          开始行号
     * @param col1         结束列号
     * @param row1         结束行号
     */
    public static void mergeCellRange(XSpreadsheet xSpreadsheet, int col, int row, int col1, int row1)
            throws Exception {
        XCellRange xCellRange = getRange(xSpreadsheet, col, row, col1, row1);
        XMergeable xMerge = UnoRuntime.queryInterface(XMergeable.class, xCellRange);
        //合并单元格
        xMerge.merge(true);
    }


    /**
     * 将XComponent对象转换成byte[]数据类型
     *
     * @param xComponent 组件对象
     * @return 数据
     * @throws Exception
     */
    public static byte[] getRawDataFromStream(XComponent xComponent) throws Exception {
        XStorageBasedDocument xStorageBasedDocument;
        xStorageBasedDocument = UnoRuntime
                .queryInterface(XStorageBasedDocument.class, xComponent);
        XStorage xStorage = xStorageBasedDocument.getDocumentStorage();
        XStorage xSubstorage = xStorage.openStorageElement("Versions",
                ElementModes.READWRITE);
        XStream stream = xSubstorage.openStreamElement("0.0.1",
                ElementModes.READWRITE);
        XOutputStream os = stream.getOutputStream();
        XTruncate truncate = UnoRuntime.queryInterface(
                XTruncate.class, os);
        truncate.truncate();
        PropertyValue[] argh = new PropertyValue[2];
        argh[0] = new PropertyValue();
        argh[0].Name = "FilterName";
        argh[0].Value = "StarOffice XML (Calc)";
        argh[1] = new PropertyValue();
        argh[1].Name = "OutputStream";
        argh[1].Value = os;
        // create storable object from document component and store to stream
        XStorable xStorable = UnoRuntime.queryInterface(
                XStorable.class, xComponent);
        xStorable.storeToURL("private:stream", argh);
        // get input stream and skeekable so we can read the stream
        XInputStream is = UnoRuntime.queryInterface(
                XInputStream.class, os);
        XSeekable xSeekable = UnoRuntime.queryInterface(
                XSeekable.class, os);
        xSeekable.seek(0);
        // read and return the bytes
        byte[][] t_bytes = new byte[1][(int) xSeekable.getLength()];
        is.readBytes(t_bytes, (int) xSeekable.getLength());
        return t_bytes[0];
    }

    /**
     * @param macroName String
     * @param params    Object[]
     * @return Object
     * @throws BootstrapException
     */
    public static Object executeMacro(String macroName, Object[] params) throws BootstrapException {
        try {
            XComponentContext context = OpenOfficeHelper.createContext();
            XComponentLoader loader = OpenOfficeHelper.createComponentLoader(context);
            XSpreadsheetDocument mxDoc = newSpreadsheetDocument(loader);


            XScriptProviderSupplier xScriptPS = UnoRuntime.queryInterface(XScriptProviderSupplier.class, mxDoc);
            XScriptProvider xScriptProvider = xScriptPS.getScriptProvider();
            XScript xScript = xScriptProvider.getScript("vnd.sun.star.script:" + macroName);
            short[][] aOutParamIndex = new short[1][1];
            Object[][] aOutParam = new Object[1][1];
            return xScript.invoke(params, aOutParamIndex, aOutParam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void testConvertPdf() {
        String inputFilePath = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\FT-模板.xlsx";
        String outputFilePath = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\FT-模板.pdf";
        try {
            toPdf(inputFilePath, outputFilePath);
        } catch (BootstrapException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String inputFilePath = "E:\\Workspace\\mes2Koala\\mes2Koala-web\\target\\mes2Koala-web-1.0.0-SNAPSHOT\\excel\\FT-模板.xlsx";
        try {
            XComponentContext context = OpenOfficeHelper.createContext();
            XComponentLoader loader = OpenOfficeHelper.createComponentLoader(context);

            XSpreadsheetDocument document = SpreadsheetHelper.openSpreadsheetDocument(loader, inputFilePath);
            XSpreadsheet spreadsheet0 = SpreadsheetHelper.getXSpreadsheetByIndex(document, 4);


            //  OpenOfficeHelper.closeDocument(document);
        } catch (BootstrapException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
