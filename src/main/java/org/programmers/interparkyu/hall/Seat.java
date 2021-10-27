package org.programmers.interparkyu.hall;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Section section;

    Integer sectionSeatNumber;

    Integer price;

    String hallName;

    @Builder
    private Seat(Section section, Integer sectionSeatNumber, Integer price, String hallName) {
        this.section = section;
        this.sectionSeatNumber = sectionSeatNumber;
        this.price = price;
        this.hallName = hallName;
    }

    public void changeSectionData(Section section, Integer sectionSeatNumber) {
        this.section = section;
        this.sectionSeatNumber = sectionSeatNumber;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeHallName(String hallName) {
        this.hallName = hallName;
    }
}
