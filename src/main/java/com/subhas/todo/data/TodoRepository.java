package com.subhas.todo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.subhas.todo.data.entity.Todo;

@Repository("todoRepository")
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("select t from Todo t where t.completed = ?1")
    List<Todo> findByIsCompleted(boolean isCompleted);
}
