package org.programmers.interparkyu.performance.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PerformanceCreateRequest(
    @NotBlank(message = "제목이 비어있습니다.")
    String title,

    @Length(min = 8, max = 8)
    @NotBlank(message = "공연 시작 날짜가 비어있습니다.")
    String startDate,

    @Length(min = 8, max = 8)
    @NotBlank(message = "공연 종료 날짜가 비어있습니다.")
    String endDate,

    @NotBlank(message = "상영시간이 비어있습니다.")
    String runtime,

    @NotBlank(message = "공연 카테고리가 비어있습니다.")
    String category,

    @NotBlank(message = "공연장소가 비어있습니다.")
    String hallName
) {
}
