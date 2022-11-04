package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.mapper.CompilationMapper;
import ru.practicum.ewm.model.dto.mapper.EventMapper;
import ru.practicum.ewm.service.interfaces.CompilationService;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final StatClientService statClient;

    @Transactional
    @Override
    public List<CompilationDto> publicFindCompilations(Boolean pinned, PageRequest of) {
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, of);
        compilations.forEach(compilation -> compilation.setEvents(
                statClient.getViews(compilation.getEvents())));
        return compilations.stream()
                .map(comp -> CompilationMapper
                        .toCompilationDto(comp, comp.getEvents().stream()
                                .map(event -> EventMapper.toEventShortDto(event,
                                        requestRepository.getNumberOfConfirmRequest(event.getId())))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CompilationDto publicFindCompilationById(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation id=%s was not found.", compId)));
        if (compilation.getEvents().size() > 0) {
            compilation.setEvents(statClient.getViews(compilation.getEvents()));
        }
        return CompilationMapper.toCompilationDto(compilation, compilation.getEvents().stream()
                .map(event -> EventMapper.toEventShortDto(event,
                        requestRepository.getNumberOfConfirmRequest(event.getId())))
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public CompilationDto adminAddCompilation(NewCompilationDto newCompilation) {
        List<Event> events = eventRepository.findAllById(newCompilation.getEvents());
        Compilation compilation = compilationRepository.save(new Compilation(0L, newCompilation.getPinned(),
                newCompilation.getTitle(), events));
        compilation.setEvents(statClient.getViews(events));
        return CompilationMapper.toCompilationDto(compilation, events.stream()
                .map(event -> EventMapper.toEventShortDto(event,
                        requestRepository.getNumberOfConfirmRequest(event.getId()))).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void adminDeleteCompilation(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public void adminDeleteCompilationsEventById(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation id=%s was not found.", compId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void adminAddCompilationsEventById(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation id=%s was not found.", compId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));

        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void adminSetPinned(long compId, boolean isPin) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation id=%s was not found.", compId)));
        if (!compilation.getPinned().equals(isPin)) {
            compilation.setPinned(isPin);
            compilationRepository.save(compilation);
        }
    }
}
