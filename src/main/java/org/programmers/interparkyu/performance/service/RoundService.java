package org.programmers.interparkyu.performance.service;

import java.text.MessageFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.performance.dto.RoundInfo;
import org.programmers.interparkyu.performance.repository.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoundService {

    private final RoundRepository repository;

    @Transactional(readOnly = true)
    public List<RoundInfo> getAllRoundByPerformanceId(Long performanceId) {
        List<Round> rounds = repository.findAllByPerformanceId(performanceId);
        return rounds.stream().map(RoundInfo::from).toList();
    }

    @Transactional(readOnly = true)
    public Round findRoundById(final Long id){
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageFormat.format("id : {0} 공연회차가 없습니다.", id)));
    }
}
