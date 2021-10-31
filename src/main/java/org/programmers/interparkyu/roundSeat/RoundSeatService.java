package org.programmers.interparkyu.roundSeat;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.hall.repository.SeatRepository;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;
import org.programmers.interparkyu.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundSeatService {

    private final RoundSeatRepository roundSeatRepository;

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<RoundSeatResponse> getAllRoundSeatByPerformanceIdAndDate(Long performanceId, String date) {
        List<RoundSeat> roundSeats =
            roundSeatRepository.findAllRoundByPerformanceIdAndDate(performanceId, TimeUtil.toLocalDate(date));
        return roundSeats.stream()
            .map(roundSeat -> {
            Seat seat = roundSeat.getSeat();
            return RoundSeatResponse.from(seat, roundSeat);
        }).toList();
    }
}
