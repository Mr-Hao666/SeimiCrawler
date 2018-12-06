package com.yhao.SeimiCrawler.util;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


import com.yhao.SeimiCrawler.domain.entity.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 杨浩
 * @create 2018-10-17 16:25
 **/
public class ExcelUtil {
    /**
     * 写入Excel
     *
     * @param list     数据
     * @param fileName 文件名
     * @param response 请求
     * @param request  响应
     * @throws Exception
     */
    public static void exportExcel(List<?> list, String fileName, HttpServletResponse response, HttpServletRequest request) {
        //1. 创建excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建一个工作表Sheet
        HSSFSheet sheet = workbook.createSheet("新工作表");
        //3.1 创建一个行
        int row = 0;
        //3.2 在这个行中，创建一行单元格
        for (Object o : list) {
            if (o instanceof Data) {
                HSSFRow detailRow = sheet.createRow(row);
                HSSFCell cell = detailRow.createCell(0);
                cell.setCellValue(((Data) o).getValue() + "");
            }
            row++;
        }
        //Workbook写入响应中
        writeStream(fileName, workbook, response, request);

    }

    /**
     * 写到输出流
     *
     * @throws IOException
     */
    /**
     * 写到输出流
     *
     * @throws IOException
     */
    private static void writeStream(String filename, HSSFWorkbook wb,
                                    HttpServletResponse response, HttpServletRequest request) {

        OutputStream outputStream = null;
        try {
            String agent = request.getHeader("USER-AGENT");

            filename += ".xls";
            filename.replaceAll("/", "-");
            // filename = new String(filename.getBytes("gbk"),"ISO8859_1");

            if (agent.toLowerCase().indexOf("firefox") > 0) {
                filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");
            }

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            wb.write(outputStream);
        } catch (Exception e) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
