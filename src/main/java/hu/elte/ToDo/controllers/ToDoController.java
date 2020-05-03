package hu.elte.ToDo.controllers;

import hu.elte.ToDo.entities.ToDo;
import hu.elte.ToDo.entities.User;
import hu.elte.ToDo.repositories.ToDoRepository;
import hu.elte.ToDo.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ToDoController {
    
    @Autowired
    private ToDoRepository todoRepository;
        
    
    @Autowired
    private UserRepository userRepository;
    
    private String selectedStatus = "";
    
    @RequestMapping("/")
    public String index() {
        return "main"; //main.html
    }
       
    @RequestMapping("/todos")
    public String list(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        model.addAttribute("name",   "WELCOME TO YOUR LIST ");
        model.addAttribute("todos", user.getTodos());
        model.addAttribute("selectedStatus", "");
        return "list"; //list.html
    }
    
    @RequestMapping("/todos/{id}")
    public String show(@PathVariable Integer id, Model model, Principal principal) throws Exception {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        
        Optional<ToDo> oToDo = todoRepository.findById(id);
        if (oToDo.isPresent()) {
            ToDo todo = oToDo.get();
            
            if (todo.getUser().getId() != user.getId()) {
                throw new Exception("Not authorized");
            }
            
            model.addAttribute("todo", todo);
            return "todo"; 
        }
        throw new Exception("No such todo");
    }
           
    @GetMapping("/todos/create")
    public String create(Model model) {
        model.addAttribute("todo", new ToDo());
        return "create-todo"; 
    }
    
    @PostMapping("/todos/create")
    public String store(@Valid ToDo todo, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create-todo";
        }
        
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        todo.setUser(user);
        
        todoRepository.save(todo);
        return "redirect:/todos";
    }
    

    @GetMapping("/todos/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) throws Exception {
        Optional<ToDo> oToDo = todoRepository.findById(id);
        if (oToDo.isPresent()) {
            ToDo todo = oToDo.get();
            model.addAttribute("todo", todo);
            return "edit-todo"; 
        }
        throw new Exception("No such todo");
    }
    

    @PostMapping("/todos/{id}/edit")
    public String store(@PathVariable Integer id, @Valid ToDo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-todo";
        }
        
        ToDo tododb = todoRepository.findById(id).get();
        todo.setUser(tododb.getUser());
        
        todoRepository.save(todo);
        return "redirect:/todos";
    }
    
    @GetMapping("/todos/{id}/delete")
    public String delete(@PathVariable Integer id) throws Exception {
        todoRepository.deleteById(id);
        return "redirect:/todos";
    }
    
    @GetMapping("/todos/{id}/check")
    String checkTodo(@PathVariable Integer id) {
        ToDo todo = todoRepository.findById(id).get();
                todo.setCompleted(true);
                todoRepository.save(todo);
                return "redirect:/todos";
    }
    
     @GetMapping("/todos/{id}/uncheck")
    String uncheckTodo(@PathVariable Integer id) {
        ToDo todo = todoRepository.findById(id).get();
                todo.setCompleted(false);
                todoRepository.save(todo);
                return "redirect:/todos";
    }
        
    @GetMapping("/todos/filter")
    public String category(@RequestParam String selectedStatus, Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).get();
        model.addAttribute("name", "WELCOME TO YOUR LIST ");
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("todos", user.getTodos());
        return "list"; //list.html
    }
}
