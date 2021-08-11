package net.xerosoft.notification.rest;

import java.util.Set;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsRequest {
    @JsonProperty("message")
    public String message;

    @JsonProperty("organisation_name")
    public String organisation;

    @JsonProperty("recipients")
    public Set<String> recipients;

    @JsonProperty("sender")
    public String sender;

    @JsonProperty("triggered_by")
    public String triggeredBy;

    @JsonProperty("units")
    public int units;

    @JsonProperty("is_bulk")
    public boolean bulk;
}
