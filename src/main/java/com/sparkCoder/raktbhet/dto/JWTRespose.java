package com.sparkCoder.raktbhet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTRespose {
    private String token;
    private String username;
}
