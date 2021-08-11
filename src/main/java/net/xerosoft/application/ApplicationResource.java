package net.xerosoft.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.xerosoft.application.model.Application;
import net.xerosoft.application.value.ApplicationResponse;

@Path("application")
@Tag(name = "Application")
public class ApplicationResource {
    @Inject
    ApplicationService applicationService;

    @GET
    public List<ApplicationResponse> list() {
        List<Application> applications = Application.findAll().list();
        return toResponse(applications);
    }

    @GET
    @Path("{id}")
    public ApplicationResponse retrieve(@PathParam UUID id) {
        Application application = (Application) Application.findByIdOptional(id)
                                    .orElseThrow(NotFoundException::new);
        return toResponse(application);
    }

    private ApplicationResponse toResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();
        response.id = application.id;
        response.name = application.name;
        response.workflowId = application.workflow.id;

        return response;
    }

    private List<ApplicationResponse> toResponse(List<Application> applications) {
        return applications.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
