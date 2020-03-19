package dev.adamcross.europa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import dev.adamcross.europa.model.ArchiveElement;

@Component
public interface ArchiveElementRepo extends CrudRepository<ArchiveElement, Integer> {
}
