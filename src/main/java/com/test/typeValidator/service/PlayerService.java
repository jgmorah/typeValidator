package com.test.typeValidator.service;

import com.test.typeValidator.dto.PlayerDto;
import com.test.typeValidator.dto.PlayersRequest;
import com.test.typeValidator.dto.PlayersValidatorResponse;
import com.test.typeValidator.exception.PersistenceException;
import com.test.typeValidator.model.Player;
import com.test.typeValidator.repository.DBPlayerRepository;
import com.test.typeValidator.repository.KafkaPlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    KafkaPlayerRepository kafkaRepository;

    @Autowired
    DBPlayerRepository dbRepository;

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public PlayersValidatorResponse processPlayers(PlayersRequest playersToProcess) {
        PlayersValidatorResponse response = new PlayersValidatorResponse();
        playersToProcess.players.stream()
                .forEach(v -> response.result.add(
                        validateTypes(v))
                );
        return response;
    }

    public String validateTypes(PlayerDto player) {
        try {
            switch (player.getType()) {
                case "expert":
                    return sendToDb(player);
                case "novice":
                    return sendToKafka(player);
                default:
                    return String.format("player %s did not fit", player.getName());
            }
        } catch (PersistenceException e) {
            logger.error("Error persisting sending to kafka");
            return "Unavailable to process this Player";
        }
    }

    /**
     * Method in charge of publish a msg to novice-players topic (kafka)
     *
     * @param player
     * @return
     * @throws PersistenceException
     */
    private String sendToKafka(PlayerDto player) throws PersistenceException {
        kafkaRepository.publish(player);
        return String.format("player %s sent to Kafka topic", player.getName());
    }

    /**
     * Method in charge of persist player's data into h2 database
     *
     * @param player
     * @return
     * @throws PersistenceException
     */
    private String sendToDb(PlayerDto player) throws PersistenceException {
        try {
            dbRepository.save(Player.fromDto(player));
            return String.format("player %s stored in DB", player.getName());
        } catch (IllegalArgumentException e) {
            throw new PersistenceException(e.getMessage());
        }

    }
}
