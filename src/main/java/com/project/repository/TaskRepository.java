package com.project.repository;

import com.project.helpers.StatusEnum;
import com.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.description = ?1, t.status = ?2 WHERE t.id = ?3")
    void updateDescriptionAndTaskStatusById(String description, StatusEnum se, Integer id);


}
