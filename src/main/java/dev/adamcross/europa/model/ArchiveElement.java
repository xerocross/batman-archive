package dev.adamcross.europa.model;

import dev.adamcross.europa.service.ArchiveElementResponse;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ARCHIVE_ELEMENT")
public class ArchiveElement {
	@Id
	@GeneratedValue
	Integer id;
	String textData;
	LocalDateTime postDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "archive_element_tags",
            joinColumns = {@JoinColumn(name = "archive_element_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<SubjectTag> tags = new HashSet<>();

	public ArchiveElement() {
	}

	public Set<SubjectTag> getTags() {
		return tags;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTextData() {
		return this.textData;
	}

	public LocalDateTime getPostDate() {
		return this.postDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public void setTags(Set<SubjectTag> tags) {
		this.tags = tags;
	}

	public ArchiveElementResponse getArchiveElementResponse() {
		ArchiveElementResponse archiveElementResponse = new ArchiveElementResponse();
		archiveElementResponse.setId(this.id);
		archiveElementResponse.setPostDate(this.postDate);
		archiveElementResponse.setTextData(this.textData);
		this.getTags().forEach(tag->{
			archiveElementResponse.getTags().add(tag.getText());
		});
		return archiveElementResponse;
	}
}
