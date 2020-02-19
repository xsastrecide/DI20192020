package cide.dam.di20192020;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class ExempleJasperJava {
	static class MySQLConnUtils {
		public static Connection getMySQLConnection()
				throws ClassNotFoundException, SQLException {
			String hostName = "172.16.26.200";
			String dbName = "Mondial";
			String userName = "alumne";
			String password = "tofol";
			return getMySQLConnection(hostName, dbName, userName, password);
		}

		public static Connection getMySQLConnection(String hostName, String dbName,
				String userName, String password) throws SQLException,
		ClassNotFoundException {

			// Declare the class Driver for MySQL DB
			// This is necessary with Java 5 (or older)
			// Java6 (or newer) automatically find the appropriate driver.
			// If you use Java> 5, then this line is not needed.
			Class.forName("com.mysql.cj.jdbc.Driver");

			String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

			Connection conn = DriverManager.getConnection(connectionURL, userName,
					password);
			return conn;
		}
	}
	public static void main(String[] args)  throws JRException,
	ClassNotFoundException, SQLException {

		String reportSrcFile = "C:\\Users\\xsastre\\JaspersoftWorkspace\\MyReports\\Cherry.jrxml";

		// First, compile jrxml file.
		JasperReport jasperReport =    JasperCompileManager.compileReport(reportSrcFile);

		Connection conn = MySQLConnUtils.getMySQLConnection();

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();

		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, conn);

		// Make sure the output directory exists.
		File outDir = new File("C:/tmp");
		outDir.mkdirs();

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(print);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"C:/tmp/FirstJasperReport.pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		System.out.print("Done!");
	}
}
