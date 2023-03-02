package com.vjykhade.springboot.practical.web.controller;

import com.vjykhade.springboot.practical.web.dao.UserDeoService;
import com.vjykhade.springboot.practical.web.entity.User;
import com.vjykhade.springboot.practical.web.exceptionhandling.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDeoService userDeoService;


    @GetMapping("/users")
    public List<User> retrieveAllUser()
    {
        return userDeoService.findAll();
    }


    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id)
    {
        User user = userDeoService.findOne(id);
        if (user==null)
            throw new UserNotFoundException("Id: "+id);
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkBuilder =  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
        User savedUser = userDeoService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable int id)
    {
        userDeoService.deleteById(id);
    }
}
