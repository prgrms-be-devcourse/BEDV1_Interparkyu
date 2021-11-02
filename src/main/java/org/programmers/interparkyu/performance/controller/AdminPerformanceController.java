package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.AdminPerformanceController.performanceRequestUri;

import java.text.MessageFormat;
import javax.validation.Valid;
import org.programmers.interparkyu.common.dto.ApiResponse;
import org.programmers.interparkyu.performance.dto.request.PerformanceModifyRequest;
import org.programmers.interparkyu.performance.dto.response.PerformanceModifyResponse;
import org.programmers.interparkyu.performance.service.AdminPerformanceService;
import org.programmers.interparkyu.performance.dto.request.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.response.PerformanceCreateResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(performanceRequestUri)
@RestController
public class AdminPerformanceController {

  public static final String performanceRequestUri = "/admin/v1/performances";

  private final AdminPerformanceService performanceService;

  public AdminPerformanceController(AdminPerformanceService performanceService){
    this.performanceService = performanceService;
  }

  @PostMapping
  public ApiResponse<PerformanceCreateResponse> createPerformance(@Valid @RequestBody PerformanceCreateRequest performanceCreateRequest){
    return ApiResponse.ok(performanceRequestUri, performanceService.createPerformance(performanceCreateRequest));
  }

  @PutMapping("/{performanceId}")
  public ApiResponse<PerformanceModifyResponse> modifyPerformance(final @PathVariable Long performanceId, @Valid @RequestBody PerformanceModifyRequest performanceModifyRequest){
    return ApiResponse.ok(MessageFormat.format("{0}/{1}", performanceRequestUri, Long.toString(performanceId)), performanceService.modifyPerformance(performanceId, performanceModifyRequest));
  }

  @DeleteMapping("/{performanceId}")
  public ApiResponse<String> deletePerformance(final @PathVariable Long performanceId){
    performanceService.deletePerformance(performanceId);
    return ApiResponse.ok(MessageFormat.format("{0}/{1}", performanceRequestUri, Long.toString(performanceId)));
  }

}
