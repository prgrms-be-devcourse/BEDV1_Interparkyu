package org.programmers.interparkyu.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.programmers.interparkyu.ticket.domain.ReservationStatus;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.performance.domain.Round;

public class RemainingSeatCalcConverter {

    public static Map<Integer, Map<String, Integer>> convertFrom(List<RoundSeat> roundSeats) {
        Map<Integer, Map<String, Integer>> map = new HashMap<>(); // < 회차번호 < 섹션이름, 좌석수 > >
        for (RoundSeat roundSeat : roundSeats) {
            Round round = roundSeat.getRound();
            map.putIfAbsent(round.getRound(), new HashMap<>());

            String sectionName = roundSeat.getSeat().getSection().toString();
            Map<String, Integer> sectionRemainingSeatCountMap = map.get(round.getRound());

            if (roundSeat.getReservationStatus().equals(ReservationStatus.NOT_RESERVED)) {
                sectionRemainingSeatCountMap.put(
                    sectionName,
                    sectionRemainingSeatCountMap.getOrDefault(sectionName, 0) + 1
                );
            }
        }

        return map;
    }

}
