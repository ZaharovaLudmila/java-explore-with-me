package ru.practicum.ewmService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmService.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select count(req) from Request req " +
            "WHERE req.event.id = :eventId " +
            "AND req.status = 'CONFIRMED'")
    int getNumberOfConfirmRequest(@Param("eventId") long eventId);

    @Query("select req from Request req " +
            "WHERE req.event.id = :eventId " +
            "AND req.status <> 'CONFIRMED'")
    List<Request> findNotConfirmedRequest(@Param("eventId") long eventId);

    List<Request> findAllByRequesterId(long userId);

    @Query("select req from Request req " +
            "WHERE req.event.id = :eventId " +
            "AND req.requester.id = :userId")
    Optional<Request> findByRequesterIdAndEventId(@Param("userId") long userId, @Param("eventId") long eventId);

    Optional<Request> findByIdAndRequesterId(long requestId, long userId);

    List<Request> findAllByEventId(long eventId);

    @Query("select req from Request req " +
            "WHERE req.id = :reqId " +
            "AND req.event.id = :eventId " +
            "AND req.event.initiator.id = :userId")
    Optional<Request> findUserEventsRequest(@Param("reqId") long reqId, @Param("eventId") long eventId,
                                            @Param("userId") long userId);
}
