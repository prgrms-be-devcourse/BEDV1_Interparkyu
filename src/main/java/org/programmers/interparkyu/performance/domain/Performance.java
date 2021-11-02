package org.programmers.interparkyu.performance.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.interparkyu.common.domain.BaseEntity;
import org.programmers.interparkyu.hall.domain.Hall;

@Entity
@Table(name = "performances")
@Getter
@NoArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer runtime;

    @Enumerated(EnumType.STRING)
    private PerformanceCategory category;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;

    @Builder
    private Performance(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Integer runtime,
        PerformanceCategory category,
        Hall hall
    ) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runtime = runtime;
        this.category = category;
        this.hall = hall;
    }

    public void changeMetaData(String title, Integer runtime, PerformanceCategory category, Hall hall) {
        this.title = title;
        this.runtime = runtime;
        this.category = category;
        this.hall = hall;
    }

    public void changeDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
