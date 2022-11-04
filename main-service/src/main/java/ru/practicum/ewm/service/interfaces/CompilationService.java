package ru.practicum.ewm.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> publicFindCompilations(Boolean pinned, PageRequest of);

    CompilationDto publicFindCompilationById(long compId);

    CompilationDto adminAddCompilation(NewCompilationDto newCompilation);

    void adminDeleteCompilation(long compId);

    void adminDeleteCompilationsEventById(long compId, long eventId);

    void adminAddCompilationsEventById(long compId, long eventId);

    void adminSetPinned(long compId, boolean isPin);
}
