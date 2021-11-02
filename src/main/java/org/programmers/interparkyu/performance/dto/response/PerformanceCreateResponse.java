package org.programmers.interparkyu.performance.dto.response;

public record PerformanceCreateResponse(Long id) {

  public static PerformanceCreateResponse from(Long id){
    return new PerformanceCreateResponse(id);
  }

}
