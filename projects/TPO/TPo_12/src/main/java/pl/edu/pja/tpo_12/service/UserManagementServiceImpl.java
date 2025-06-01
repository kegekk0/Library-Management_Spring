package pl.edu.pja.tpo_12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo_12.model.Role;
import pl.edu.pja.tpo_12.model.User;
import pl.edu.pja.tpo_12.repository.RoleRepository;
import pl.edu.pja.tpo_12.repository.UserRepository;

import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserManagementServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void assignRole(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.addRole(role);
        userRepository.save(user);
    }

    @Override
    public void revokeRole(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.removeRole(role);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void activateUser(Long id) {
        User user = getUserById(id);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}