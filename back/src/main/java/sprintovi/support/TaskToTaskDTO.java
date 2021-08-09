package sprintovi.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Task;
import sprintovi.web.dto.TaskDto;


@Component
public class TaskToTaskDTO implements Converter<Task, TaskDto>{

	@Override
	public TaskDto convert(Task source) {
		
		TaskDto retValue = new TaskDto();
		retValue.setId(source.getId());
		retValue.setName(source.getName());
		retValue.setPoints(source.getPoints());
		retValue.setEmployee(source.getEmployee());
		
		retValue.setSprintId(source.getSprint().getId());
		retValue.setSprintName(source.getSprint().getName());
		
		retValue.setStateId(source.getState().getId());
		retValue.setStateName(source.getState().getName());
		
		return retValue;
	}

	public List<TaskDto> convert(List<Task> source){
		List<TaskDto> ret = new ArrayList<>();
		
		for(Task z : source){
			ret.add(convert(z));
		}
		
		return ret;
	}

}
