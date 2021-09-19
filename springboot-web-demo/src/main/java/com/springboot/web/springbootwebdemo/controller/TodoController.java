package com.springboot.web.springbootwebdemo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.web.springbootwebdemo.model.Todo;
import com.springboot.web.springbootwebdemo.service.TodoRepository;

@Controller
public class TodoController {
	
	@Autowired
	TodoRepository repository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodosList(ModelMap model) {
		String name = getLoggedInUserName();
		model.put("todos", repository.findByUser(name));
		//model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	private String getLoggedInUserName() {
	  Object principal = SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }
    
    return principal.toString();
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(), "Default Description", new Date(),false));
		return "todo";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
	  repository.deleteById(id);
	  //service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = repository.findById(id).get();
		//Todo todo = service.retrieveTodo(id);		
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUser(getLoggedInUserName());
		repository.save(todo);
		//service.updateTodo(todo);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUser(getLoggedInUserName());
		repository.save(todo);
		//service.addTodo(getLoggedInUserName(), todo.getDesc(), todo.getTargetDate(), false);
		
		return "redirect:/list-todos";
	}
}
