package com.sideproject.conopli.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {

    Object data;

    ResponseDto(Object data) {
        this.data = data;
    }

    public static ResponseDto of(Object data) {
        return new ResponseDto(data);
    }
}