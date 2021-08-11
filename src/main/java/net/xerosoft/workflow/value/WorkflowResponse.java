package net.xerosoft.workflow.value;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class WorkflowResponse {
    public UUID id;
    @JsonRawValue
    public String template;
    
    @JsonProperty("created_at")
    public Date createAt;
}
