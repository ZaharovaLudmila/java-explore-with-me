package ru.practicum.ewmStat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewsStats {
    private String app;
    private String uri;
    private long hits;
}
