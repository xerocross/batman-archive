package dev.adamcross.europa.repositories;

import dev.adamcross.europa.model.ArchiveElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ArchiveElementRepo extends CrudRepository<ArchiveElement, Integer> {
    List<ArchiveElement> findByHash(Long hash);
    List<ArchiveElement> findByUserId(Long userId);
    Optional<ArchiveElement> findById(Long archiveElementId);
}
