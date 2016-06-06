package help;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利用Jxls模板从Vo导出成Excel，从Excel导入进Vo
 * 文档 http://jxls.sourceforge.net/index.html
 *
 * @author Yuxiang Que
 * @version 2015/12/11
 */
public class ExcelTemplateHelper {

    /**
     * 根据Jx定义的Excel模板，将vo数据填充至其中
     *
     * @param inputXlsxTemplateStream 模板
     * @param voListName              模板中需要voArray，导出名
     * @param voList                  voArray数据
     * @param targetCell              目标cell， Sheet1!A1
     * @param outputXlsxStream        输出Xls
     * @throws IOException
     */
    public static void simpleTemplateExportExcel(InputStream inputXlsxTemplateStream,
                                                 String voListName,
                                                 List<Object> voList,
                                                 String targetCell,
                                                 OutputStream outputXlsxStream)
            throws IOException {
        Context context = new Context();
        context.putVar(voListName, voList);
        JxlsHelper.getInstance().processTemplateAtCell(inputXlsxTemplateStream, outputXlsxStream, context, targetCell);
    }

    /**
     * 根据Xml配置文件，将Excel中的数据取出
     *
     * @param inputXmlConfigStream
     * @param voArrayName
     * @param inputXlsx
     * @param voList
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws InvalidFormatException
     */
    public static boolean simpleTemplateImportExcel(InputStream inputXmlConfigStream,
                                                    String voArrayName,
                                                    InputStream inputXlsx,
                                                    List<Object> voList)
            throws IOException, SAXException, InvalidFormatException {
        XLSReader mainReader = ReaderBuilder.buildFromXML(inputXmlConfigStream);
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put(voArrayName, voList);
        XLSReadStatus readStatus = mainReader.read(inputXlsx, beans);
        return readStatus.isStatusOK();
    }

    /**
     * 根据分割符delimiter将ids字符串转换为数组，通常在excel导出时使用
     *
     * @param ids
     * @param delimiter
     * @return
     */
    public static Long[] extractIdArray(String ids, String delimiter) {
        String[] value = ids.split(delimiter);
        Long[] idArray = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArray[i] = Long.parseLong(value[i]);
        }
        return idArray;
    }

    public static String templatePath(Class<?> clazz) {
        //获取类文件所在的路径，为获取web应用路径 做准备
        String classPath = clazz.getClassLoader().getResource("").getPath();
        return classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
    }

    public static String importPath(Class<?> clazz) {
        String classPath = clazz.getClassLoader().getResource("").getPath();
        String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
        return templatePath + "import/";
    }
}
