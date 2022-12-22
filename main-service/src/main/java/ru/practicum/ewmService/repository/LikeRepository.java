package ru.practicum.ewmService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmService.model.Like;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findLikeByEventIdAndUserId(long eventId, long userId);


    @Query("select count(l) from Like l where l.event.id = :eventId and l.isPositive = :isPositive")
    Optional<Double> findCountByEventIdAndIsPositive(@Param("eventId") long eventId,
                                                   @Param("isPositive") Boolean isPositive);

    @Query("select count(l) from Like l where l.event.id = :eventId")
    Optional<Double> findCountByEventId(@Param("eventId") long eventId);

    @Query("select count(l) from Like l where l.event.initiator.id = :userId and l.isPositive = :isPositive")
    Optional<Double> findCountByEventInitiatorIdAndIsPositive(@Param("userId") long userId,
                                                            @Param("isPositive") Boolean isPositive);

    @Query("select count(l) from Like l where l.event.initiator.id = :userId")
    Optional<Double> findCountByEventInitiatorId(@Param("userId") long userId);
}
