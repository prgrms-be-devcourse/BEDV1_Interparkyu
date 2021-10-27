package org.programmers.interparkyu.performance;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.interparkyu.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalTime runtime;

    @Enumerated(EnumType.STRING)
    private PerformanceCategory category;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Builder
    private Performance(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalTime runtime,
        PerformanceCategory category
    ) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runtime = runtime;
        this.category = category;
    }

    public void changeMetaData(String title, LocalTime runtime, PerformanceCategory category) {
        this.title = title;
        this.runtime = runtime;
        this.category = category;
        super.update();
    }

    public void changeDate(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        super.update();
    }
}
