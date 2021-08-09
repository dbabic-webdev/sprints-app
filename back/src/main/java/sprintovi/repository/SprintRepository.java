package sprintovi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sprintovi.model.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long>{

}
