package sprintovi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sprintovi.model.Sprint;
import sprintovi.repository.SprintRepository;
import sprintovi.service.SprintService;

@Service
public class JpaSprintService implements SprintService {
	
	@Autowired
	private SprintRepository sprintRepository;

	@Override
	public List<Sprint> all() {
		return sprintRepository.findAll();
	}

	@Override
	public Optional<Sprint> one(Long id) {
		return sprintRepository.findById(id);
	}

	@Override
	public Sprint save(Sprint sprint) {
		return sprintRepository.save(sprint);
	}
	
	
}
