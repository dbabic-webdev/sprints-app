package sprintovi.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sprintovi.model.Task;
import sprintovi.service.TaskService;
import sprintovi.support.TaskToTaskDTO;
import sprintovi.web.dto.TaskDto;

@RestController
@RequestMapping(value = "/api/tasks")
public class ApiTaskController {
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskToTaskDTO toDTO;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<TaskDto>> get(@RequestParam(value = "taskName", required = false) String taskName,
			@RequestParam(value = "sprintId", required = false) Long sprintId,
			@RequestParam(value = "pageNo", defaultValue = "0") int pageNo) {

		Page<Task> page = null;

		if (taskName != null || sprintId != null) {
			page = taskService.search(taskName, sprintId, pageNo);
		} else {
			page = taskService.all(pageNo);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

		if(sprintId != null) {
			headers.add("Sprint-Total", Long.toString(taskService.sumPoints(sprintId)));
		}
		
		return new ResponseEntity<>(toDTO.convert(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<TaskDto> getOne(@PathVariable Long id) {
		Optional<Task> task= taskService.one(id);
		if (!task.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(task.get()), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<TaskDto> delete(@PathVariable Long id) {
		Task deleted = taskService.delete(id);

		if (deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<TaskDto> add(@Validated @RequestBody TaskDto newDto) {

		Task saved = taskService.save(newDto);

		return new ResponseEntity<>(toDTO.convert(saved), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<TaskDto> edit(@Validated @RequestBody TaskDto taskDTO, @PathVariable Long id) {

		if (!id.equals(taskDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Task persisted = taskService.save(taskDTO);

		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
	@RequestMapping(value = "/{id}/change_state", method = RequestMethod.POST)
	public ResponseEntity<TaskDto> changeState(@PathVariable Long id) {
		
		Task task = taskService.prelazak(id);
		if(task != null) {
			return new ResponseEntity<>(toDTO.convert(task),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
