package org.programmers.interparkyu.roundSeat;

import static org.programmers.interparkyu.roundSeat.RoundSeatController.roundSeatRequestBaseUri;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.hall.service.SeatService;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(roundSeatRequestBaseUri)
public class RoundSeatController {

    public static final String roundSeatRequestBaseUri = "/v1/roundSeats";

    private final RoundSeatService roundSeatService;

    private final SeatService seatService;

    @GetMapping("/{performanceId}")
    public ApiResponse<List<RoundSeatResponse>> allRoundSeat(@PathVariable Long performanceId, @RequestParam String date) {
        return ApiResponse.ok(String.format("%s/%s?date=%s", roundSeatRequestBaseUri, performanceId, date),
            roundSeatService.getAllRoundSeatByPerformanceIdAndDate(performanceId, date));
    }
}
