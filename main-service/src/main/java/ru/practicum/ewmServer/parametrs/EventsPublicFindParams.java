package ru.practicum.ewmServer.parametrs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewmServer.exception.EventSortException;
import ru.practicum.ewmServer.exception.TimeException;
import ru.practicum.ewmServer.model.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Data
@AllArgsConstructor
public class EventsPublicFindParams {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private EventSort sort;
    private Pageable page;
    private String addr;
    private String uri;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventsPublicFindParams(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                                  Boolean onlyAvailable, String sort, Integer from, Integer size,
                                  HttpServletRequest request)
            throws DateTimeParseException {
        this.text = text;
        if(categories != null) {
            this.categories = categories;
        }
        this.paid = paid;
        if (rangeStart != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, formatter);
        } else {
            this.rangeStart = LocalDateTime.now();
        }
        if (rangeEnd != null) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter);
        } else {
            this.rangeEnd = LocalDateTime.now().plusYears(100);
        }

        if (this.rangeStart.isAfter(this.rangeEnd)) {
            throw new TimeException(String.format("The start date %s cannot be later than the end date %s",
                    rangeStart, rangeEnd));
        }
        this.onlyAvailable = onlyAvailable;
        this.sort = EventSort.from(sort)
                .orElseThrow(() -> new EventSortException("Unknown sort: " + sort));
        this.page = this.sort == EventSort.EVENT_DATE ? PageRequest.of(from / size, size, Sort.Direction.ASC,
                "eventDate") : PageRequest.of(from / size, size);
        this.addr = request.getRemoteAddr();
        this.uri = request.getRequestURI();
    }
}
