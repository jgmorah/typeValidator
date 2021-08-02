package com.test.typeValidator.controller;

import com.test.typeValidator.dto.PlayersRequest;
import com.test.typeValidator.dto.PlayersValidatorResponse;
import com.test.typeValidator.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/test")
    public PlayersValidatorResponse processPlayer(@RequestBody PlayersRequest playersRequest) {
        return playerService.processPlayers(playersRequest);
    }
}
