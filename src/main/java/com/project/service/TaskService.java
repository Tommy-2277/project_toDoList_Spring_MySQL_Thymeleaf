package com.project.service;

import com.project.helpers.StatusEnum;
import com.project.model.Task;
import com.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Create
    @Transactional
    public Task save(Task te){
        taskRepository.save(te);
        System.out.println("save successfully completed");
        return te;
    }

    //Read
    @Transactional
    public Optional<Task> findTaskById(Integer id){
        Optional<Task> taskById = taskRepository.findById(id);
        return taskById;
    }
    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Update
    @Transactional
    public void updateDescriptionAndTaskStatusById(String description, StatusEnum se, Integer id){
        taskRepository.updateDescriptionAndTaskStatusById(description, se, id);
        System.out.println("updateDescriptionAndTaskStatusById successfully completed");
    }

    //Delete
    @Transactional
    public void delete(Task task){
        taskRepository.delete(task);
        System.out.println("delete successfully completed");
    }
    @Transactional
    public void deleteById(Integer id){
        taskRepository.deleteById(id);
        System.out.println("deleteById successfully completed");
    }

    //paging
    public Page<Task> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return taskRepository.findAll(pageable);
    }
}
