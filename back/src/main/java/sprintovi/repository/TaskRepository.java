package sprintovi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sprintovi.model.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query("SELECT t FROM Task t WHERE" +
			"(:taskName = NULL OR t.name LIKE :taskName) AND " + 
			"(:sprintId = NULL OR t.sprint.id = :sprintId)")
	Page<Task> search(@Param("taskName") String taskName, @Param("sprintId") Long sprintId, Pageable pageable);

	@Query("SELECT COALESCE(SUM(t.points),0) FROM Task t WHERE t.sprint.id = :sprintId")
	Long sumPoints(Long sprintId);

}
