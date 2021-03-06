package org.programmers.interparkyu.performance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.programmers.interparkyu.common.domain.BaseEntity;

@Entity
@Table(name = "rounds")
@Getter
@NoArgsConstructor
public class Round extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer remainingSeats;

    private Integer round;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime ticketingStartDateTime;

    private LocalDateTime ticketingEndDateTime;

    private LocalDateTime ticketCancelableUntil;

    @ManyToOne
    @JoinColumn(name = "performance_id", referencedColumnName = "id")
    private Performance performance;

    @Builder
    private Round(
        String title,
        Integer round,
        Integer remainingSeats,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime ticketingStartDateTime,
        LocalDateTime ticketingEndDateTime,
        LocalDateTime ticketCancelableUntil,
        Performance performance
    ) {
        this.title = title;
        this.round = round;
        this.remainingSeats = remainingSeats;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketingStartDateTime = ticketingStartDateTime;
        this.ticketingEndDateTime = ticketingEndDateTime;
        this.ticketCancelableUntil = ticketCancelableUntil;
        this.performance = performance;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void decreaseRemainingSeat() {
        // TODO : ????????? ????????? ?????? ??????
        this.remainingSeats--;
    }

    public void increaseRemainingSeat() {
        this.remainingSeats++;
    }

    public void changeRound(Integer round) {
        this.round = round;
    }

    public void changePerformanceDateTime(
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
    ) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void changeTicketingDateTime(
        LocalDateTime ticketingStartDateTime,
        LocalDateTime ticketingEndDateTime,
        LocalDateTime ticketCancelableUntil
    ) {
        this.ticketingStartDateTime = ticketingStartDateTime;
        this.ticketingEndDateTime = ticketingEndDateTime;
        this.ticketCancelableUntil = ticketCancelableUntil;
    }

}
