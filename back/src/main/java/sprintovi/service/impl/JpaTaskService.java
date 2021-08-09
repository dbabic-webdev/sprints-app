package sprintovi.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sprintovi.model.Sprint;
import sprintovi.model.State;
import sprintovi.model.Task;
import sprintovi.repository.SprintRepository;
import sprintovi.repository.StateRepository;
import sprintovi.repository.TaskRepository;
import sprintovi.service.TaskService;
import sprintovi.support.TaskDtoToTask;
import sprintovi.web.dto.TaskDto;


@Service
@Transactional
public class JpaTaskService implements TaskService {
	
	@Autowired
	private TaskDtoToTask toEntity;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private SprintRepository sprintRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Override
	public Page<Task> all(int page) {
		return taskRepository.findAll(PageRequest.of(page, 3));
	}
	
	@Override
	public Page<Task> search(String taskName, Long sprintId, int page){
		
		if(taskName != null) {
			taskName = "%" + taskName + "%";
		}
		
		return taskRepository.search(taskName, sprintId, PageRequest.of(page, 3));
	}
	
	@Override
	public Optional<Task> one(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Task save(TaskDto taskDto) {
				
		Task task = toEntity.convert(taskDto);
		if(task.getId() == null) {
			State newState = stateRepository.findById(1L).get();
			task.setState(newState);
		}

		if(taskDto.getId() != null) { // Trebalo bi da postoji u bazi
			Optional<Task> oldTaskOptional = one(taskDto.getId());
			if(oldTaskOptional.isPresent()) {
				Task oldTask = oldTaskOptional.get();
				Sprint oldSprint = oldTask.getSprint();
				oldSprint.removeTask(taskDto.getId());
				sprintRepository.save(oldSprint);
			}
		}
			
		Sprint sprint = task.getSprint();
		sprint.addTask(task);
		Task savedTask = taskRepository.save(task);
		sprintRepository.save(sprint);
		return savedTask;
	}


	@Override
	@Transactional
	public Task delete(Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if(taskOptional.isPresent()) {
			Task task = taskOptional.get();
			
			Sprint sprint = task.getSprint();
			Integer newSprintPoints = Integer.parseInt(sprint.getPoints()) - task.getPoints();
			sprint.setPoints(newSprintPoints + "");
			
			sprint.removeTask(task.getId());
			State state = task.getState();
			state.removeTask(task.getId());
			
			stateRepository.save(state);
			sprintRepository.save(sprint);
			taskRepository.deleteById(id);
			return task;
		}
		
		return null;
	}

	@Override
	public Task prelazak(Long id) {
		
		Task task = taskRepository.getOne(id);
		if(task != null) {
			State currentState = task.getState();
			if(currentState.getId().equals(1L)) {
				State inProgress = stateRepository.getOne(2L);
				task.setState(inProgress);
			}
			else if(currentState.getId().equals(2L)) {
				State finished = stateRepository.getOne(3L);
				task.setState(finished);
			}
			
			return taskRepository.save(task);
		}
		
		return null;
	}

	@Override
	public Long sumPoints(Long sprintId) {
		return taskRepository.sumPoints(sprintId);
	}
	
	

}
