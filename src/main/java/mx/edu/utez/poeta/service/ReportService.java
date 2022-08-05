package mx.edu.utez.poeta.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@Transactional
public class ReportService {

    @Autowired
    protected DataSource localDataSource;

    @Transactional(readOnly = true)
    public JasperPrint test() throws IOException, JRException, SQLException {
        try (java.sql.Connection con = localDataSource.getConnection()) {
            String report = "classpath:reports/test.jrxml";
            JasperReport jasperReport = null;
            File file = ResourceUtils.getFile(report);
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            Map<String, Object> map = new HashedMap();
            //map.put("CLAVE", "VALOR");
            return JasperFillManager.fillReport(jasperReport, map, con);
        }
    }
    
}
