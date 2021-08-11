package net.xerosoft.application;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import net.xerosoft.application.model.Application;
import net.xerosoft.application.value.ApplicationRequest;
import net.xerosoft.workflow.model.Workflow;

@ApplicationScoped
public class ApplicationService {
    public Application create(ApplicationRequest request) {
        Workflow workflow = (Workflow) Workflow.findByIdOptional(request.workflowId)
                                .orElseThrow(() -> new NotFoundException("workflow not found"));
        
        Application application = new Application();
        application.name = request.name;
        application.workflow = workflow;
        application.persist();

        return application;
    }
}
