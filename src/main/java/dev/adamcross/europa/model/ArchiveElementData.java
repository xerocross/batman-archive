package dev.adamcross.europa.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArchiveElementData {
    private Long id;
    private String textData;
    private LocalDateTime postDate;
    private String hash;
    private String previousHash;
    private String username;
    private List<String> tags = new ArrayList<>();
}
