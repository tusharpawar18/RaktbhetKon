package com.sparkCoder.raktbhet.dto;

import lombok.Builder;
import lombok.Data;

@Data

public class JWTRequest {
    private String username;
    private String password;
}
