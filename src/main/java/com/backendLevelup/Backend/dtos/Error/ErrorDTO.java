package com.backendLevelup.Backend.dtos.Error;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
public class ErrorDTO {
    private Integer status;
    private Date date;

    private Map<String,String> errors;

    @Override
    public String toString() {
        return  "ErrorDTO{" +
                "status=" + status +
                ", date=" + date +
                ", errors=" + errors +
                '}';
    }
}
