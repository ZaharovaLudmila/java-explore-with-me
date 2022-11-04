package ru.practicum.ewmService.parametrs;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmService.exception.TimeException;
import ru.practicum.ewmService.model.EventState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class EventsAdminFindParams {
    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Pageable page;
    private DateTimeFormatter formatter = UtilDateFormatter.getFormatter();

    public EventsAdminFindParams(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, Integer from, Integer size) throws DateTimeParseException {
        this.users = users;
        this.states = states.stream().map(EventState::from)
                .map(s -> s.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + s)))
                .collect(Collectors.toList());
        this.categories = categories;

        if (rangeStart != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter);
        }

        if (this.rangeStart != null && this.rangeEnd != null && this.rangeStart.isAfter(this.rangeEnd)) {
            throw new TimeException(String.format("The start date %s cannot be later than the end date %s",
                    rangeStart, rangeEnd));
        }
        this.page = PageRequest.of(from / size, size);
    }
}
