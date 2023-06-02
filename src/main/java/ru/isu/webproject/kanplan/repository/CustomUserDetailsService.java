package ru.isu.webproject.kanplan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isu.webproject.kanplan.exception.WrongMailException;
import ru.isu.webproject.kanplan.model.AutoUser;
import ru.isu.webproject.kanplan.repository.AutoUserRepository;


@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	AutoUserRepository autoUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AutoUser user = autoUserRepository.findAutoUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}

		return user;
	}

}