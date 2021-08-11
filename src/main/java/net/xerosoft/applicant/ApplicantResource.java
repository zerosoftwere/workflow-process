package net.xerosoft.applicant;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.xerosoft.applicant.model.Applicant;
import net.xerosoft.applicant.value.ApplicantRequest;
import net.xerosoft.applicant.value.ApplicantResponse;

@Path("applicant")
@Tag(name = "Applicant")
public class ApplicantResource {

    @Inject
    ApplicantService applicantService;

    @GET
    public List<ApplicantResponse> list() {
        List<Applicant> applicants = Applicant.findAll().list();
        return toResonse(applicants);
    }

    @GET
    @Path("{id}")
    public ApplicantResponse retrieve(@PathParam UUID id) {
        Applicant applicant = (Applicant) Applicant.findByIdOptional(id)
                                .orElseThrow(NotFoundException::new);
        return toResponse(applicant);
    }

    public ApplicantResponse create(ApplicantRequest request) {
        Applicant applicant = applicantService.create(request);
        return toResponse(applicant);
    }
    
    private ApplicantResponse toResponse(Applicant applicant) {
        ApplicantResponse response = new ApplicantResponse();
        response.id = applicant.id;
        response.name = applicant.name;
        response.satus = applicant.status;
        response.applicationId = applicant.application.id;
        response.sex =  applicant.sex;
        
        return response;
    }

    private List<ApplicantResponse> toResonse(List<Applicant> applicants) {
        return applicants.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
