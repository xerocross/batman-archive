package dev.adamcross.europa.service;

import dev.adamcross.europa.model.ArchiveElementData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArchiveListResponse {
	private List<ArchiveElementData> archiveElementData = new ArrayList<>();
}
