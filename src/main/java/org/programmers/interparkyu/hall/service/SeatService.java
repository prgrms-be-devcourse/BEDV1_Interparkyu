package org.programmers.interparkyu.hall.service;

import java.text.MessageFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.hall.dto.SeatResponse;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository repository;

    @Transactional(readOnly = true)
    public SeatResponse findById(Long seatId) {
        return SeatResponse.from(repository.findById(seatId).orElseThrow(() -> {
            throw new NotFoundException(String.format("Wrong Seat ID is given %s", seatId));
        }));
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getAllSeat() {
        return repository.findAll().stream().map(SeatResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Seat getSeatById(Long seatId) {
        return repository.findById(seatId)
            .orElseThrow(() -> {
                throw new NotFoundException(MessageFormat.format("Wrong Seat ID is given {0}", seatId));
            });
    }
}
