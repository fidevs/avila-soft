package com.fidev.avilasoft.exception;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseException extends Throwable {
    private String code;
    private String message;

    public Map<String, Object> toResponse() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);

        return map;
    }
}
