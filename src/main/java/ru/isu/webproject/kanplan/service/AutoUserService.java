package ru.isu.webproject.kanplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.webproject.kanplan.exception.WrongMailException;
import ru.isu.webproject.kanplan.model.AutoUser;
import ru.isu.webproject.kanplan.repository.AutoUserRepository;

@Service
public class AutoUserService {
    
    @Autowired
    private AutoUserRepository autoUserRepository; 
    
    public AutoUser getAutoUserByMail(String mail) throws WrongMailException {
        AutoUser autoUser = autoUserRepository.findAutoUserByUserMail(mail);
        if (autoUser == null || autoUser.getMail().equals("")) {
            throw new WrongMailException("User don't exist with mail: " + mail);
        }
        return autoUser;
    }
    
}
