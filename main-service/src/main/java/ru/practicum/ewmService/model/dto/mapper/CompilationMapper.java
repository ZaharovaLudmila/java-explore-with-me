package ru.practicum.ewmService.model.dto.mapper;

import ru.practicum.ewmService.model.Compilation;
import ru.practicum.ewmService.model.dto.CompilationDto;
import ru.practicum.ewmService.model.dto.EventShortDto;

import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
