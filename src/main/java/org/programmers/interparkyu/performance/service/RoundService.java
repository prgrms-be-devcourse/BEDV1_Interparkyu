package org.programmers.interparkyu.performance.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.performance.dto.RoundDateResponse;
import org.programmers.interparkyu.performance.repository.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundService {

    private final RoundRepository repository;

    @Transactional(readOnly = true)
    public List<RoundDateResponse> getAllByPerformanceId(Long performanceId) {
        List<Round> rounds = repository.findAllByPerformanceId(performanceId);
        return rounds.stream().map(RoundDateResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<Round> getAllByPerformanceIdAndDate(Long performanceId, LocalDate date) {
        return repository.findAllByPerformanceIdAndDateOrderByRoundAsc(performanceId, date);
    }

    @Transactional(readOnly = true)
    public List<Round> getAllByPerformanceIdAndDateAndRound(Long performanceId, LocalDate date, Integer round) {
        return repository.findAllByPerformanceIdAndDateAndRoundOrderById(performanceId, date, round);
    }

    @Transactional(readOnly = true)
    public Round findRoundById(final Long id){
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageFormat.format("id : {0} 공연회차가 없습니다.", id)));
    }
}
