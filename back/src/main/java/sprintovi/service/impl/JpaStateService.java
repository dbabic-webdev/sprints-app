package sprintovi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sprintovi.model.State;
import sprintovi.repository.StateRepository;
import sprintovi.service.StateService;

@Service
public class JpaStateService implements StateService {
	
	@Autowired
	private StateRepository stanjeRepository;

	@Override
	public List<State> all() {
		return stanjeRepository.findAll();
	}

	@Override
	public Optional<State> one(Long id) {
		return stanjeRepository.findById(id);
	}

	@Override
	public State save(State stanje) {
		return stanjeRepository.save(stanje);
	}

}
