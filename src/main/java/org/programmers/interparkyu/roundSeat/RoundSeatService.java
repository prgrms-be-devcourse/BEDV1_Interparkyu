package org.programmers.interparkyu.roundSeat;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.repository.SeatRepository;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.performance.dto.RoundResponse;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;
import org.programmers.interparkyu.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundSeatService {

    private final RoundSeatRepository roundSeatRepository;

    private final RoundService roundService;

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<RoundResponse> getAllRoundAndRoundSeatByPerformanceIdAndDate(Long performanceId, String date) {
        List<Round> rounds = roundService.getAllByPerformanceIdAndDate(performanceId, TimeUtil.toLocalDate(date));
        return rounds.stream().map(round -> {
            List<RoundSeat> roundSeats = roundSeatRepository.findAllByRoundId(round.getId());
            return RoundResponse.from(round, roundSeats);
        }).toList();
    }
    
    @Transactional(readOnly = true)
    public List<RoundSeatResponse> getAllRoundSeatByPerformanceIdAndDateAndRound(Long performanceId, String date, Integer roundNumber) {
        List<Round> rounds = roundService.getAllByPerformanceIdAndDateAndRound(performanceId, TimeUtil.toLocalDate(date), roundNumber);
        return rounds.stream().map(round -> {
            List<RoundSeat> roundSeats = roundSeatRepository.findAllByRoundId(round.getId());
            return RoundSeatResponse.from(round, roundSeats);
        }).toList();
    }

    @Transactional(readOnly = true)
    public RoundSeat getRoundSeatById(Long roundSeatId) {
        return roundSeatRepository.getById(roundSeatId);
    }
}
