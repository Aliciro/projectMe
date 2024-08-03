package com.example.projectMe.Service;

import com.example.projectMe.Entity.Project;
import com.example.projectMe.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public  Project getProjectById(Long id) {
        return projectRepository.findById(id).get();
    }

    public void updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id);
        if (project != null) {
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            projectRepository.save(project);
        }
    }
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);

    }


}
