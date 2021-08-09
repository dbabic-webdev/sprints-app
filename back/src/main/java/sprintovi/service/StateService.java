package sprintovi.service;

import java.util.List;
import java.util.Optional;

import sprintovi.model.State;

public interface StateService {

	List<State> all();
	Optional<State> one(Long id);
	State save(State stanje);

}
