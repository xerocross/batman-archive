package dev.adamcross.europa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.adamcross.europa.model.SubjectTag;
import org.springframework.stereotype.Service;

import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.repositories.ArchiveElementRepo;
import dev.adamcross.europa.repositories.SubjectTagRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArchiveService {
	
	private final ArchiveElementRepo archiveElementRepo;
	private final SubjectTagRepo subjectTagRepo;

	public ArchiveElement handleNewData(DataRequestBody dataRequestBody) {
		ArchiveElement archiveElement = ArchiveHelper.buildArchiveElement(dataRequestBody);
		archiveElementRepo.save(archiveElement);
		Set<SubjectTag> tags = ArchiveHelper.resolveSubjectTags(archiveElement);
		subjectTagRepo.saveAll(tags);
		archiveElement.getTags().addAll(tags);
		archiveElementRepo.save(archiveElement);
		return archiveElement;
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
}
