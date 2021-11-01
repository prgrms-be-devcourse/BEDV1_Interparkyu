package org.programmers.interparkyu.ticket;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.programmers.interparkyu.BaseEntity;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;


@Entity
@Table(name = "tickets")
@Getter
public class Ticket extends BaseEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.WAITING;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id", nullable = false)
    @Setter
    private Seat seat;

    public void cancel() {
        LocalDateTime cancelableUntil = round.getTicketCancelableUntil();

        if (LocalDateTime.now().isAfter(cancelableUntil))
            throw new RuntimeException(
                MessageFormat.format("ticket was cancelable until {0}", cancelableUntil));

        this.paymentStatus = this.paymentStatus.cancel();
    }

    public void complete() {
        this.paymentStatus = this.paymentStatus.complete();
    }

}
