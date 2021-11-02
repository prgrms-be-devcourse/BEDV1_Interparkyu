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
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.programmers.interparkyu.common.domain.BaseEntity;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;

@Entity
@Table(name = "round_seats")
@Getter
@NoArgsConstructor
public class RoundSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = ReservationStatus.NOT_RESERVED;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    public RoundSeat(Round round, Seat seat) {
        this.round = round;
        this.seat = seat;
    }

    public void reserve() {
        reservationStatus = reservationStatus.reserve();
    }

    public void waitForPayment() {
        reservationStatus = reservationStatus.waitForPayment();
    }

    public void cancel() {
        reservationStatus = reservationStatus.cancel();
    }

    public void makeAvailable() {
        reservationStatus = reservationStatus.makeAvailable();
    }
}
