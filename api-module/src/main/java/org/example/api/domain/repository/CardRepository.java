package org.example.api.domain.repository;

import org.example.api.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByEncryptedNumber(byte[] encrypted);
}
