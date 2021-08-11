package net.xerosoft.template;

public class Template {
    private final String template;

    public Template(String template) {
        this.template = template;
    }

    public Builder builder() {
        return new Builder(this.template);
    }

    public static class Builder {
        private String template;

        public Builder(String template) {
            this.template = template;
        }

        public Builder set(String key, String value) {
            this.template = this.template.replaceAll(placeholder(key), value);
            return this;
        }

        public String build() {
            return template;
        }

        private String placeholder(String key) {
            return "\\{" + key + "\\}"; 
        }
    }
}
