package net.xerosoft.log;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import net.xerosoft.workflow.model.WorkflowLog;

@Path("log")
public class LogResource {
    @GET
    @Path("workflow")
    public List<WorkflowLog> workflowLogs(@QueryParam("applicant-id") UUID applicantId) {
        if (applicantId != null) return WorkflowLog.find("applicantId", applicantId).list();
        return WorkflowLog.findAll().list();
    }
}
