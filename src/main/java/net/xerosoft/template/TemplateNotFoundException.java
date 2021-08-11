package net.xerosoft.template;

public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException() {
        super("template not found");
    }

    public TemplateNotFoundException(String message) {
        super(message);
    }
}
