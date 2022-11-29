package com.subhas.todo.web;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subhas.todo.business.TodoService;
import com.subhas.todo.data.entity.Todo;

@RestController
@RequestMapping(path = "/api")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    ResponseEntity<List<Todo>> getAllTodos(@RequestParam Map<String, String> queryParams) {
	String completedQueryParam = queryParams.get("completed");
	Optional<Boolean> isCompleted = Optional.empty();
	if (Objects.nonNull(completedQueryParam)) {
	    isCompleted = Optional.ofNullable(Boolean.parseBoolean(completedQueryParam));
	}
	return new ResponseEntity<>(todoService.listTodos(isCompleted), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    ResponseEntity<Todo> getTodo(@PathVariable(required = true, value = "id") long id) {
	return new ResponseEntity<>(todoService.retrieveTodo(id), HttpStatus.OK);
    }

    @PostMapping("/todos")
    ResponseEntity<Todo> create(@RequestBody Todo todo) {
	return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }
}
