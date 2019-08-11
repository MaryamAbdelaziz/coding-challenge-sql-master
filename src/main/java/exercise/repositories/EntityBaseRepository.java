package exercise.repositories;

import exercise.model.EntityBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityBaseRepository extends JpaRepository<EntityBase, Long>, JpaSpecificationExecutor<EntityBase> {
}
