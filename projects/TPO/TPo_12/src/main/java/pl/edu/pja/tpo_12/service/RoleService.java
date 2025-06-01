package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role saveRole(Role role);
    Role updateRole(Long id, Role role);
    void deleteRole(Long id);
    Role getRoleByName(String name);
}