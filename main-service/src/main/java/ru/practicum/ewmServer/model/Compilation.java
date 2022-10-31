package ru.practicum.ewmServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id", nullable = false)
    private Long id;
    private Boolean pinned;
    @Column(length = 300)
    private String title;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "compilation_events",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "compilation_id")}
    )
    private List<Event> events;
}
