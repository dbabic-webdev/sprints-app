package sprintovi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sprintovi.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
