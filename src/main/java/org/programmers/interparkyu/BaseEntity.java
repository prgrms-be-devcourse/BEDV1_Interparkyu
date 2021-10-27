package org.programmers.interparkyu;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = createdAt;

    protected void update() {
        this.updatedAt = LocalDateTime.now();
    }

}
