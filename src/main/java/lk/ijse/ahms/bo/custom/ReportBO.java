package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.SQLException;
import java.util.HashMap;

public interface ReportBO extends SuperBO {
    JasperPrint printBill(HashMap hashMap) throws JRException, SQLException;
    JasperPrint printAppointment() throws JRException, SQLException;
}
