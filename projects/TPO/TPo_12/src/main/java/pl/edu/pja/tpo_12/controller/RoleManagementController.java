package pl.edu.pja.tpo_12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pja.tpo_12.service.RoleService;
import pl.edu.pja.tpo_12.service.UserManagementService;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleManagementController {

    private final UserManagementService userManagementService;
    private final RoleService roleService;

    @Autowired
    public RoleManagementController(UserManagementService userManagementService, RoleService roleService) {
        this.userManagementService = userManagementService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showRoleManagementPage(Model model) {
        model.addAttribute("users", userManagementService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/role-management";
    }

    @PostMapping("/assign")
    public String assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        userManagementService.assignRole(userId, roleId);
        return "redirect:/admin/roles?success";
    }

    @PostMapping("/revoke")
    public String revokeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        userManagementService.revokeRole(userId, roleId);
        return "redirect:/admin/roles?success";
    }
}