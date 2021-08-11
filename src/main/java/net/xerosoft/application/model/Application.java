package net.xerosoft.application.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import net.xerosoft.workflow.model.Workflow;

@Entity
@Table(name = "application")
public class Application extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    public Workflow workflow;
}
