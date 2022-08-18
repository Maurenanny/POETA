package mx.edu.utez.poeta.entity;

public class GeneralTemplateResponse {

    private int code;
    private Object object;
    private String message;

    public GeneralTemplateResponse(Object obj) {
        this.code = 200;
        this.object = obj;
        this.message = "";
    }

    public GeneralTemplateResponse() {
        this.code = 403;
        this.object = null;
        this.message = "No tiene permiso para ejecutar esta acción";
    }

    public int getCode() {
        return this.code;
    }

    public Object getObject() {
        return this.object;
    }

    public String getMessage() {
        return this.message;
    }
    
}
