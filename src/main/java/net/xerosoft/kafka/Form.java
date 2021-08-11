package net.xerosoft.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Form {
    @JsonProperty("name")
    public String name;

    @JsonProperty("age")
    public int age;

    @JsonProperty("sex")
    public String sex;

    @JsonProperty("email")
    public String email;

    @JsonProperty("score")
    public int score;
}
