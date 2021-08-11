package net.xerosoft.workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.xerosoft.applicant.model.Applicant;
import net.xerosoft.workflow.model.Workflow;
import net.xerosoft.workflow.model.WorkflowLog;
import net.xerosoft.workflow.model.WorkflowState;
import net.xerosoft.workflow.value.WorkflowRequest;

@ApplicationScoped
public class WorkflowService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(WorkflowService.class);

    @Inject
    WorkflowEngine engine;
    
    @Transactional
    public Workflow create(WorkflowRequest request) {
        Workflow workflow = new Workflow();
        copy(request, workflow);
        workflow.persist();
        return workflow;
    }

    @Transactional
    public void intiate(Applicant applicant) {
        WorkflowState state = new WorkflowState();
        state.applicant = applicant;
        state.step = "start";

        String template = applicant.application.workflow.template;
        try {
            JsonNode steps = mapper.readTree(template);

            while(!"end".equals(state.step)) {
                
                JsonNode step = getStage(steps, state.step);
                logStep(state, step);
    
                if (step == null) break;
                log.info(step.toString());
                
                engine.next(step, state);

                log.info("{}", state.answer);
            }
        } catch (JsonProcessingException ex) {
            log.error("Failed to process json");
        }
    }

    private void logStep(WorkflowState state, JsonNode step) {
        WorkflowLog log = new WorkflowLog();
        log.applicantId = state.applicant.id;
        log.triggeredBy = "-";
        log.workflowStep = step.toString();

        log.persist();
    }

    private JsonNode getStage(JsonNode steps, String id) {
        for (JsonNode step : steps) {
            if (step.get("id").asText().equals(id)) {
                return step;
            }
        }
        return null;
    }

    private void copy(WorkflowRequest request, Workflow workflow) {
        workflow.template = request.template.toString();
    }
}
