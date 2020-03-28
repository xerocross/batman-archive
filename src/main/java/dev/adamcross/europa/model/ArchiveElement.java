package dev.adamcross.europa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.adamcross.europa.service.ArchiveElementResponse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ARCHIVE_ELEMENT")
public class ArchiveElement {
	@Id
	@GeneratedValue
	@Getter @Setter
	Long id;
	@Getter @Setter
	String textData;
	@Getter @Setter
	LocalDateTime postDate;
	@Getter @Setter
	Long masterId;
	@Getter @Setter
	String previousHash;
	@Getter @Setter
	String hash;

	public ArchiveElement(){
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@Getter
	ApplicationUser user;

	public ArchiveElement(ApplicationUser user) {
		this.user = user;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ARCHIVE_ELEMENT_TAG",
            joinColumns = {@JoinColumn(name = "archive_element_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
	@Getter @Setter
    private Set<SubjectTag> tags = new HashSet<>();

	public void setHash(int hash) {
		this.hash = Integer.toString(hash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(textData, masterId, postDate, tags, previousHash);
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

	public ArchiveElementData getArchiveElementData() {
		ArchiveElementData archiveElementData = new ArchiveElementData();
		archiveElementData.setId(this.id);
		archiveElementData.setPostDate(this.postDate);
		archiveElementData.setTextData(this.textData);
		archiveElementData.setHash(this.getHash());
		archiveElementData.setPreviousHash(this.getPreviousHash());
		archiveElementData.setUsername(this.getUser().getUsername());
		return archiveElementData;
	}
}
