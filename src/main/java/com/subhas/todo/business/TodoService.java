package com.subhas.todo.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subhas.todo.business.exception.TodoNotFoundException;
import com.subhas.todo.data.TodoRepository;
import com.subhas.todo.data.entity.Todo;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
	this.todoRepository = todoRepository;
    }

    public List<Todo> listTodos(Optional<Boolean> isCompleted) {
	if (isCompleted.isPresent()) {
	    return todoRepository.findByIsCompleted(isCompleted.get());
	}
	return todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
	return todoRepository.save(todo);
    }

    public Todo retrieveTodo(long id) {
	Optional<Todo> todoOptional = todoRepository.findById(id);
	if (todoOptional.isEmpty()) {
	    throw new TodoNotFoundException(id);
	}
	return todoOptional.get();
    }

}
