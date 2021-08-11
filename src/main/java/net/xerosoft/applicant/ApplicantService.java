package net.xerosoft.applicant;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.xerosoft.applicant.model.Applicant;
import net.xerosoft.applicant.model.Applicant.Status;
import net.xerosoft.applicant.value.ApplicantRequest;
import net.xerosoft.application.model.Application;
import net.xerosoft.workflow.WorkflowService;

@ApplicationScoped
public class ApplicantService {

    private static final Logger log = LoggerFactory.getLogger(ApplicantService.class);

    @Inject
    WorkflowService workflowService;
    
    @Transactional
    public Applicant create(ApplicantRequest request) {
        Application application = (Application) Application.findByIdOptional(request.applicationId)
                                    .orElseThrow(() -> new NotFoundException("Application not found"));

        Applicant applicant = new Applicant();
        applicant.application = application;
        applicant.status = Status.PENDING;
        
        copy(request, applicant);
        applicant.persist();

        workflowService.intiate(applicant);
        return applicant;
    }

    @Transactional
    public void updateStatus(UUID applicantId, String status) {
        Applicant.findByIdOptional(applicantId).ifPresentOrElse(entity -> {
            Applicant applicant = (Applicant) entity;
            applicant.status = Status.valueOf(status);
            applicant.persist();
        }, () -> log.warn("attempt to update non existing applicant: {}", applicantId));;
    }

    private void copy(ApplicantRequest request, Applicant applicant) {
        applicant.age = request.age;
        applicant.name = request.name;
        applicant.score = request.score;
        applicant.sex = request.sex;
        applicant.email = request.email;
    }
}
