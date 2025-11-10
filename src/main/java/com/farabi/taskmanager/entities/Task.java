package com.farabi.taskmanager.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "stage", length = 20)
    @Enumerated(EnumType.STRING)
    private TaskStage stage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "priority")
    private Short priority;

    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "is_flagged")
    private Boolean isFlagged  = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;
}
