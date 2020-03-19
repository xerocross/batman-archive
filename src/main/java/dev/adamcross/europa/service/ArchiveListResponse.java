package dev.adamcross.europa.service;

import java.util.ArrayList;
import java.util.List;

import dev.adamcross.europa.model.ArchiveElement;
import lombok.Data;

@Data
public class ArchiveListResponse {
	private List<ArchiveElementResponse> archiveElements = new ArrayList<>();
}
