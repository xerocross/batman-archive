package dev.adamcross.europa.repositories;

import dev.adamcross.europa.model.SubjectTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectTagRepo extends CrudRepository<SubjectTag, Integer> {
    List<SubjectTag> findByText(String tagText);
    List<SubjectTag> findByTextAndUserId(String tagText, Long userId);
}
