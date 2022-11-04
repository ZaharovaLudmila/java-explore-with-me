package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.dto.ViewsStats;
import ru.practicum.ewm.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<EndpointHit, Long>  {
    @Query(value = "select new ru.practicum.ewm.model.dto.ViewsStats(e.app, e.uri, count(e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp between :start and :end " +
            "and (coalesce(:uris, null) is null or e.uri in :uris) " +
            "GROUP BY e.uri, e.app")
    List<ViewsStats> getStatsUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                    @Param("uris") List<String> uris);

    @Query(value = "select new ru.practicum.ewm.model.dto.ViewsStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp between :start and :end " +
            "and (coalesce(:uris, null) is null or e.uri in :uris) " +
            "GROUP BY e.uri, e.app")
    List<ViewsStats> getStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                    @Param("uris") List<String> uris);
}
