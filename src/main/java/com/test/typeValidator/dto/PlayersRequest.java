package com.test.typeValidator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayersRequest {
    public List<PlayerDto> players;
}
