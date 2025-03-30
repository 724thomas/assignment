package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> extends BaseResponse {

    private T data;

    public static <T> DataResponse<T> of(T data) {
        return new DataResponse<>(data);
    }
}
