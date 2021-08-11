package net.xerosoft.workflow.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "workflow")
public class Workflow extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "template", columnDefinition = "TEXT")
    public String template;
    
    @CreationTimestamp
    @Column(name = "created_at")
    public Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Date updatedAt;
}
