package com.mycompany.myapp.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonFormat
public class MyCustomData1 {

    // https://stackoverflow.com/questions/8560348/different-names-of-json-property-during-serialization-and-deserialization

    private Long employeeId;

    private String employeeName;

    private Long employeeAge;

    private Long companyId;

    private String companyName;

    private Date companyPublishedAt;

    @JsonProperty("employeeId")
    public Long getEmployeeId() {
        return employeeId;
    }

    @JsonProperty("employee_id")
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @JsonProperty("employeeName")
    public String getEmployeeName() {
        return employeeName;
    }

    @JsonProperty("employee_name")
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @JsonProperty("employeeAge")
    public Long getEmployeeAge() {
        return employeeAge;
    }

    @JsonProperty("employee_age")
    public void setEmployeeAge(Long employeeAge) {
        this.employeeAge = employeeAge;
    }

    @JsonProperty("companyId")
    public Long getCompanyId() {
        return companyId;
    }

    @JsonProperty("company_id")
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("company_name")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("companypublishedAt")
    public Date getCompanyPublishedAt() {
        return companyPublishedAt;
    }

    @JsonProperty("company_published_at")
    public void setCompanyPublishedAt(Date companyPublishedAt) {
        this.companyPublishedAt = companyPublishedAt;
    }
}
