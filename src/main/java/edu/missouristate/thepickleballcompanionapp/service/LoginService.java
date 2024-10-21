package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.domain.dto.LoginDTO;

public interface LoginService {
    boolean validateUser(LoginDTO loginRequest);
}
