package net.xerosoft.applicant.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicantRequest {
    public String name;
    public int age;
    public int score;
    public String sex;
    public String email;
    @JsonProperty("application_id")
    public UUID applicationId;
}
