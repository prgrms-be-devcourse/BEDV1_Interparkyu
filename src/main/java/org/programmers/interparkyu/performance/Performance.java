package org.programmers.interparkyu.performance;

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
import lombok.Setter;
import org.programmers.interparkyu.BaseEntity;
import org.programmers.interparkyu.hall.Hall;

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
    @Setter
    private Hall hall;

    @Builder
    private Performance(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Integer runtime,
        PerformanceCategory category
    ) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runtime = runtime;
        this.category = category;
    }

    public void changeMetaData(String title, Integer runtime, PerformanceCategory category) {
        this.title = title;
        this.runtime = runtime;
        this.category = category;
        super.update();
    }

    public void changeDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        super.update();
    }
}
