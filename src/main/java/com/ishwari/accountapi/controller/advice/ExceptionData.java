package com.ishwari.accountapi.controller.advice;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExceptionData {

    private Instant exceptionTime;

    private String path = "";

    private int responseStatusCode;

    private String responseStatusText;

    private String exceptionClassType;

    private String exceptionMessage;

}
