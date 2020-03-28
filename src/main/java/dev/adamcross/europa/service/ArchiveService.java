package dev.adamcross.europa.service;

import dev.adamcross.europa.model.ApplicationUser;
import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.model.ArchiveMasterElement;
import dev.adamcross.europa.model.SubjectTag;
import dev.adamcross.europa.repositories.ArchiveElementRepo;
import dev.adamcross.europa.repositories.ArchiveMasterElementRepo;
import dev.adamcross.europa.repositories.SubjectTagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArchiveService {
	
	private final ArchiveElementRepo archiveElementRepo;
	private final SubjectTagRepo subjectTagRepo;
	private final ArchiveServiceHelper archiveServiceHelper;
	private final ArchiveMasterElementRepo archiveMasterElementRepo;
	private final AuthenticationService authenticationService;

	public ArchiveElement handleNewData(DataRequestBody dataRequestBody) {
		Long userId = authenticationService.getUser().getId();
		ApplicationUser user = authenticationService.getUser();
		// create initial archive element and master
		ArchiveElement archiveElement = archiveServiceHelper.buildArchiveElement(dataRequestBody);
		ArchiveMasterElement archiveMasterElement = getNewArchiveMasterElement();
		archiveElement.setMasterId(archiveMasterElement.getId());

		archiveElementRepo.save(archiveElement);

		addTags(archiveElement);

		// add element hash
		int hash = archiveElement.hashCode();
		archiveElement.setHash(hash);
		archiveElementRepo.save(archiveElement);
		archiveMasterElement.setInitialHash(archiveElement.getHash());
		archiveMasterElement.setLatestHash(archiveElement.getHash());
		archiveMasterElementRepo.save(archiveMasterElement);
		return archiveElement;
	}

	public List<ArchiveElement> getAll() {
		ApplicationUser user = authenticationService.getUser();
		return archiveElementRepo.findByUserId(user.getId());
		//return archiveElements.stream().map(ArchiveElement::getArchiveElementData).collect(Collectors.toList());
	}

	private void addTags(ArchiveElement archiveElement) {
		Set<SubjectTag> tags = archiveServiceHelper.resolveSubjectTags(archiveElement);
		subjectTagRepo.saveAll(tags);
		archiveElement.getTags().addAll(tags);
	}

	public ArchiveElement handleRevision(DataRequestBody dataRequestBody, Long elementHash) {
		List<ArchiveElement> formerElementMatches = archiveElementRepo.findByHash(elementHash);
		ArchiveElement formerElement = formerElementMatches.stream().findFirst().orElse(null);
		if (formerElement == null) {
			throw new RuntimeException();
		} else {
			ArchiveMasterElement archiveMasterElement = archiveMasterElementRepo.findById(formerElement.getMasterId())
					.stream().findFirst().orElse(null);
			if (archiveMasterElement == null) {
				throw new RuntimeException();
			}
			if (archiveMasterElement.getLatestHash().equals(elementHash)) {
				throw new RuntimeException();
			}
			return buildAndSaveRevision(dataRequestBody, archiveMasterElement);
		}
	}

	private ArchiveElement buildAndSaveRevision(DataRequestBody dataRequestBody, ArchiveMasterElement archiveMasterElement) {
		ArchiveElement archiveElement = archiveServiceHelper.buildArchiveElement(dataRequestBody);
		archiveElement.setMasterId(archiveMasterElement.getId());
		addTags(archiveElement);
		archiveElement.setPreviousHash(archiveMasterElement.getLatestHash());
		archiveElement.setHash(archiveElement.hashCode());
		archiveMasterElement.setLatestHash(archiveElement.getHash());
		archiveElementRepo.save(archiveElement);
		archiveMasterElementRepo.save(archiveMasterElement);
		return archiveElement;
	}


	private ArchiveMasterElement getNewArchiveMasterElement() {
		ArchiveMasterElement archiveMasterElement = new ArchiveMasterElement(authenticationService.getUser());
		archiveMasterElementRepo.save(archiveMasterElement);
		return archiveMasterElement;
	}

	public ArchiveElement getArchiveElementById(Integer id) {
		Optional<ArchiveElement> elt = archiveElementRepo.findById(id);
		return elt.orElse(null);
	}

	public List<ArchiveElement> searchText(String searchText) {
		List<ArchiveElement> list = new ArrayList<>();
		Iterable<ArchiveElement> archiveElements =  archiveElementRepo.findAll();
		archiveElements.forEach(elt -> {
			if (elt.getTextData().contains(searchText)) {
				list.add(elt);
			}
		});
		return list;
	}

	public Set<ArchiveElement> searchByTag(String tagText) {
		Set<ArchiveElement> list = new HashSet<>();
		Iterable<ArchiveElement> archiveElements =  archiveElementRepo.findAll();
		archiveElements.forEach(archiveElement -> {
			Set<SubjectTag> tags = archiveElement.getTags();
			tags.forEach(tag-> {
				if (tag.getText().equals(tagText)) {
					list.add(archiveElement);
				}
			});

		});
		return list;
	}
}
