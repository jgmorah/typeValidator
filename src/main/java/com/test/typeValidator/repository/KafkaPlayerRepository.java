package com.test.typeValidator.repository;

import com.test.typeValidator.dto.PlayerDto;
import com.test.typeValidator.exception.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository()
public class KafkaPlayerRepository {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    public void publish(PlayerDto message) throws PersistenceException {
        try {
            kafkaTemplate.send(topicName, message.toString());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
