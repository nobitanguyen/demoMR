package com.mr.springmvc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.mr.springmvc.model.Restaurant;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@SuppressWarnings("deprecation")
public class ReadWriteExcelFile {

	// Write file Excel

	public static void genFile(List<Restaurant> lst) throws Exception {
		try {
			
		} catch (Exception ex) {
			throw ex;
		}
	}

	// Read file Excel
	public static List<Restaurant> readXLSFile(MultipartFile file) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		List<Restaurant> lstR = new ArrayList<Restaurant>();
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Restaurant r = new Restaurant();
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				if (cell.getColumnIndex() == 0)
					r.setName(cell.getStringCellValue());
				else if (cell.getColumnIndex() == 1)
					r.setAddress(cell.getStringCellValue());
				else if (cell.getColumnIndex() == 2)
					r.setDescription(cell.getStringCellValue());
			}

			lstR.add(r);
		}
		return lstR;
	}

	public static List<Restaurant> readXLSXFile(MultipartFile file) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
		XSSFWorkbook test = new XSSFWorkbook();
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		List<Restaurant> lstR = new ArrayList<Restaurant>();
		while (rows.hasNext()) {
			Restaurant r = new Restaurant();
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				if (cell.getColumnIndex() == 0)
					r.setName(cell.getStringCellValue());
				else if (cell.getColumnIndex() == 1)
					r.setAddress(cell.getStringCellValue());
				else if (cell.getColumnIndex() == 2)
					r.setDescription(cell.getStringCellValue());
			}
			lstR.add(r);
		}
		return lstR;
	}
}
