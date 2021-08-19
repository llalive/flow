package dev.lochness.flow.controller;

import dev.lochness.flow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController extends AuthenticatedUserContoller {

    private final TaskService taskService;

    @GetMapping("/")
    public String taskList(Model model) {
        model.addAttribute("tasks", taskService.getTasksForToday(getCurrentUser()));
        model.addAttribute("user", getCurrentUser());
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin";
    }
}
