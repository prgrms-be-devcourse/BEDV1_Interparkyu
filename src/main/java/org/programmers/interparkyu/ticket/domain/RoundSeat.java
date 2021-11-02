package org.programmers.interparkyu.ticket.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.programmers.interparkyu.common.domain.BaseEntity;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;

@Entity
@Table(name = "round_seats")
@Getter
public class RoundSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = ReservationStatus.NOT_RESERVED;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    @Setter
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @Setter
    private Seat seat;

    public void reserve() {
        this.reservationStatus = this.reservationStatus.reserve();
    }

    public void waitForPayment() {
        this.reservationStatus = this.reservationStatus.waitForPayment();
    }

    public void cancel() {
        this.reservationStatus = this.reservationStatus.cancel();
    }

    public void makeAvailable() {
        this.reservationStatus = this.reservationStatus.makeAvailable();
    }
}
