package com.test.typeValidator.repository;

import com.test.typeValidator.exception.PersistenceException;
import com.test.typeValidator.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface DBPlayerRepository extends JpaRepository<Player, String> {
}
