package com.subhas.todo.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private long id;
    private String text;
    private boolean completed;

    public Todo() {

    }

    public Todo(long id, String text, boolean completed) {
	this.id = id;
	this.text = text;
	this.completed = completed;
    }

    public Todo(String text, boolean completed) {
	this.text = text;
	this.completed = completed;
    }

    public String getText() {
	return text;
    }

    public boolean isCompleted() {
	return completed;
    }

    public long getId() {
	return id;
    }

}
