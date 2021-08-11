package net.xerosoft.application.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationRequest {
    public String name;
    @JsonProperty("workflow_id")
    public UUID workflowId;
}
