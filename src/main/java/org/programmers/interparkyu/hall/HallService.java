package org.programmers.interparkyu.hall;

import java.text.MessageFormat;
import java.util.Optional;
import org.programmers.interparkyu.error.exception.NotFoundException;
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
