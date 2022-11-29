package com.subhas.todo.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.subhas.todo.business.exception.TodoNotFoundException;
import com.subhas.todo.data.TodoRepository;
import com.subhas.todo.data.entity.Todo;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
	@Mock
	private TodoRepository todoRepository;

	@Autowired
	@InjectMocks
	private TodoService todoService;

	@Test
	@DisplayName("Test save one todo")
	void testSaveTodo() {
		Todo todoSample = new Todo(1, "Todo sample 1", true);
		when(todoRepository.save(any())).thenReturn(todoSample);
		Todo savedTodo = todoService.createTodo(todoSample);

		assertEquals(todoSample.getText(), savedTodo.getText());
		assertEquals(todoSample.getId(), savedTodo.getId());
		assertTrue(todoSample.isCompleted());
		verify(todoRepository).save(todoSample);
	}

	@Test
	@DisplayName("Test get one todo and it is present")
	void testGetOneAndPresent() {
		Todo sampleTodo = new Todo(3, "Todo Sample 3", false);
		when(todoRepository.findById(anyLong())).thenReturn(Optional.of(sampleTodo));

		Todo retrievedTodo = todoService.retrieveTodo(3);

		assertEquals(sampleTodo.getId(), retrievedTodo.getId());
		assertEquals(sampleTodo.getText(), retrievedTodo.getText());
		assertFalse(retrievedTodo.isCompleted());
		verify(todoRepository).findById(3L);
	}

	@Test
    @DisplayName("Test get one todo and it is not present")
    void testGetOneAndNotPresent() {
	when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());

	assertThrows(TodoNotFoundException.class, () -> todoService.retrieveTodo(3));
	verify(todoRepository).findById(3L);
    }

	@Test
	@DisplayName("Test find all todo")
	void testFindAll() {

		List<Todo> mockedList = List.of(new Todo(1, "Sample Text 1", false), new Todo(2, "Sample Text 2", false),
				new Todo(3, "Sample Text 3", true), new Todo(4, "Sample Text 4", true),
				new Todo(5, "Sample Text 5", false));

		when(todoRepository.findAll()).thenReturn(mockedList);

		List<Todo> retrievedList = todoService.listTodos(Optional.empty());

		assertEquals(mockedList.size(), retrievedList.size());
		verify(todoRepository).findAll();
	}

	@Test
	@DisplayName("Test find only completed todo")
	void testFindByIsCompletedOnlyCompleted() {
		List<Todo> mockedList = List.of(new Todo(1, "Sample Text 1", true), new Todo(2, "Sample Text 2", true),
				new Todo(3, "Sample Text 3", true), new Todo(4, "Sample Text 4", true),
				new Todo(5, "Sample Text 5", true));
		when(todoRepository.findByIsCompleted(true)).thenReturn(mockedList);

		List<Todo> retrievedList = todoService.listTodos(Optional.of(true));

		assertEquals(mockedList.size(), retrievedList.size());
		verify(todoRepository).findByIsCompleted(true);
	}

	@Test
	@DisplayName("Test find only not completed todo")
	void testFindByIsCompletedOnlyNotCompleted() {
		List<Todo> mockedList = List.of(new Todo(1, "Sample Text 1", false), new Todo(2, "Sample Text 2", false),
				new Todo(3, "Sample Text 3", false), new Todo(4, "Sample Text 4", false),
				new Todo(5, "Sample Text 5", false));
		when(todoRepository.findByIsCompleted(false)).thenReturn(mockedList);

		List<Todo> retrievedList = todoService.listTodos(Optional.of(false));

		assertEquals(mockedList.size(), retrievedList.size());
		verify(todoRepository).findByIsCompleted(false);
	}
}
