package net.xerosoft.workflow.value;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class WorkflowRequest {
    @Schema(type = SchemaType.ARRAY, implementation = Map.class)
    public JsonNode template;
}
