package com.test.typeValidator.model;

import com.test.typeValidator.dto.PlayerDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public static Player fromDto(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        return player;
    }
}
