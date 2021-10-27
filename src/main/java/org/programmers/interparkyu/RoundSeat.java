package org.programmers.interparkyu;

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
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;

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
    @Setter
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @Setter
    private Seat seat;

    public RoundSeat(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void changeReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        super.update();
    }
}