package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.AdminPerformanceController.performanceRequestUri;

import javax.validation.Valid;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.performance.service.AdminPerformanceService;
import org.programmers.interparkyu.performance.dto.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.PerformanceCreateResponse;
import org.springframework.web.bind.annotation.PostMapping;
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

}
