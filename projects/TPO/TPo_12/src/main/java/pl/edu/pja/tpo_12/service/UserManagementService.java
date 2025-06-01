package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.User;
import java.util.List;

public interface UserManagementService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void assignRole(Long userId, Long roleId);
    void revokeRole(Long userId, Long roleId);
    void deleteUser(Long id);
    void activateUser(Long id);
    void deactivateUser(Long id);
}