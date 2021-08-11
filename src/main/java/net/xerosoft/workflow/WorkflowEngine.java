package net.xerosoft.workflow;

import java.lang.reflect.Field;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.xerosoft.applicant.ApplicantService;
import net.xerosoft.notification.EmailService;
import net.xerosoft.template.Template;
import net.xerosoft.template.TemplateService;
import net.xerosoft.workflow.model.WorkflowState;

@ApplicationScoped
public class WorkflowEngine {

    private static final Logger log = LoggerFactory.getLogger(WorkflowEngine.class);

    @Inject
    ApplicantService applicantService;

    @Inject
    TemplateService templateService;

    @Inject
    EmailService emailService;
    
    public WorkflowState next(JsonNode node, WorkflowState state) throws JsonProcessingException {
        String id = node.get("id").asText();

        switch (id) {
            case "start":
                state.step = node.get("next").asText();
                return state;
            case "end":
                state.step = "end";
                return state;
        }

        String operation = node.get("operation").asText();

        switch (operation) {
            case "set": {
                JsonNode data = node.get("data");
                state.answer = parseInt(data, state);
                state.step = node.get("next").asText();
                return state;
            }
            case "goto": {
                state.step = node.get("next").asText();
                return state;
            }
            case "add": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                state.answer = s1 + s2;
                state.step = node.get("next").asText();
                return state;
            }
            case "subtract": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                state.answer = s1 - s2;
                state.step = node.get("next").asText();
                return state;
            }
            case "multiply": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                state.answer = s1 * s2;
                state.step = node.get("next").asText();
                return state;
            }
            case "divide": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                state.answer = s1 / s2;
                state.step = node.get("next").asText();
                return state;
            }
            case "isEqual": {
                JsonNode data = node.get("data");
                String s1 = parseString(data.get(0), state);
                String s2 = parseString(data.get(1), state);
                if (s1.equals(s2)) {
                    state.step = node.get("true").asText();
                } else {
                    state.step = node.get("false").asText();
                }
                return state;
            }
            case "isLessThan": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                if (s1 < s2) {
                    state.step = node.get("true").asText();
                } else {
                    state.step = node.get("false").asText();
                }
                return state;
            }
            case "isGreaterThan": {
                JsonNode data = node.get("data");
                int s1 = parseInt(data.get(0), state);
                int s2 = parseInt(data.get(1), state);
                if (s1 > s2) {
                    state.step = node.get("true").asText();
                } else {
                    state.step = node.get("false").asText();
                }
                return state;
            }
            case "status": {
                String data = node.get("data").asText();
                applicantService.updateStatus(state.applicant.id, data);
                state.step = node.get("next").asText();
                return state;
            }
            case "sendMail": {
                JsonNode data = node.get("data");
                sendMail(data, state);
                state.step = node.get("next").asText();
                return state;
            }
        }
        return state;
    }

    private int parseInt(JsonNode node, WorkflowState state) {
        if (node.asText().startsWith("<")) {
            String variable = node.asText();
            if (variable.equals("<answer>")) {
                return state.answer;
            }
            else {
                try {
                    return Integer.parseInt(fetchValue(variable, state));
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        }
        return node.asInt();
    }

    private String parseString(JsonNode node, WorkflowState state) {
        if (node.asText().startsWith("<")) {
            String variable = node.asText();
            if (variable.equals("<answer>")) {
                return String.valueOf(state.answer);
            }
            else {
                return fetchValue(variable, state);
            }
        }
        return node.asText();
    }

    private String fetchValue(String variable, WorkflowState state) {
        String fieldName = variable.substring(1, variable.lastIndexOf(">"));
        try {
            Field field = state.applicant.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(state.applicant).toString();
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            log.error("variable {} not found", fieldName, ex);
            return null;
        }
    }

    private void sendMail(JsonNode data, WorkflowState state) {
        String recepient = parseString(data.get("recipient"), state);
        String subject = parseString(data.get("subject"), state);
        String templateName = parseString(data.get("template"), state);
        Template.Builder builder = templateService.get(templateName).builder();

        data.get("template_vars").fields().forEachRemaining(field -> {
            builder.set(field.getKey(), parseString(field.getValue(), state));
        });

        String message = builder.build();
        emailService.sendMail(recepient, subject, message);
    }
}
