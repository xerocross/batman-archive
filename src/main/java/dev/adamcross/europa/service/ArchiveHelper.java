package dev.adamcross.europa.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.adamcross.europa.model.ArchiveElement;
import dev.adamcross.europa.model.SubjectTag;

public class ArchiveHelper {

	
	
	public static ArchiveElement buildArchiveElement(DataRequestBody dataRequestBody) {
		LocalDateTime now = LocalDateTime.now();
		ArchiveElement archiveElement = new ArchiveElement();
		archiveElement.setPostDate(now);
		archiveElement.setTextData(dataRequestBody.getDataRequestBody());
		return archiveElement;
	}
	
	public static Set<SubjectTag> resolveSubjectTags(ArchiveElement archiveElement) {
		Set<SubjectTag> tags = new HashSet<>();
		Pattern pattern = Pattern.compile("#([A-Za-zÀ-ÖØ-öø-ÿ0-9]+)");
		Matcher matcher = pattern.matcher(archiveElement.getTextData());
		while (matcher.find()) {
			SubjectTag tag = new SubjectTag();
			tag.setText(matcher.group(1));
			tags.add(tag);
		}
		return tags;
	}
}
