package help.openoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.script.provider.XScript;
import com.sun.star.script.provider.XScriptProvider;
import com.sun.star.script.provider.XScriptProviderSupplier;
import com.sun.star.sdbc.SQLException;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import java.io.File;

/**
 * @author yuxia
 * @version 2015/12/14
 */
public class TextHelper {

    /**
     * 新建XTextDocument
     *
     * @param loader XComponentLoader
     * @return XTextDocument
     */
    public static XTextDocument newWriterDocument(XComponentLoader loader) {
        try {
            PropertyValue[] szEmptyArgs = new PropertyValue[0];
            String strDoc = "private:factory/swriter";
            XComponent component = loader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);
            return UnoRuntime.queryInterface(XTextDocument.class, component);

        } catch (Exception e) {
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * 打开XTextDocument
     *
     * @param loader        XComponentLoader
     * @param inputFilePath String
     * @return XComponent
     */
    static XTextDocument openSpreadsheetDocument(XComponentLoader loader, String inputFilePath) throws IOException, IllegalArgumentException {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.Name = "Hidden";
        propertyValue.Value = true;
        File inputFile = new File(inputFilePath);
        String inputUrl = "file:///" + inputFile.getAbsolutePath().replace('\\', '/');
        XComponent component = loader.loadComponentFromURL(inputUrl, "_blank", 0, new PropertyValue[]{propertyValue});
        return UnoRuntime.queryInterface(XTextDocument.class, component);
    }


    /**
     * @param inputFilePath  String
     * @param outputFilePath String
     * @throws BootstrapException
     * @throws IOException
     */
    public static void toPdf(String inputFilePath, String outputFilePath) throws BootstrapException, IOException {
        // Getting the given type to convert to
        String convertType = "writer_pdf_Export";
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
     * @param macroName  String
     * @param parameters Object[]
     * @return Object
     * @throws BootstrapException
     */
    public static Object executeMacro(String macroName, Object[] parameters) throws BootstrapException {
        try {
            XComponentContext context = OpenOfficeHelper.createContext();
            XComponentLoader loader = OpenOfficeHelper.createComponentLoader(context);
            XTextDocument mxDoc = newWriterDocument(loader);
            XScriptProviderSupplier scriptPS = UnoRuntime.queryInterface(XScriptProviderSupplier.class, mxDoc);
            XScriptProvider xScriptProvider = scriptPS.getScriptProvider();
            XScript xScript = xScriptProvider.getScript("vnd.sun.star.script:" + macroName);
            short[][] aOutParamIndex = new short[1][1];
            Object[][] aOutParam = new Object[1][1];
            return xScript.invoke(parameters, aOutParamIndex, aOutParam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
