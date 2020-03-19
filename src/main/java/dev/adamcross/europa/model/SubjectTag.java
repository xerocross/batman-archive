package dev.adamcross.europa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TAG")
public class SubjectTag {
	@Id
	@GeneratedValue
	@Getter
	@Setter
	private Integer id;
	@Getter
	@Setter
	private String text;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<ArchiveElement> archiveElements = new HashSet<>();

	public Set<ArchiveElement> getArchiveElements() {
		return archiveElements;
	}
	public void setArchiveElements(Set<ArchiveElement> archiveElementSet) {
		this.archiveElements = archiveElementSet;
	}
}
