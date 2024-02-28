package br.com.mbe.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mbe.todolist.domain.taskPriority.TaskPriority;

public interface ITaskPriorityRepository extends JpaRepository<TaskPriority, UUID>{

	TaskPriority findByEnabled(int i);

	TaskPriority findByPriority(String priority);

}
