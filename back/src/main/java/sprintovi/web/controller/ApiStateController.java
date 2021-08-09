package sprintovi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sprintovi.model.State;
import sprintovi.service.StateService;
import sprintovi.support.StateToStateDto;
import sprintovi.web.dto.StateDto;

@RestController
@RequestMapping("api/states")
public class ApiStateController {

	@Autowired
	private StateService stateService;
	
	@Autowired
	private StateToStateDto toDto;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<StateDto>> getAll(
			@RequestParam(required = false) String name){
		
		List<State> states = stateService.all();
		return new ResponseEntity<>(toDto.convert(states), HttpStatus.OK);
	}
	
}
