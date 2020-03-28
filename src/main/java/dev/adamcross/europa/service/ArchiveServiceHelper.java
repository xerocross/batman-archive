package dev.adamcross.europa.service;

import dev.adamcross.europa.model.ApplicationUser;
import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.model.SubjectTag;
import dev.adamcross.europa.repositories.SubjectTagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ArchiveServiceHelper {
	private final SubjectTagRepo subjectTagRepo;
	private final AuthenticationService authenticationService;
	
	
	public ArchiveElement buildArchiveElement(DataRequestBody dataRequestBody) {
		LocalDateTime now = LocalDateTime.now();
		ArchiveElement archiveElement = new ArchiveElement(authenticationService.getUser());
		archiveElement.setPostDate(now);
		archiveElement.setTextData(dataRequestBody.getArchiveText());
		return archiveElement;
	}
	
	public Set<SubjectTag> resolveSubjectTags(ArchiveElement archiveElement) {
		Set<SubjectTag> tags = new HashSet<>();
		Pattern pattern = Pattern.compile("#([A-Za-zÀ-ÖØ-öø-ÿ0-9]+)");
		final int GROUP_INDEX = 1;
		Matcher matcher = pattern.matcher(archiveElement.getTextData());
		ApplicationUser user = authenticationService.getUser();
		while (matcher.find()) {
			String tagText = matcher.group(GROUP_INDEX);
			List<SubjectTag> matchingTags = subjectTagRepo.findByTextAndUserId(tagText, user.getId());
			SubjectTag matchingTag = matchingTags.stream().findFirst().orElse(null);
			if (matchingTag != null) {
				tags.add(matchingTag);
			} else {
				SubjectTag tag = new SubjectTag(authenticationService.getUser());
				tag.setText(tagText);
				tags.add(tag);
			}
		}
		return tags;
	}
}
