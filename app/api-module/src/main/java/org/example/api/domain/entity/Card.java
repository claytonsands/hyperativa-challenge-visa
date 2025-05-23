package org.example.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(name = "card_encrypted", columnDefinition = "BYTEA")
    private byte[] cardEncrypted;

    @Column(name = "card_masked", nullable = false)
    private String cardMasked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "line_identifier")
    private String lineIdentifier;

    @Column(name = "order_in_batch")
    private int orderInBatch;
}
