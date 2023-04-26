package org.train.tikhonov.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "statuses")
public class StatusEntity {

    @Id
    @SequenceGenerator(
            name = "status_sequence",
            sequenceName = "status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_sequence"
    )
    private Long id;

    private String name;

    public StatusEntity(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<UserEntity> users;
}
