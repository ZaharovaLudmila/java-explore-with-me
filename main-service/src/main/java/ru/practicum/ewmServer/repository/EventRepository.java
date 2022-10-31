package ru.practicum.ewmServer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmServer.model.Event;
import ru.practicum.ewmServer.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select e from Event e " +
            "where ((:text is null or lower(e.annotation) like concat('%', lower(:text), '%')) " +
            "or (:text is null or lower(e.description) like concat('%', lower(:text), '%'))) " +
            "and (coalesce(:categories, null) is null or e.category.id in :categories) " +
            "and (coalesce(:paid, null) is null or e.paid = :paid) " +
            "and e.eventDate between :rangeStart AND :rangeEnd and e.state = 'PUBLISHED'")
    List<Event> findAllPublic(@Param("text") String text, @Param("categories") List<Long> categories,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd, Pageable page);

    List<Event> findAllByInitiatorId(long userId);

    Optional<Event> findEventByIdAndInitiatorId(long eventId, long userId);

    @Query(value = "select e from Event e " +
            "where (coalesce(:users, null) is null or e.initiator.id in :users) " +
            "and (coalesce(:states, null) is null or e.state in :states) " +
            "and (coalesce(:categories, null) is null or e.category.id in :categories) " +
            "and e.eventDate between :rangeStart AND :rangeEnd")
    List<Event> findAllAdmin(@Param("users") List<Long> users, @Param("states") List<EventState> states,
                             @Param("categories") List<Long> categories, @Param("rangeStart") LocalDateTime rangeStart,
                             @Param("rangeEnd") LocalDateTime rangeEnd, Pageable page);

    @Query("select count(event) from Event event " +
            "WHERE event.category.id = :catId ")
    int findAllByCategoryId(@Param("catId") long catId);

}
