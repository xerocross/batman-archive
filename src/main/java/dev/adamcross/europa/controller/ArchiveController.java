package dev.adamcross.europa.controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.service.ArchiveListResponse;
import dev.adamcross.europa.service.ArchiveService;
import dev.adamcross.europa.service.ArchiveServiceResponse;
import dev.adamcross.europa.service.DataRequestBody;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArchiveController {
	
	private final ArchiveService archiveService;

	@PostMapping(path = "/newInfo",  
			consumes = "application/json", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveServiceResponse> addNewInfo(@RequestBody DataRequestBody dataRequestBody) {
		ArchiveElement archiveElement = archiveService.handleNewData(dataRequestBody);
		ArchiveServiceResponse response = new ArchiveServiceResponse();
		response.setId(archiveElement.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/{archiveElementId}",  
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveServiceResponse> getNewInfo(@PathVariable Integer archiveElementId) {
		ArchiveElement archiveEntity = archiveService.getArchiveElementById(archiveElementId);
		if (archiveEntity == null) {
			return ResponseEntity.notFound().build();
		} else {
			ArchiveServiceResponse response = new ArchiveServiceResponse();
			response.setTextData(archiveEntity.getTextData());
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArchiveListResponse> searchArchive(@RequestParam String searchString) {
		ArchiveListResponse response = new ArchiveListResponse();
		List<ArchiveElement> list = archiveService.searchText(searchString);
		list.forEach(elt-> {
			response.getArchiveElements().add(elt.getArchiveElementResponse());
		});
		return ResponseEntity.ok(response);
	}
	
	
}
