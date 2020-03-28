package dev.adamcross.europa.controller;

import dev.adamcross.europa.model.ApplicationUser;
import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.model.ArchiveElementData;
import dev.adamcross.europa.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ArchiveController {
	
	private final ArchiveService archiveService;
	private final AuthenticationService authenticationService;

	@PostMapping(path = "/archive/new",
			consumes = "application/json", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveServiceResponse> addNewInfo(@RequestBody DataRequestBody dataRequestBody) {
		ApplicationUser user = authenticationService.getUser();
		ArchiveElement archiveElement = archiveService.handleNewData(dataRequestBody);
		ArchiveServiceResponse response = new ArchiveServiceResponse();
		response.setUsername(user.getUsername());
		response.setId(archiveElement.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/archive/all",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveListResponse> getAll() {
		ApplicationUser user = authenticationService.getUser();
		List<ArchiveElementData> archiveElementData = archiveService
				.getAll()
				.stream()
				.map(ArchiveElement::getArchiveElementData)
				.collect(Collectors.toList());
		ArchiveListResponse archiveListResponse = new ArchiveListResponse();
		archiveListResponse.setArchiveElementData(archiveElementData);
		return ResponseEntity.ok(archiveListResponse);
	}

	@PostMapping(path = "/revise/{archiveMasterId}",
			consumes = "application/json",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveServiceResponse> reviseArchiveElement(
										@PathVariable Long archiveMasterId,
										@RequestBody DataRequestBody dataRequestBody) {
		ArchiveElement archiveElement = archiveService.handleRevision(archiveMasterId, dataRequestBody);
		ArchiveServiceResponse response = new ArchiveServiceResponse();
		response.setId(archiveElement.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/archive/{archiveElementId}",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveServiceResponse> getNewInfo(@PathVariable Long archiveElementId) {
		ArchiveElement archiveEntity = archiveService.getArchiveElementById(archiveElementId);
		if (archiveEntity == null) {
			return ResponseEntity.notFound().build();
		} else {
			ArchiveServiceResponse response = new ArchiveServiceResponse();
			response.setTextData(archiveEntity.getTextData());
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping(path = "archive/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveListResponse> searchArchive(@RequestParam String searchString) {
		ArchiveListResponse response = new ArchiveListResponse();
		List<ArchiveElement> list = archiveService.searchText(searchString);
		list.forEach(elt-> {
			response.getArchiveElementData().add(elt.getArchiveElementData());
		});
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "archive/tag/{tagText}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveListResponse> findByTag(@PathVariable String tagText) {
		ArchiveListResponse response = new ArchiveListResponse();
		Set<ArchiveElement> list = archiveService.searchByTag(tagText);
		list.forEach(elt-> {
			response.getArchiveElementData().add(elt.getArchiveElementData());
		});
		return ResponseEntity.ok(response);
	}
}
