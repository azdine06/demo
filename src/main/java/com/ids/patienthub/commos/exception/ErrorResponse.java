package com.ids.patienthub.commos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @AllArgsConstructor
@NoArgsConstructor @SuperBuilder
public class ErrorEesponse {
    private String timestamp;
    private int status;
    private String error;
    private String path;

}
