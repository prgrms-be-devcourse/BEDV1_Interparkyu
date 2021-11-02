package org.programmers.interparkyu.hall.service;

import java.text.MessageFormat;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.domain.Hall;
import org.programmers.interparkyu.hall.repository.HallRepository;
import org.springframework.stereotype.Service;

@Service
public class HallService {

  private final HallRepository hallRepository;

  public HallService(HallRepository hallRepository) {
    this.hallRepository = hallRepository;
  }

  public Hall findIdByName(String hallName){
    return hallRepository.findByName(hallName).orElseThrow(() -> new NotFoundException(
        MessageFormat.format("{0} 공연장이 없습니다.", hallName)));
  }

}
