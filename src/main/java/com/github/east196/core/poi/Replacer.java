package com.github.east196.core.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class Replacer {
	public <T> T abc(){
		return (T)null;
	}
	public String abcd(){
		return abc();
	}

	public static void replaceWordDoc(String inPath, String outPath, Map<String, String> context) {
		Validate.notBlank(inPath);
		Validate.notBlank(outPath);
		Validate.notNull(context);
		try (FileInputStream in = new FileInputStream(new File(inPath));
				FileOutputStream out = new FileOutputStream(outPath, false)) {
			HWPFDocument  hdt = new HWPFDocument(in);
			Range range = hdt.getRange();
			for (Map.Entry<String, String> entry : context.entrySet()) {
				range.replaceText(entry.getKey(), entry.getValue());
			}
			hdt.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void replaceExcelXls(String inPath, String outPath, Map<String, String> context) {
		Validate.notBlank(inPath);
		Validate.notBlank(outPath);
		Validate.notNull(context);
		try (FileInputStream in = new FileInputStream(new File(inPath));
				FileOutputStream out = new FileOutputStream(outPath, false)) {
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			int sheetNum = workbook.getNumberOfSheets();
			for (int index = 0; index < sheetNum; index++) {
				HSSFSheet sheet = workbook.getSheetAt(index);
				for (int i = 0; i <= sheet.getLastRowNum(); i++) {
					HSSFRow row = sheet.getRow(i);
					if (null == row) {
						continue;
					} else {
						short minColIx = row.getFirstCellNum();
						short maxColIx = row.getLastCellNum();
						for (int colIx = minColIx; colIx < maxColIx; colIx++) {
							HSSFCell cell = row.getCell(colIx);
							if (cell == null) {
								continue;
							} else {
								String cellText = cell.getStringCellValue();
								for (Map.Entry<String, String> entry : context.entrySet()) {
									cellText = cellText.replace(entry.getKey(), entry.getValue());
								}
								cell.setCellValue(cellText);
							}
						}
					}
				}
			}
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String base = "D:/test/poi";
		String inPath = base + "/doc-template.doc";
		String outPath = base + "/doc-result.doc";
		Map<String, String> map = new HashMap<String, String>();
		map.put("${name}", "East196");
		map.put("${time}", new Date().toString());
		Replacer.replaceWordDoc(inPath, outPath, map);
		inPath = base + "/xls-template.xls";
		outPath = base + "/xls-result.xls";
		Replacer.replaceExcelXls(inPath, outPath, map);
	}

}
