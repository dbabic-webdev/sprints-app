package sprintovi.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Sprint;
import sprintovi.model.State;
import sprintovi.model.Task;
import sprintovi.service.SprintService;
import sprintovi.service.StateService;
import sprintovi.service.TaskService;
import sprintovi.web.dto.TaskDto;

@Component
public class TaskDtoToTask implements Converter<TaskDto, Task>{

	@Autowired
	private TaskService zadatakService;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private StateService stanjeService;
	
	@Override
	public Task convert(TaskDto source) {

		Sprint sprint = null;
		if(source.getSprintId() != null) {
			sprint = sprintService.one(source.getSprintId()).get();
		}
		
		State state = null;
		if(source.getId() != null) {
			state = stanjeService.one(source.getStateId()).get();
		}
		
		if(sprint!=null) {
			
			Long id = source.getId();
			Task task = id == null ? new Task() : new Task(zadatakService.one(id).get());
			
			if(task != null) {
				task.setId(source.getId());
				task.setName(source.getName());
				task.setPoints(source.getPoints());
				task.setEmployee(source.getEmployee());
				
				task.setSprint(sprint);
				
				if(state != null) {
					task.setState(state);	
				}
			}
			
			return task;
			
		}else {
			throw new IllegalStateException("Trying to attach to non-existant entities");
		}
		
	}
}
