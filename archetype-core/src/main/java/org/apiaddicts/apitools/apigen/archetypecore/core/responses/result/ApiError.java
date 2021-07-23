package org.apiaddicts.apitools.apigen.archetypecore.core.responses.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String code;
    private String message;
    private String element;

    public ApiError(Integer code, String message, String element) {
        this.code = code == null ? null : code.toString();
        this.message = message;
        this.element = element;
    }
}
