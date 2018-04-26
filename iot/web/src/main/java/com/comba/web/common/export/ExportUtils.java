package com.comba.web.common.export;

import com.comba.server.common.data.web.page.Page;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.CommonProperties;
import com.comba.web.utils.I18nUtils;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 导出工具类
 *
 * @author wengzhonghui
 */
public class ExportUtils {

    private static Logger logger = Logger.getLogger(ExportUtils.class);

    /**
     * 根据数据，生成excel文件
     *
     * @param dataPage
     * @param exportFlag
     * @param session
     * @param parameterMap
     * @return excel文件下载地址
     */
    @SuppressWarnings("unchecked")
    public static ResponseBean exportDatasByExcel(Page dataPage, String exportFlag, HttpSession session
            , @RequestParam Map<String, Object> parameterMap) {
        ResponseBean responseBean = new ResponseBean(ResponseBean.SUCCESS);
        //限制导出最大数量
        if (dataPage.getTotalCount() > CommonProperties.maxAllowExportNum) {
            responseBean.setFlag_fail();
            responseBean.setMessage(I18nUtils.getI18nText("common.export.maxAllow")
                    + CommonProperties.maxAllowExportNum + " "
                    + I18nUtils.getI18nText("common.current.pageNum") + dataPage.getTotalCount());
            return responseBean;
        }
        List<Object> datas = (List<Object>) dataPage.getResult();

        if (datas.isEmpty()){
            responseBean.setMessage("导出失败，记录为空");
            responseBean.setFlag_fail();
            return responseBean;
        }

        String path = "";
        String fileName = "导出数据";
        if (parameterMap.get("fileName") != null) {
            fileName = parameterMap.get("fileName").toString();
        }
        List<ExportColumn> columns = ExportUtils.getColumns(parameterMap);
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            String sheetName = fileName;
            if (sheetName.indexOf("_") > 0) {
                sheetName = sheetName.substring(0, sheetName.lastIndexOf("_"));
            }
            createSheet(wb, datas, sheetName, columns);
            path = createExcelFile(wb,fileName,session);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        responseBean.setMessage(path);
        return responseBean;

    }

    /**
     * 生成excel文件，支持多个sheet,column通过参数设定
     *
     * @param dataList     数据列表
     * @param parameterMap column参数
     * @param sheetNames   名称
     * @return
     * @throws IOException
     */
    public static ResponseBean export(List<List<?>> dataList, Map<String, Object> parameterMap, List<String> sheetNames,HttpSession session){
        ResponseBean response = new ResponseBean(ResponseBean.FAIL);

        if (dataList.isEmpty()){
            response.setMessage("导出失败，记录为空");
            return response;
        }

        List<ExportColumn> columns = ExportUtils.getColumns(parameterMap);
        HSSFWorkbook wb = new HSSFWorkbook();

        String fileName = "导出数据";
        String path = "";
        if (parameterMap.get("fileName") != null) {
            fileName = parameterMap.get("fileName").toString();
        }

        try {
            for (int i = 0; i < sheetNames.size(); i++) {
                List<Object> exportData = (List<Object>)dataList.get(i);
                String sheetName = sheetNames.get(i);
                createSheet(wb, exportData, sheetName, columns);
            }

            path =createExcelFile(wb,fileName,session);
        } catch (Exception e) {
            String msg = "导出文件失败 " + e.getMessage();
            logger.error(msg);
            response.setMessage(msg);
            return response;
        }

        response.setMessage(path);
        response.setFlag_success();
        return response;
    }

