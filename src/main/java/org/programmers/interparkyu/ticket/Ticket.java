package org.programmers.interparkyu.ticket;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.programmers.interparkyu.user.User;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;


@Entity
@Table(name = "tickets")
@Getter
public class Ticket {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.WAITING;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    @Setter
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @Setter
    private Seat seat;

    public void changePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
