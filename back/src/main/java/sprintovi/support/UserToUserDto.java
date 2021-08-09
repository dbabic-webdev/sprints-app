package sprintovi.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.User;
import sprintovi.web.dto.UserDto;

@Component
public class UserToUserDto implements Converter<User, UserDto>{

	@Override
	public UserDto convert(User source) {
		UserDto target = new UserDto();
		
		target.setId(source.getId());
		target.setUsername(source.getUsername());
		
		return target;
	}

	
}
