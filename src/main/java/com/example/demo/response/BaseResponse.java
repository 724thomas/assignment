package com.example.demo.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse implements Serializable {

    @Builder.Default
    private String dateTime = LocalDateTime.now().toString();

    public static BaseResponse of() {
        return new BaseResponse();
    }

}
