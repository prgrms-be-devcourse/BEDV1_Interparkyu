package org.programmers.interparkyu.ticket.service;

import java.text.MessageFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.common.utils.TimeUtil;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.performance.dto.response.RoundResponse;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.ticket.dto.response.RoundSeatResponse;
import org.programmers.interparkyu.ticket.repository.RoundSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundSeatService {

    private final RoundSeatRepository roundSeatRepository;

    private final RoundService roundService;

    @Transactional(readOnly = true)
    public List<RoundResponse> getAllRoundAndRoundSeat(Long performanceId, String date) {
        List<Round> rounds = roundService.getAll(performanceId, TimeUtil.toLocalDate(date));
        return rounds.stream().map(round -> {
            List<RoundSeat> roundSeats = roundSeatRepository.findAllByRoundId(round.getId());
            return RoundResponse.from(round, roundSeats);
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<RoundSeatResponse> getAllRoundSeat(Long performanceId, String date,
        Integer roundNumber) {
        List<Round> rounds = roundService.getAll(
            performanceId, TimeUtil.toLocalDate(date), roundNumber);
        return rounds.stream().map(round -> {
            List<RoundSeat> roundSeats = roundSeatRepository.findAllByRoundId(round.getId());
            return RoundSeatResponse.from(round, roundSeats);
        }).toList();
    }

    @Transactional(readOnly = true)
    public RoundSeat getRoundSeat(Long roundSeatId) {
        return roundSeatRepository.getById(roundSeatId);
    }

    public RoundSeat getRoundSeatAndLock(Long roundSeatId) {
        return roundSeatRepository.readById(roundSeatId);
    }

    @Transactional
    public RoundSeat getRoundSeat(Round round, Seat seat) {
        return roundSeatRepository.findByRoundAndSeat(round, seat).orElseThrow(
            () -> new NotFoundException(
                MessageFormat.format(
                    "roundSeat with round id {0}, seat id {1} not found",
                    round.getId(),
                    seat.getId()
                )
            )
        );
    }

}
