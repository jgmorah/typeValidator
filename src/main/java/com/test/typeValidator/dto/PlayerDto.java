package com.test.typeValidator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class PlayerDto {
    private String name;
    private String Type;
}
