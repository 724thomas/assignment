package com.example.demo.util.openai.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceStreamDto {
    private DeltaDto deltaDto;
    private int index;
    private String finish_reason;
}
