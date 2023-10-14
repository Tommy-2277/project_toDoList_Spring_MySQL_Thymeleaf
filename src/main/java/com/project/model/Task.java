package com.project.model;

import com.project.helpers.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

   @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;
}
