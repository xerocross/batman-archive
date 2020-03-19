package dev.adamcross.europa.repositories;

import org.springframework.data.repository.CrudRepository;
import dev.adamcross.europa.model.SubjectTag;

public interface SubjectTagRepo extends CrudRepository<SubjectTag, Integer> {
}
