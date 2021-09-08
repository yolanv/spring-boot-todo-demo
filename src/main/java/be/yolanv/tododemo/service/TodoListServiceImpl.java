package be.yolanv.tododemo.service;

import be.yolanv.tododemo.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {
    private List<TodoItem> todoItems;

    public TodoListServiceImpl() {
        todoItems = new ArrayList<>();
    }

    @Override
    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    @Override
    public TodoItem addTodoItem(TodoItem todoItem) {
         todoItems.add(todoItem);
         return todoItem;
    }

    @Override
    public void deleteTodoItem(String description) {
        todoItems.removeIf(t -> t.getDescription().equals(description));
    }
}
