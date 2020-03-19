package dev.adamcross.europa.service;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArchiveElementResponse {
    private Integer id;
    private String textData;
    private LocalDateTime postDate;
    private List<String> tags = new ArrayList<>();
}
