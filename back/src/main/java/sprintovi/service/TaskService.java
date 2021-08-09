package sprintovi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import sprintovi.model.Task;
import sprintovi.web.dto.TaskDto;

public interface TaskService {
	
	Page<Task> search(String imeZadatka, Long idSprinta, int pageNum);
	Page<Task> all(int page);
	Optional<Task> one(Long id);
	Task save(TaskDto zadatak);
	Task delete(Long id);
	Task prelazak(Long id);
	Long sumPoints(Long sprintId);
	
}
