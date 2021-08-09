package sprintovi.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Sprint;
import sprintovi.web.dto.SprintDto;

@Component
public class SprintToSprintDto implements Converter<Sprint, SprintDto>{

	@Override
	public SprintDto convert(Sprint source) {
		SprintDto dto = new SprintDto();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setPoints(Integer.parseInt(source.getPoints()));
		
		return dto;
	}
	
	public List<SprintDto> convert(List<Sprint> source){
		List<SprintDto> retVal = new ArrayList<>();
		
		for(Sprint s : source) {
			SprintDto dto = convert(s);
			retVal.add(dto);
		}
		
		return retVal;
	}

}
