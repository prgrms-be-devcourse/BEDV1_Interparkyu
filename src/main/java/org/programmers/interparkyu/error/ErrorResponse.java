package org.programmers.interparkyu.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public record ErrorResponse(String errorMessage, String requestUri, HttpStatus status) {

  @Builder
  public ErrorResponse {
  }

}
