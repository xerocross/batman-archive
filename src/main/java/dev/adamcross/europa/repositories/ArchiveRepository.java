package dev.adamcross.europa.repositories;

import dev.adamcross.europa.model.ArchiveElement;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
public class ArchiveRepository {

    private EntityManager entityManager;

    public Optional<ArchiveElement> save(ArchiveElement archiveElement) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(archiveElement);
            entityManager.getTransaction().commit();
            return Optional.of(archiveElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
