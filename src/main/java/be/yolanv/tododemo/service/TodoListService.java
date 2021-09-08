package be.yolanv.tododemo.service;

import be.yolanv.tododemo.model.TodoItem;

import java.util.List;

public interface TodoListService {
    List<TodoItem> getTodoItems();
    TodoItem addTodoItem(TodoItem todoItem);
    void deleteTodoItem(String description);
}
