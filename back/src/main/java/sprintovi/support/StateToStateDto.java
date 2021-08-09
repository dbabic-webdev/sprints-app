package sprintovi.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.State;
import sprintovi.web.dto.StateDto;

@Component
public class StateToStateDto implements Converter<State, StateDto>{

	@Override
	public StateDto convert(State source) {
		StateDto dto = new StateDto();
		dto.setId(source.getId());
		dto.setName(source.getName());
		
		return dto;
	}
	
	public List<StateDto> convert(List<State> source){
		List<StateDto> retVal = new ArrayList<>();
		
		for(State s : source) {
			StateDto dto = convert(s);
			retVal.add(dto);
		}
		
		return retVal;
	}

}
