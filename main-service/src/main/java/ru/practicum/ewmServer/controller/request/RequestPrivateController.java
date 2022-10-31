package ru.practicum.ewmServer.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmServer.model.dto.ParticipationRequestDto;
import ru.practicum.ewmServer.service.interfaces.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@RequestMapping(path = "/users")
public class RequestPrivateController {

    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> findRequestsByRequesterId(@PathVariable("userId") long userId) {

        log.info("Private - получение заявок на участие пользователя с id = {} в метоприятиях ", userId);
        return requestService.findRequestsByRequesterId(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addRequest(@PathVariable("userId") long userId,
                                               @Positive @RequestParam("eventId") long eventId) {
        log.info("Private - добавление запроса на участие в событии с id={} пользователем с id={}", eventId, userId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable("userId") long userId,
                                              @PathVariable("requestId") long requestId) {
        log.info("Private - отмена запроса на участие с id={} пользователем с id={}", requestId, userId);
        return requestService.cancelRequest(userId, requestId);
    }
}
