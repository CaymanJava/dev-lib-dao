package org.cayman.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "votes")
    private int votes;

    @Column(name = "points")
    private int points;

    public static Rating defaultRating() {
        return Rating.builder().points(0).votes(0).build();
    }
}
