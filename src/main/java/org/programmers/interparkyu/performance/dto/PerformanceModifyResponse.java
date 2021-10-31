package org.programmers.interparkyu.performance.dto;

public record PerformanceModifyResponse(Long id) {

  public static PerformanceModifyResponse from(Long id){
    return new PerformanceModifyResponse(id);
  }

}