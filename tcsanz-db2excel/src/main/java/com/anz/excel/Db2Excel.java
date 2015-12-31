/**
 * 
 */
package com.anz.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.builder.SimpleBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component("db2Excel")
public class Db2Excel {
	
	@Value("${file.path}")
	private String filePath;
	
	@Value("${file.prefix}")
	private String filePrefix;
	
	@Value("${file.exp}")
	private String fileExp;
	
	@Value("${excel.sheet}")
	private String excelSheet;
	
	@Autowired
	private Exchange exchange;
	
	private static final Logger LOG = LoggerFactory.getLogger(Db2Excel.class);
	
	public void toExcel(Map<String, Object[]> data) throws Exception {
		
		LOG.info("Creating workbook");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(excelSheet);
		
		//Styling
		CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    style.setFont(font);
		
	    LOG.info("Populating data");
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
		    Row row = sheet.createRow(rownum++);
		    Object [] objArr = data.get(key);
		    int cellnum = 0;
		    for (Object obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        if(obj instanceof Date) 
		            cell.setCellValue((Date)obj);
		        else if(obj instanceof Boolean)
		            cell.setCellValue((Boolean)obj);
		        else if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Double)
		            cell.setCellValue((Double)obj);
		        
		        //Applying for fist row only
		        if (rownum == 1) {
		        	cell.setCellStyle(style);
		        }
		    }
		}
		
		//Auto resize column
		sheet.autoSizeColumn(0);
	    sheet.autoSizeColumn(1); 
	    sheet.autoSizeColumn(2); 
	    sheet.autoSizeColumn(3);
		 
	    //Writing to file
		try {
			String fileName = SimpleBuilder
					.simple(filePrefix + "${" + fileExp + "}")
					.evaluate(exchange, String.class);
			
			LOG.info("Writing data to {}.xlsx", fileName);
			
			FileOutputStream out = new FileOutputStream(new File(filePath + "\\" + fileName + ".xlsx"));
		    workbook.write(out);
		    out.close();
		    
		    LOG.info("Excel written successfully to {}", filePath);
		} finally {
			workbook.close();
		}
	}
}
