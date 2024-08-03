package com.example.projectMe.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.projectMe.Entity.Task;
import com.example.projectMe.Repository.TaskRepository;

import java.util.List;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setProject(updatedTask.getProject());
            taskRepository.save(task);
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}
