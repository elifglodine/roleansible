package com.example.role_ansible;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity<String> receiveRoleName(@RequestBody String roleName) {
        roleService.processRoleName(roleName);
        return ResponseEntity.ok("Role name received and processed");
    }
}