package net.xerosoft.workflow.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRawValue;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "workflow_log")
public class WorkflowLog extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "triggered_by")
    public String triggeredBy;

    @Column(name = "applicant_id")
    public UUID applicantId;

    @JsonRawValue
    @Column(name = "workflow_step")
    public String workflowStep;
}
