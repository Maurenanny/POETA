package mx.edu.utez.poeta.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.config.AuthCheckPermission;
import mx.edu.utez.poeta.entity.GeneralTemplateResponse;
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
    private AuthCheckPermission authCheckPermission;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public GeneralTemplateResponse findAllPostulantsCVs(@RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")
                || authCheckPermission.checkPermission(token, "reclutador")) {
            return new GeneralTemplateResponse(postulantCVService.findAllPostulantantCVs());
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.GET })
    public GeneralTemplateResponse findPostulantCVById(@PathVariable("id") long id,
            @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")
                || authCheckPermission.checkPermission(token, "reclutador")) {
            return new GeneralTemplateResponse(postulantCVService.findPostulantCVById(id));
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(value = "/user/{id}", method = { RequestMethod.GET })
    public GeneralTemplateResponse findPostulantCVByUserId(@PathVariable("id") long id,
            @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")
                || authCheckPermission.checkPermission(token, "reclutador")) {
            return new GeneralTemplateResponse(postulantCVService.findPostulantCVByUserId(id));
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test(HttpServletResponse response, @RequestHeader HttpHeaders headers)
            throws JRException, IOException, SQLException {
        String token = String.valueOf(headers.get("authorization"));
        //if (authCheckPermission.checkPermission(token, "candidato")) {
            JasperPrint jasperPrint = reportService.test();
            response.setContentType(typeApp);
            response.setHeader("Content-disposition", attachment);
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            //response.setStatus(200);
        /* } else {
            response.setStatus(403);
        }*/

    }

}
