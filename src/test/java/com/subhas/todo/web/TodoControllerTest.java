package com.subhas.todo.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhas.todo.business.TodoService;
import com.subhas.todo.business.exception.TodoNotFoundException;
import com.subhas.todo.data.entity.Todo;

@WebMvcTest
class TodoControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;

	@Test
	@DisplayName("Sucessfully get all the todos")
	void getAllTodos() throws Exception {
		List<Todo> todoList = new ArrayList<>();

		todoList.add(new Todo(1L, "Eat thrice", true));
		todoList.add(new Todo(2L, "Sleep early", false));
		todoList.add(new Todo(3L, "Follow TDD", true));
		when(todoService.listTodos(Optional.empty())).thenReturn(todoList);
		mockMvc.perform(get("/api/todos").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(3)))
				.andDo(print());
		verify(todoService).listTodos(Optional.empty());
	}

	@Test
	@DisplayName("Successfully get completed todos")
	void getAllCompletedTodos() throws Exception {
		List<Todo> todoList = new ArrayList<>();

		todoList.add(new Todo(1L, "Eat thrice", true));
		todoList.add(new Todo(2L, "Sleep early", true));
		todoList.add(new Todo(3L, "Following TDD", true));
		when(todoService.listTodos(Optional.of(true))).thenReturn(todoList);
		mockMvc.perform(get("/api/todos?completed=true").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3))).andDo(print());
		verify(todoService).listTodos(Optional.of(true));
	}

	@Test
	@DisplayName("Successfully get incomplete todos")
	void getAllNotCompletedTodos() throws Exception {
		List<Todo> todoList = new ArrayList<>();

		todoList.add(new Todo(1L, "Eat thrice", false));
		todoList.add(new Todo(3L, "Following TDD", false));
		when(todoService.listTodos(Optional.of(false))).thenReturn(todoList);
		mockMvc.perform(get("/api/todos?completed=false").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andDo(print());
		verify(todoService).listTodos(Optional.of(false));
	}

	@Test
	@DisplayName("Successfully create a Todo")
	void successfullyCreateTodo() throws Exception {
		Todo eatTodo = new Todo("Eat thrice", true);
		when(todoService.createTodo(any(Todo.class))).thenReturn(eatTodo);
		ObjectMapper objectMapper = new ObjectMapper();
		String eatTodoJson = objectMapper.writeValueAsString(eatTodo);
		ResultActions result = mockMvc
				.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content(eatTodoJson));
		result.andExpect(status().isCreated()).andExpect(jsonPath("$.text").value("Eat thrice"))
				.andExpect(jsonPath("$.completed").value(true));
		verify(todoService).createTodo(any(Todo.class));
	}

	@Test
	@DisplayName("Successfully retrieve a Todo")
	void getSpecificTodo() throws Exception {
		Todo testTodo = new Todo(23L, "Specific One", false);
		when(todoService.retrieveTodo(23)).thenReturn(testTodo);

		mockMvc.perform(get("/api/todos/23").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(23)).andExpect(jsonPath("$.text").value("Specific One"))
				.andDo(print());

		verify(todoService).retrieveTodo(23);
	}

	@Test
    @DisplayName("Return not found, when requested Todo is not found")
    void getUnavailableTodo() throws Exception {
	when(todoService.retrieveTodo(2L)).thenThrow(new TodoNotFoundException(2L));
	mockMvc.perform(get("/api/todos/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(jsonPath("$.status").value("NOT_FOUND"))
		.andExpect(jsonPath("$.errorMessage").value("Could not find Todo with id 2")).andDo(print());
	verify(todoService).retrieveTodo(2);
    }
}
