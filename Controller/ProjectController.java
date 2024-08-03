package com.example.projectMe.Controller;


import com.example.projectMe.Entity.Project;
import com.example.projectMe.Entity.Task;
import com.example.projectMe.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectMe.Service.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;


@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

   // @Autowired
   // private UserDetailsService userDetailsService;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "index";
    }

    @GetMapping("/projects")
    public String viewProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";

    }

    @GetMapping("/projects/create")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        // Add logic to handle project creation form
        return "create_project";
    }

    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{id}")
    public String editProjectForm(@PathVariable("id") Long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        // Add logic to handle project edit form
        return "edit_project";
    }

    @PostMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") Long id, @ModelAttribute Project project) {
        Project existingProject = projectService.getProjectById(id);

        if (existingProject != null) {
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setStartDate(project.getStartDate());
            existingProject.setEndDate(project.getEndDate());

            projectService.saveProject(existingProject);
        } else {
            return "redirect:/projects"; // Or show an error page
        }

        return "redirect:/projects";
    }


    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id, Model model) {
        projectService.deleteProject(id);

        // Add logic to handle project deletion
        return "redirect:/projects";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("message", "Project deleted successfully!");
        return "redirect:/projects";
    }


    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "tasks";
    }

    @GetMapping("/tasks/project/{projectId}")
    public String viewProjectTasks(@PathVariable("projectId") Long projectId, Model model) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("project", project);
        return "project_tasks";
    }

    @GetMapping("/tasks/project/{projectId}/create")
    public String createTaskForm(@PathVariable("projectId") Long projectId, Model model) {
        Task task = new Task();
        task.setProject(projectService.getProjectById(projectId));
        model.addAttribute("task", task);
        return "create_task";
    }

    @PostMapping("/tasks/project/{projectId}/create")
    public String createTask(@PathVariable("projectId") Long projectId, @ModelAttribute Task task) {
        task.setProject(projectService.getProjectById(projectId));
        taskService.saveTask(task);
        return "redirect:/tasks/project/" + projectId;
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "edit_task";
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, @ModelAttribute Task task) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask != null) {
            existingTask.setName(task.getName());
            existingTask.setDescription(task.getDescription());
            existingTask.setStartDate(task.getStartDate());
            existingTask.setEndDate(task.getEndDate());
            //existingTask.setProject(task.getProject());
            taskService.saveTask(existingTask);
        }
        else {
            return "redirect:/tasks/project/";
        }
        return "redirect:/tasks/project/" + existingTask.getProject().getId();
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        Task task = taskService.getTaskById(id);
        Long projectId = task.getProject().getId();
        taskService.deleteTask(id);
        return "redirect:/tasks/project/" + task.getProject().getId();
    }

    @GetMapping("/settings")
    public String viewSettings() {

        return "settings"; // Maps to settings.html
    }

/*
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        // In a real application, you would save the user to a database.
        // For in-memory, just simulate a successful registration.
        redirectAttributes.addFlashAttribute("message", "Registration successful! You can now log in.");
        return "redirect:/login";
    }
*/



}
