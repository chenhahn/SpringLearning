package com.springboot.web.springbootwebdemo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.springbootwebdemo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{
   List<Todo> findByUser(String user);
}
