package org.programmers.interparkyu.hall;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class HallService {

  private final HallRepository hallRepository;

  public HallService(HallRepository hallRepository) {
    this.hallRepository = hallRepository;
  }

  public Hall findIdByName(String hallName){
    return hallRepository.findByName(hallName).orElseThrow(() -> new RuntimeException("공연장이 없습니다."));
  }

}
