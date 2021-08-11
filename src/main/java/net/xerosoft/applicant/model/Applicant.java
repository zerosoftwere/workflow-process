package net.xerosoft.applicant.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import net.xerosoft.application.model.Application;

@Entity
@Table(name = "applicant")
public class Applicant extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "name")
    public String name;

    @Column(name = "age")
    public int age;

    @Column(name = "sex")
    public String sex;

    @Column(name = "score")
    public int score;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public Status status;

    @Column(name = "email")
    public String email;

    @ManyToOne
    @JoinColumn(name = "application_id")
    public Application application;

    public static enum Status {
        PENDING, ACCEPTED, REJECTED
    }
}
