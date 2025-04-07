package org.example.api.domain.repository;

import org.example.api.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c.id FROM Card c WHERE c.cardEncrypted = :encrypted")
    Optional<List<Long>> findCardIdsByCardEncrypted(@Param("encrypted") byte[] encrypted);
}
