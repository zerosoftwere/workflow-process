package net.xerosoft.workflow.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.xerosoft.applicant.model.Applicant;


@Entity
@Table(name = "workflow_state")
public class WorkflowState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public UUID id;

    @Column(name = "step")
    public String step;

    @Column(name = "answer")
    public int answer;

    @Column(name = "counter")
    public int counter;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    public Applicant applicant;
}
