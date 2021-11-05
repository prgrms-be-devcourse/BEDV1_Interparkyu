package org.programmers.interparkyu.ticket.controller;

import static org.programmers.interparkyu.ticket.controller.RoundSeatController.roundSeatBaseUri;

import java.text.MessageFormat;
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
@RequestMapping(roundSeatBaseUri)
public class RoundSeatController {

    public static final String roundSeatBaseUri = "/v1/performances";

    private final RoundSeatService roundSeatService;

    @GetMapping("/{performanceId}/round")
    public ApiResponse<List<RoundResponse>> allRoundSeat(
        @PathVariable Long performanceId,
        @RequestParam String date
    ) {
        return ApiResponse.ok(
            MessageFormat.format("{0}/{1}?date={2}", roundSeatBaseUri, performanceId, date),
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
            MessageFormat.format("{0}/{1}/{2}?date={4}", roundSeatBaseUri, performanceId, roundNumber, date),
            roundSeatService.getAllRoundSeat(performanceId, date, roundNumber)
        );
    }

}
