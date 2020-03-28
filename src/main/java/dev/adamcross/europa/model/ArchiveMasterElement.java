package dev.adamcross.europa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ARCHIVE_MASTER_ELEMENT")
public class ArchiveMasterElement {
    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String initialHash;
    @Getter @Setter
    private String latestHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Getter
    ApplicationUser user;

    public ArchiveMasterElement(ApplicationUser user) {
        this.user = user;
    }
}
