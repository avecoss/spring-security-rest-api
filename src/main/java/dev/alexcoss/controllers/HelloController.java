package dev.alexcoss.controllers;

import dev.alexcoss.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }
}
