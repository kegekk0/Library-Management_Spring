package pl.edu.pja.tpo_12.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleManagementController {

    private final UserService userService;
    private final RoleService roleService;

    public RoleManagementController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showRoleManagementPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/role-management";
    }

    @PostMapping("/assign")
    public String assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        userService.assignRole(userId, roleId);
        return "redirect:/admin/roles?success";
    }

    @PostMapping("/revoke")
    public String revokeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        userService.revokeRole(userId, roleId);
        return "redirect:/admin/roles?success";
    }
}