package org.programmers.interparkyu.ticket.domain;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.interparkyu.common.domain.BaseEntity;
import org.programmers.interparkyu.common.error.exception.TimeExceedException;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;


@Entity
@Table(name = "tickets")
@Getter
@NoArgsConstructor
public class Ticket extends BaseEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.WAITING;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id", nullable = false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id", nullable = false)
    private Seat seat;

    public Ticket(User user, Round round, Seat seat) {
        this.user = user;
        this.round = round;
        this.seat = seat;
    }

    public void cancel() {
        LocalDateTime cancelableUntil = round.getTicketCancelableUntil();

        if (LocalDateTime.now().isAfter(cancelableUntil)) {
            // TODO 메
            throw new TimeExceedException(
                MessageFormat.format("ticket was cancelable until {0}", cancelableUntil)
            );
        }

        paymentStatus = paymentStatus.cancel();
    }

    public void complete() {
        paymentStatus = paymentStatus.complete();
    }

}
