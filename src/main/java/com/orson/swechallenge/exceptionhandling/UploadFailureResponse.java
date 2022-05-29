package com.orson.swechallenge.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFailureResponse {

    private Integer failure;
    private String error;
}

