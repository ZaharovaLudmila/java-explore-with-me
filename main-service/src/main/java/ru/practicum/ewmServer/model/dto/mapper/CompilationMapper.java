package ru.practicum.ewmServer.model.dto.mapper;

import ru.practicum.ewmServer.model.Compilation;
import ru.practicum.ewmServer.model.dto.CompilationDto;
import ru.practicum.ewmServer.model.dto.EventShortDto;

import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
