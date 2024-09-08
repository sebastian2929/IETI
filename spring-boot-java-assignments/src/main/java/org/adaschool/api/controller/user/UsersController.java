package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
       try{
           User user = new User(userDto);
           usersService.save(user);
           return new ResponseEntity<>(user,HttpStatus.CREATED);
       } catch (Exception ex) {
           Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try{
            List<User> userList = usersService.all();
            return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
        }catch (Exception ex){
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) throws UserNotFoundException{
        Optional<User> optionalUser = usersService.findById(id);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        Optional<User> optionalUser = usersService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.update(userDto);
            usersService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) throws UserNotFoundException {
        Optional<User> optionalUser = usersService.findById(id);
        if(optionalUser.isPresent()){
            usersService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            throw new UserNotFoundException(id);
        }
    }
}
