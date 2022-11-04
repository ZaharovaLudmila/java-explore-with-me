package ru.practicum.ewm.model.dto.mapper;

import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.EventShortDto;

import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(events, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
