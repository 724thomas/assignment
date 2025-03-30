package com.example.demo.api.admin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class ActivityReportResponse {
    private int signUpCount;
    private int loginCount;
    private int chatCreatedCount;
}
