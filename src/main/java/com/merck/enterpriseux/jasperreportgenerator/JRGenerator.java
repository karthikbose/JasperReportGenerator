package com.merck.enterpriseux.jasperreportgenerator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JRGenerator {

	public JRGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String jrxmlfileName = "/coq_pdf_template.jrxml";
		String inputProperties = "/input.properties";
		String outputFileName = "Report.pdf";
		
		 for(int i = 0; i < args.length; i++) {
			 if(i==0) {
				 //jrxmlfileName
				 jrxmlfileName = args[0];
			 } else if(i==1) {
				 //reports input properties
				 inputProperties = args[1];
			 } else if(i==2) {
				 //output pdf file name
				 outputFileName = args[2];
				 break;
			 }
		 }
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters you can pass dynamic values that needs to replaced in jasper file
//		parameters.put("reference", "IOS9090909");
//		parameters.put("description", "Test Description");
//		parameters.put("lotNumber", "LUX1010101");
//		parameters.put("issuedDate", "11-11-2011");

		InputStream inputStream = JRGenerator.class.getResourceAsStream(inputProperties);
		
		InputStream reportStream = JRGenerator.class.getResourceAsStream(jrxmlfileName);
		try {
			Properties props = new Properties();
			props.load(inputStream);	
			
			for (Entry<Object, Object> entry : props.entrySet()) {
				parameters.put((String) entry.getKey(), entry.getValue());
			}
			
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			if (print != null) {
				// report2 : name of file you want to export
				JasperExportManager.exportReportToPdfFile(print, outputFileName);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
