package net.xerosoft.workflow;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.xerosoft.workflow.model.Workflow;
import net.xerosoft.workflow.value.WorkflowRequest;
import net.xerosoft.workflow.value.WorkflowResponse;

@Path("workflow")
@Tag(name = "Workflow")
public class WorkflowResource {

    @Inject
    WorkflowService workflowService;

    @GET
    public List<WorkflowResponse> list() {
        List<Workflow> workflows = Workflow.findAll().list();
        return toResponse(workflows);
    }


    @GET
    @Path("{id}")
    public WorkflowResponse retrieve(@PathParam UUID id) {
        Workflow workflow = Workflow.findByIdOptional(id)
                                .map(v -> (Workflow) v)
                                .orElseThrow(NotFoundException::new);
        return toResponse(workflow);
    }

    @POST
    public WorkflowResponse create(WorkflowRequest request) {
        Workflow workflow = workflowService.create(request);
        return toResponse(workflow);
    }

    private WorkflowResponse toResponse(Workflow workflow) {
        WorkflowResponse response = new WorkflowResponse();
        response.id = workflow.id;
        response.template = workflow.template;
        response.createAt = workflow.createdAt;
        return response;
    }

    private List<WorkflowResponse> toResponse(List<Workflow> workflows) {
        return workflows.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
