package edu.missouristate.thepickleballcompanionapp.service;

import edu.missouristate.thepickleballcompanionapp.dao.UserRepository;
import edu.missouristate.thepickleballcompanionapp.domain.dto.ProfileImageDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserDTO> getUsers();
    public UserDTO getUserByUserName(String username);
    public Boolean addUser(UserDTO userDTO);
    public Boolean uploadProfileImage(String username, String imageData);
}
