package org.programmers.interparkyu.hall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.interparkyu.BaseEntity;

@Entity
@Table(name = "halls")
@Getter
@NoArgsConstructor
public class Hall extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer seatCount;

    public Hall(String name, Integer seatCount) {
        this.name = name;
        this.seatCount = seatCount;
    }

    public void changeName(String name) {
        this.name = name;
        super.update();
    }

    public void changeSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
        super.update();
    }

}
