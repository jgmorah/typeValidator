package com.test.typeValidator.unit;


import com.test.typeValidator.dto.PlayerDto;
import com.test.typeValidator.dto.PlayersRequest;
import com.test.typeValidator.dto.PlayersValidatorResponse;
import com.test.typeValidator.exception.PersistenceException;
import com.test.typeValidator.model.Player;
import com.test.typeValidator.repository.DBPlayerRepository;
import com.test.typeValidator.repository.KafkaPlayerRepository;
import com.test.typeValidator.service.PlayerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PlayerServiceTest {

    @MockBean
    KafkaPlayerRepository kafkaRepository;
    @MockBean
    DBPlayerRepository dbRepository;

    @InjectMocks
    @Autowired
    PlayerService service;

    @Test
    public void processPlayersTest() {
        //GIVEN
        PlayersRequest request = playersDtoList();
        when(dbRepository.save(any())).thenReturn(new Player());
        //WHEN
        PlayersValidatorResponse response = service.processPlayers(request);

        //THEN
        Assert.assertEquals(3, response.result.size());
        Assert.assertEquals("player joe did not fit", response.result.get(0));
        Assert.assertEquals("player doe sent to Kafka topic", response.result.get(1));
        Assert.assertEquals("player moe stored in DB", response.result.get(2));
    }

    @Test
    public void validateTypesDbErrorTest() {
        //GIVEN
        when(dbRepository.save(any())).thenThrow(new IllegalArgumentException());
        //WHEN
        String response = service.validateTypes(new PlayerDto("joe", "expert"));

        //THEN
        Assert.assertEquals("Unavailable to process this Player", response);
    }

    @Test
    public void validateTypesKafkaErrorTest() throws PersistenceException {
        //GIVEN
        doThrow(new PersistenceException("msg")).when(kafkaRepository).publish(any());

        //WHEN
        String response = service.validateTypes(new PlayerDto("joe", "novice"));

        //THEN
        Assert.assertEquals("Unavailable to process this Player", response);
    }


    private PlayersRequest playersDtoList() {
        PlayerDto p1 = new PlayerDto("joe", "any");
        PlayerDto p2 = new PlayerDto("doe", "novice");
        PlayerDto p3 = new PlayerDto("moe", "expert");
        PlayersRequest playersRequest = new PlayersRequest();
        playersRequest.setPlayers(List.of(p1, p2, p3));

        return playersRequest;
    }
}
