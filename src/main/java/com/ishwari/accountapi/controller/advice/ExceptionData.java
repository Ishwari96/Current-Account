package com.ishwari.accountapi.controller.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
public class ExceptionData {

    private Instant exceptionTime;

    private String path = "";

    private int responseStatusCode;

    private String responseStatusText;

    private String exceptionClassType;

    private String exceptionMessage;

}
