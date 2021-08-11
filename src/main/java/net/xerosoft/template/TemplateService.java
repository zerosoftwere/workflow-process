package net.xerosoft.template;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemplateService {

    public Template get(String name) {
        String templateURI = String.format("/templates/%s.html", name);

        try {
            String template = new String(getClass().getResourceAsStream(templateURI).readAllBytes());
            return new Template(template);
        } catch (Exception ex) {
            throw new TemplateNotFoundException();
        }
    }
}