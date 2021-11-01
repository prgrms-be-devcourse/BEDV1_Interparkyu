package org.programmers.interparkyu.roundSeat;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.hall.repository.SeatRepository;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundSeatService {

    private final RoundSeatRepository roundSeatRepository;

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<RoundSeatResponse> getAllRoundSeatByRoundId(Long roundId) {
        List<RoundSeat> roundSeats = roundSeatRepository.findAllByRoundId(roundId);
        return roundSeats.stream().map(roundSeat -> {
            Seat seat = roundSeat.getSeat();
            return RoundSeatResponse.from(seat, roundSeat);
        }).toList();
    }

    @Transactional(readOnly = true)
    public RoundSeat getRoundSeatById(Long roundSeatId) {
        return roundSeatRepository.getById(roundSeatId);
    }
}
