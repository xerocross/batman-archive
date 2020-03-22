package dev.adamcross.europa.repositories;

import dev.adamcross.europa.model.ArchiveMasterElement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchiveMasterElementRepo extends CrudRepository<ArchiveMasterElement, Integer>  {
    List<ArchiveMasterElement> findById(Long id);
}
