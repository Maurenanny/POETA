package mx.edu.utez.poeta.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.PostulantCV;
import mx.edu.utez.poeta.service.PostulantCVService;
import mx.edu.utez.poeta.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping(value = "/postulant/cv")
public class PostulantCVController {

    private static final String typeApp = "application/x-pdf";
    private static final String attachment = "attachment; filename=cv.pdf";

    @Autowired
    private PostulantCVService postulantCVService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<PostulantCV> findAllPostulantsCVs() {
        return postulantCVService.findAllPostulantantCVs();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public PostulantCV findPostulantCVById(@PathVariable("id") long id) {
        return postulantCVService.findPostulantCVById(id);
    }

    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET})
    public PostulantCV findPostulantCVByUserId(@PathVariable("id") long id) {
        return postulantCVService.findPostulantCVByUserId(id);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test(HttpServletResponse response) throws JRException, IOException, SQLException {
        JasperPrint jasperPrint = reportService.test();
        response.setContentType(typeApp);
        response.setHeader("Content-disposition", attachment);
        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
    
}
