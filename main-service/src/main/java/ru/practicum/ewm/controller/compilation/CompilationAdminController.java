package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.service.interfaces.CompilationService;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@Validated
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping()
    public CompilationDto addCompilation(@Validated @RequestBody NewCompilationDto newCompilation) {

        log.info("Admin - добавление новой подборки событий: {}", newCompilation.getTitle());
        return compilationService.adminAddCompilation(newCompilation);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable("compId") long compId) {
        log.info("Admin - удаление подборки событий с id: {}", compId);
        compilationService.adminDeleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteCompilationsEventById(@Positive @PathVariable("compId") long compId,
                                            @Positive @PathVariable("eventId") long eventId) {
        log.info("Admin - удаление события с id: {} из подборки с id: {}", eventId, compId);
        compilationService.adminDeleteCompilationsEventById(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addCompilationsEventById(@Positive @PathVariable("compId") long compId,
                                            @Positive @PathVariable("eventId") long eventId) {
        log.info("Admin - добавление события с id: {} в подборку с id: {}", eventId, compId);
        compilationService.adminAddCompilationsEventById(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@Positive @PathVariable("compId") long compId) {
        log.info("Admin - открепление подборки с id: {} на главной странице", compId);
        compilationService.adminSetPinned(compId, false);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@Positive @PathVariable("compId") long compId) {
        log.info("Admin - закрепление подборки с id: {} на главной странице", compId);
        compilationService.adminSetPinned(compId, true);
    }
}
