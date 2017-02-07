package org.cayman.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "vote")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vote {
    @ToString
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VotePK implements Serializable{
        @Column(name = "user_id")
        private int userId;

        @Column(name = "book_id")
        private int bookId;
    }

    @EmbeddedId
    private VotePK votePK;

    @Column(name = "value")
    private int value;

    @Column(name = "date")
    private LocalDate date;
}
