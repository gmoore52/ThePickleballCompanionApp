package edu.missouristate.thepickleballcompanionapp.dao;

import edu.missouristate.thepickleballcompanionapp.dao.custom.UserRepositoryCustom;
import edu.missouristate.thepickleballcompanionapp.domain.User;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {
    public List<User> findAll();

    public User findByUserName(String username);
    Boolean existsByUserName(String username);
}
