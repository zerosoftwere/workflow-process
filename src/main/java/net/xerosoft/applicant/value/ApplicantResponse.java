package net.xerosoft.applicant.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.xerosoft.applicant.model.Applicant;

public class ApplicantResponse {
    public UUID id;
    public String name;
    public Applicant.Status satus;
    public String sex;

    @JsonProperty("application_id")
    public UUID applicationId;
}
