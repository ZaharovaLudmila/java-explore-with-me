package ru.practicum.statClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewsStats {
    private String uri;
    private int hits;
}
