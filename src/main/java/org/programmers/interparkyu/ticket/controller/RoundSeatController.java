package org.programmers.interparkyu.ticket.controller;

import static org.programmers.interparkyu.ticket.controller.RoundSeatController.roundSeatRequestBaseUri;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.dto.ApiResponse;
import org.programmers.interparkyu.performance.dto.response.RoundResponse;
import org.programmers.interparkyu.ticket.dto.response.RoundSeatResponse;
import org.programmers.interparkyu.ticket.service.RoundSeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(roundSeatRequestBaseUri)
public class RoundSeatController {

    public static final String roundSeatRequestBaseUri = "/v1/performances";

    private final RoundSeatService roundSeatService;

    @GetMapping("/{performanceId}/round")
    public ApiResponse<List<RoundResponse>> allRoundSeat(
        @PathVariable Long performanceId,
        @RequestParam String date
    ) {
        return ApiResponse.ok(
            String.format("%s/%s?date=%s", roundSeatRequestBaseUri, performanceId, date),
            roundSeatService.getAllRoundAndRoundSeat(performanceId, date)
        );
    }

    @GetMapping("/{performanceId}/round/{roundNumber}/seats")
    public ApiResponse<List<RoundSeatResponse>> allRoundSeat(
        @PathVariable Long performanceId,
        @RequestParam String date,
        @PathVariable Integer roundNumber
    ) {
        return ApiResponse.ok(
            String.format(
                "%s/%s/%s?date=%s", roundSeatRequestBaseUri, performanceId, roundNumber, date),
            roundSeatService.getAllRoundSeat(
                performanceId, date, roundNumber)
        );
    }

}
