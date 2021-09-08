package be.yolanv.tododemo.controller;

import be.yolanv.tododemo.model.TodoItem;
import be.yolanv.tododemo.service.TodoListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {
    private final TodoListService todoListService;

    public TodoController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/")
    public String getTodos(Model model) {
        List<TodoItem> todos = todoListService.getTodoItems();
        model.addAttribute("todos", todos);
        return "index";
    }

    @PostMapping("/todo/create")
    public String addTodo(@RequestParam String todoDescription, Model model) {
        TodoItem add = new TodoItem(todoDescription);
        todoListService.addTodoItem(add);
        model.addAttribute("todos", todoListService.getTodoItems());
        return "redirect:/";
    }

    @PostMapping("/todo/delete/{description}")
    public String delete(@PathVariable String description, Model model) {
        todoListService.deleteTodoItem(description);
        model.addAttribute("todos", todoListService.getTodoItems());
        return "redirect:/";
    }
}
