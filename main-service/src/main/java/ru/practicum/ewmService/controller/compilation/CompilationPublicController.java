package ru.practicum.ewmService.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmService.model.dto.CompilationDto;
import ru.practicum.ewmService.service.interfaces.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@Validated
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping()
    public List<CompilationDto> findCompilations(@RequestParam(name = "pinned", defaultValue = "false") Boolean pinned,
                                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                                Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "10")
                                                Integer size) {

        log.info("Public - получение подборок - закреплено = {}", pinned);
        return compilationService.publicFindCompilations(pinned, PageRequest.of(from / size, size));
    }

    @GetMapping("{compId}")
    public CompilationDto findCompilationById(@PathVariable long compId) {

        log.info("Public - получение подборки по id {}", compId);
        return compilationService.publicFindCompilationById(compId);
    }
}
