package com.jogdev.barbackend.bar.dto.auth;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorDto {
    private String message;
    private String backendMessage;
    private String method;
    private String url;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp;
}

