package org.programmers.interparkyu.hall.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.programmers.interparkyu.common.domain.BaseEntity;

@Entity
@Table(name = "seats", uniqueConstraints = {
    @UniqueConstraint(
        name = "SEAT_UNIQUE",
        columnNames = { "hall_id", "section", "sectionSeatNumber" }
    )
})
@Getter
@NoArgsConstructor
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Section section;

    Integer sectionSeatNumber;

    Integer price;

    String hallName;

    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    @Setter
    private Hall hall;

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
