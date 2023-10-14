package com.test;

import com.project.Application;
import com.project.helpers.StatusEnum;
import com.project.model.Task;
import com.project.service.TaskService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {
    @Autowired
    public TaskService taskService;

    private static Integer id = -1;

    @Test
    @Order(1)
    public void whenTaskSavedThenCanGetTaskById(){
        Task task = new Task();
        task.setDescription("created amazing task");
        task.setStatus(StatusEnum.IN_PROGRESS);
        taskService.save(task);
        System.out.println(task.getId());
        assertNotEquals(task.getId(), 0);
        id = task.getId();
        Task taskFromDB = taskService.getTaskById(id);
        assertEquals(taskFromDB.getDescription(), "created amazing task");
        assertEquals(taskFromDB.getStatus(), StatusEnum.IN_PROGRESS);
    }

    @Test
    @Order(2)
    public void whenTaskSavedCanUpdateIt(){
        taskService.updateDescriptionAndTaskStatusById("updated amazing task", StatusEnum.PAUSED, id);
        assertEquals(taskService.getTaskById(id).getStatus(), StatusEnum.PAUSED);
        assertEquals(taskService.getTaskById(id).getDescription(), "updated amazing task");
    }

    @Test
    @Order(3)
    public void whenTaskSavedCanDeleteIt(){
        taskService.deleteById(id);
        RuntimeException re = assertThrows(RuntimeException.class, () -> taskService.getTaskById(id));
        assertEquals(re.getMessage(), "Task not found with id : " + id);
    }
}