    private static String createExcelFile(HSSFWorkbook wb,String fileName,HttpSession session) throws IOException {
        String realPath = CommonProperties.downloadExcelPath + File.separator + System.currentTimeMillis();
        String targetPath = session.getServletContext().getRealPath("/") + realPath;

        File webFileTemp = new File(targetPath);
        if (!webFileTemp.exists()) {
            webFileTemp.mkdirs();
        }
        String targetFilePath =  targetPath  + File.separator + fileName+ ".xls";
        File file = new File(targetFilePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOut = new FileOutputStream(targetFilePath);
        wb.write(fileOut);
        fileOut.close();
        wb.close();
        return realPath + File.separator + fileName+ ".xls";
    }

    private static void createSheet(HSSFWorkbook wb, List<Object> exportData, String sheetName, List<ExportColumn> columns) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow headRow = sheet.createRow(0);
        //头栏样式设置
        HSSFCellStyle headRowStyle = wb.createCellStyle();
        headRowStyle.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
        headRowStyle.setFillForegroundColor(HSSFColor.RED.index);
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontHeightInPoints((short) 14); //设置字体高度
        headRowStyle.setFont(fontStyle);
        headRow.setHeight((short) 350);

        //内容栏样式设置
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 10); //设置字体高度
        cellStyle.setFont(cellFont);

        int cellIndex = 0;//为了防止个别列隐藏的情况
        for (int k = 0; k < columns.size(); k++) {
            ExportColumn col = columns.get(k);
            if (col.getIsHide() != null && (col.getIsHide().equalsIgnoreCase("1")
                    || col.getIsHide().equalsIgnoreCase("true"))) {
                continue;
            }
            int width = 100;
            if (col.getWidth() != null && col.getWidth().length() > 0) {
                width = Integer.parseInt(col.getWidth());
            }
            sheet.setColumnWidth(cellIndex, width * 50);
            HSSFCell cell = headRow.createCell(cellIndex);
            cell.setCellValue(col.getTitle());
            cell.setCellStyle(cellStyle);
            cellIndex++;
        }

        if (exportData != null && columns != null) {
            for (int i = 0; i < exportData.size(); i++) {
                Object obj = exportData.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                cellIndex = 0;//为了防止个别列隐藏的情况
                for (int k = 0; k < columns.size(); k++) {
                    ExportColumn col = columns.get(k);
                    if (col.getIsHide() != null && (col.getIsHide().equalsIgnoreCase("1")
                            || col.getIsHide().equalsIgnoreCase("true"))) {
                        continue;
                    }
                    String celVal = "";
                    try {
                        if (StringUtils.isNotBlank(col.getField())) {
                            Object celValObj = PropertyUtils.getProperty(obj, col.getField());
                            if (celValObj != null) {
                                celVal = celValObj.toString();
                            }
                            if (col.getExportFormatter() != null) {
                                celVal = CellFormatter.formatterVal(col.getExportFormatter(), celVal);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    HSSFCell cell = row.createCell(cellIndex);
                    cell.setCellValue(celVal);
                    cell.setCellStyle(cellStyle);
                    cellIndex++;
                }
            }
        }
    }

    /**
     * 通过web参数请求，获取参数导出列定义
     *
     * @param parameterMap
     * @return
     */
    public static List<ExportColumn> getColumns(Map<String, Object> parameterMap) {
        boolean isNext = true;
        List<ExportColumn> columnList = new ArrayList<ExportColumn>();
        int index = 0;
        while (isNext) {
            ExportColumn column = new ExportColumn(index, parameterMap);
            if (column.getField() != null) {
                columnList.add(column);
            } else {
                isNext = false;
            }
            index++;
        }
        return columnList;
    }

    /**
     * 读取设备的excel文件
     *
     * @param excel excel文件
     * @return
     */
    public static List<HSSFRow> readExcel(MultipartFile excel) throws IOException {

        HSSFWorkbook workbook = null;
        try {

            NPOIFSFileSystem fs = new NPOIFSFileSystem(excel.getInputStream());
            workbook = new HSSFWorkbook(fs.getRoot(), true);
        } catch (IOException e) {
            logger.error("读取excel文件失败，{}", e);
            throw new IOException(e.getMessage());
        }

        List<HSSFRow> list = Lists.newArrayList();
        //遍历sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            //遍历每一行,第一行是标题，略过
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                list.add(sheet.getRow(j));
            }
        }

        return list;
    }
}
