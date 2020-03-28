package dev.adamcross.europa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TAG")
public class SubjectTag {

	public SubjectTag(){
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter
	private Integer id;
	@Getter
	@Setter
	private String text;



	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@Getter
	ApplicationUser user;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<ArchiveElement> archiveElements = new HashSet<>();

	public SubjectTag(ApplicationUser user) {
		this.user = user;
	}


	public Set<ArchiveElement> getArchiveElements() {
		return archiveElements;
	}

	public void setArchiveElements(Set<ArchiveElement> archiveElementSet) {
		this.archiveElements = archiveElementSet;
	}
}
