package com.project.controller;

import com.project.helpers.StatusEnum;
import com.project.model.Task;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Controller
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @PostMapping("/createTask")
    public String createNewTask(@ModelAttribute("task") Task task) {
        taskService.save(task);
        return "redirect:/";
    }


    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable(value = "id") int id){
        taskService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute("taskForUpdate") Task task){
        taskService.save(task);
        return "redirect:/";
    }

    @GetMapping("/main/{pageNum}")
    public String findPaginated(@PathVariable(value = "pageNum") int pageNum,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDirection") String sortDirection,
                                Model model) {
        int pageSize = 5;

        Page<Task> page = taskService.findPaginated(pageNum, pageSize, sortField, sortDirection);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("listTasks", page.getContent());

        Task task = new Task();
        model.addAttribute("task", task);
        Task taskForUpdate = new Task();
        model.addAttribute("taskForUpdate", taskForUpdate);

        model.addAttribute("statuses", Arrays.asList(StatusEnum.values()));
        return "first_page";
    }

}
