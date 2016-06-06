package org.jxls.demo;

/**
 * Created by Administrator on 2015/12/27.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jxls.area.Area;
import org.jxls.area.CommandData;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.command.GridCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.formula.StandardFormulaProcessor;
import org.jxls.template.SimpleExporter;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;

public class RuncardHelper {
    private boolean hideTemplateSheet = false;
    private boolean deleteTemplateSheet = true;
    private boolean processFormulas = true;
    private boolean useFastFormulaProcessor = true;
    private String expressionNotationBegin;
    private String expressionNotationEnd;
    private SimpleExporter simpleExporter = new SimpleExporter();
    private AreaBuilder areaBuilder = new XlsCommentAreaBuilder();

    public static RuncardHelper getInstance() {
        return new RuncardHelper();
    }

    private RuncardHelper() {
    }

    public AreaBuilder getAreaBuilder() {
        return this.areaBuilder;
    }

    public RuncardHelper setAreaBuilder(AreaBuilder areaBuilder) {
        this.areaBuilder = areaBuilder;
        return this;
    }

    public boolean isProcessFormulas() {
        return this.processFormulas;
    }

    public RuncardHelper setProcessFormulas(boolean processFormulas) {
        this.processFormulas = processFormulas;
        return this;
    }

    public boolean isHideTemplateSheet() {
        return this.hideTemplateSheet;
    }

    public RuncardHelper setHideTemplateSheet(boolean hideTemplateSheet) {
        this.hideTemplateSheet = hideTemplateSheet;
        return this;
    }

    public boolean isDeleteTemplateSheet() {
        return this.deleteTemplateSheet;
    }

    public RuncardHelper setDeleteTemplateSheet(boolean deleteTemplateSheet) {
        this.deleteTemplateSheet = deleteTemplateSheet;
        return this;
    }

    public boolean isUseFastFormulaProcessor() {
        return this.useFastFormulaProcessor;
    }

    public RuncardHelper setUseFastFormulaProcessor(boolean useFastFormulaProcessor) {
        this.useFastFormulaProcessor = useFastFormulaProcessor;
        return this;
    }

    public RuncardHelper buildExpressionNotation(String expressionNotationBegin, String expressionNotationEnd) {
        this.expressionNotationBegin = expressionNotationBegin;
        this.expressionNotationEnd = expressionNotationEnd;
        return this;
    }

    public RuncardHelper processTemplate(InputStream templateStream, OutputStream targetStream, Context context) throws IOException {
        Transformer transformer = createTransformer(templateStream, targetStream);
        areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        for (Area xlsArea : xlsAreaList) {
            xlsArea.applyAt(
                    new CellRef(xlsArea.getStartCellRef().getCellName()), context);
            if( processFormulas ) {
                setFormulaProcessor(xlsArea);
                xlsArea.processFormulas();
            }
        }
        transformer.write();
        return this;
    }

    public Area setFormulaProcessor(Area xlsArea) {
        if(this.useFastFormulaProcessor) {
            xlsArea.setFormulaProcessor(new FastFormulaProcessor());
        } else {
            xlsArea.setFormulaProcessor(new StandardFormulaProcessor());
        }

        return xlsArea;
    }

    public RuncardHelper processTemplateAtCell(InputStream templateStream, OutputStream targetStream, Context context, String targetCell) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List xlsAreaList = this.areaBuilder.build();
        if(xlsAreaList.isEmpty()) {
            throw new IllegalStateException("No XlsArea were detected for this processing");
        } else {
            Area firstArea = (Area)xlsAreaList.get(0);
            CellRef targetCellRef = new CellRef(targetCell);
            firstArea.applyAt(targetCellRef, context);
            if(this.processFormulas) {
                this.setFormulaProcessor(firstArea);
                firstArea.processFormulas();
            }

            String sourceSheetName = firstArea.getStartCellRef().getSheetName();
            if(!sourceSheetName.equalsIgnoreCase(targetCellRef.getSheetName())) {
                if(this.hideTemplateSheet) {
                    transformer.setHidden(sourceSheetName, true);
                }

                if(this.deleteTemplateSheet) {
                    transformer.deleteSheet(sourceSheetName);
                }
            }

            transformer.write();
            return this;
        }
    }

    public RuncardHelper processGridTemplate(InputStream templateStream, OutputStream targetStream, Context context, String objectProps) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List xlsAreaList = this.areaBuilder.build();
        Iterator var7 = xlsAreaList.iterator();

        while(var7.hasNext()) {
            Area xlsArea = (Area)var7.next();
            GridCommand gridCommand = (GridCommand)((CommandData)xlsArea.getCommandDataList().get(0)).getCommand();
            gridCommand.setProps(objectProps);
            this.setFormulaProcessor(xlsArea);
            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
            if(this.processFormulas) {
                xlsArea.processFormulas();
            }
        }

        transformer.write();
        return this;
    }

    public void processGridTemplateAtCell(InputStream templateStream, OutputStream targetStream, Context context, String objectProps, String targetCell) throws IOException {
        Transformer transformer = this.createTransformer(templateStream, targetStream);
        this.areaBuilder.setTransformer(transformer);
        List xlsAreaList = this.areaBuilder.build();
        Area firstArea = (Area)xlsAreaList.get(0);
        CellRef targetCellRef = new CellRef(targetCell);
        GridCommand gridCommand = (GridCommand)((CommandData)firstArea.getCommandDataList().get(0)).getCommand();
        gridCommand.setProps(objectProps);
        firstArea.applyAt(targetCellRef, context);
        if(this.processFormulas) {
            this.setFormulaProcessor(firstArea);
            firstArea.processFormulas();
        }

        String sourceSheetName = firstArea.getStartCellRef().getSheetName();
        if(!sourceSheetName.equalsIgnoreCase(targetCellRef.getSheetName())) {
            if(this.hideTemplateSheet) {
                transformer.setHidden(sourceSheetName, true);
            }

            if(this.deleteTemplateSheet) {
                transformer.deleteSheet(sourceSheetName);
            }
        }

        transformer.write();
    }

    public RuncardHelper registerGridTemplate(InputStream inputStream) throws IOException {
        this.simpleExporter.registerGridTemplate(inputStream);
        return this;
    }

    public RuncardHelper gridExport(Collection headers, Collection dataObjects, String objectProps, OutputStream outputStream) {
        this.simpleExporter.gridExport(headers, dataObjects, objectProps, outputStream);
        return this;
    }

    public Transformer createTransformer(InputStream templateStream, OutputStream targetStream) {
        Transformer transformer = TransformerFactory.createTransformer(templateStream, targetStream);
        if(transformer == null) {
            throw new IllegalStateException("Cannot load XLS transformer. Please make sure a Transformer implementation is in classpath");
        } else {
            if(this.expressionNotationBegin != null && this.expressionNotationEnd != null) {
                transformer.getTransformationConfig().buildExpressionNotation(this.expressionNotationBegin, this.expressionNotationEnd);
            }

            return transformer;
        }
    }
}
