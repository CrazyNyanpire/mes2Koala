package help.openoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.script.provider.XScript;
import com.sun.star.script.provider.XScriptProvider;
import com.sun.star.script.provider.XScriptProviderSupplier;
import com.sun.star.sdbc.SQLException;
import com.sun.star.sdbc.XCloseable;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import ooo.connector.BootstrapSocketConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;


public class OpenOfficeHelper {
    /**
     * 根据系统获取OO目录
     * @return String OO目录
     */
    static String getOOExeFolder() {
        String osName = System.getProperty("os.name");
        if (osName.matches("Linux.*")) {
            return "";
        } else if (osName.matches("Windows.*")) {
            return "C:/Program Files (x86)/OpenOffice 4/program/";
        } else {
            return "";
        }
    }

    /**
     * 获取组件上下文 XComponentContext
     *
     * @return XComponentContext
     * @throws BootstrapException
     */
    static XComponentContext createContext() throws BootstrapException {
        String oooExeFolder = getOOExeFolder();
        return BootstrapSocketConnector.bootstrap(oooExeFolder);
    }

    /**
     * 创建组件加载器  XComponentLoader
     *
     * @param context XComponentContext
     * @return XComponentLoader
     * @throws Exception
     */
    static XComponentLoader createComponentLoader(XComponentContext context) throws Exception {
        XMultiComponentFactory mcf = context.getServiceManager();
        Object desktop = mcf.createInstanceWithContext("com.sun.star.frame.Desktop", context);
        return UnoRuntime.queryInterface(XComponentLoader.class, desktop);
    }


    /**
     * 将绝对路径转换成URL
     *
     * @return 文件URL
     * @throws IOException           IO异常
     * @throws FileNotFoundException 文件找不到异常
     */
    public static String pathToUrl(String filePath) throws IOException, FileNotFoundException, MalformedURLException {
        File inputFile = new File(filePath);
        return "file:///" + inputFile.getAbsolutePath().replace('\\', '/');
    }

    /**
     * 打开任意文档
     *
     * @param loader        XComponentLoader
     * @param inputFilePath String
     * @return XComponent
     */
    static XComponent openDocumentComponent(XComponentLoader loader, String inputFilePath) throws IOException, IllegalArgumentException {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.Name = "Hidden";
        propertyValue.Value = true;

        File inputFile = new File(inputFilePath);
        String inputUrl = "file:///" + inputFile.getAbsolutePath().replace('\\', '/');
        return loader.loadComponentFromURL(inputUrl, "_blank", 0, new PropertyValue[]{propertyValue});
    }


    /**
     * 关闭文档
     *
     * @param doc Object
     * @throws SQLException
     */
    static void closeDocument(Object doc) throws SQLException {
        // Closing the converted document. Use XCloseable.clsoe if the
        // interface is supported, otherwise use XComponent.dispose
        XCloseable closeable = UnoRuntime.queryInterface(XCloseable.class, doc);
        if (closeable != null) {
            closeable.close();
        } else {
            XComponent component = UnoRuntime.queryInterface(XComponent.class, doc);
            component.dispose();
        }
    }

    /**
     * 转换文档
     *
     * @param doc            Object
     * @param outputFilePath String
     * @param convertType    String
     * @throws IOException
     */
    static void convertDocument(Object doc, String outputFilePath, String convertType) throws IOException {
        // Preparing properties for converting the document
        // Setting the flag for overwriting
        PropertyValue overwriteValue = new PropertyValue();
        overwriteValue.Name = "Overwrite";
        overwriteValue.Value = true;
        // Setting the filter name
        PropertyValue filterValue = new PropertyValue();
        filterValue.Name = "FilterName";
        filterValue.Value = convertType;

        // Composing the URL by replacing all backslashs
        File outputFile = new File(outputFilePath);
        String outputUrl = "file:///" + outputFile.getAbsolutePath().replace('\\', '/');

        // Getting an object that will offer a simple way to store
        // a document to a URL.
        XStorable storable = UnoRuntime.queryInterface(XStorable.class, doc);
        // Storing and converting the document
        storable.storeToURL(outputUrl, new PropertyValue[]{overwriteValue, filterValue});
    }

}
