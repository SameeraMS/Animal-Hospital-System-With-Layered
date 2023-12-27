package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.ReportBO;
import lk.ijse.ahms.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportBOImpl implements ReportBO {
    public JasperPrint printBill(HashMap hashMap) throws JRException, SQLException {

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Bill.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);

        return jasperPrint;

    }

    public JasperPrint printAppointment() throws JRException, SQLException {

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/allappointments.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);

        return jasperPrint;
    }
}
