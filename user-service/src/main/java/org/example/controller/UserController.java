package org.example.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.annotations.swagger.*;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @CreatedUserResponse
    @UserBadRequest //Кастомная аннотация для документации метода с не пройденной валидацией
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//Код 201
    @CircuitBreaker(name = "userService", fallbackMethod = "returnUserFallback")
    public EntityModel<UserDto> saveUser(@Valid @RequestBody UserDto userDto){
        return EntityModel.of(userService.saveUser(userDto),
                                getSelfLink(userDto.getId()),
                                getUpdateLink(),
                                getDeleteLink(userDto.getId()),
                                getUsersLink());
    }

    @Operation(summary = "Get user by ID", description = "Returns a single user")
    @OkFindUserResponse //Кастомная аннотация для документации метода с успешной работой над User'ом
    @UserNotFoundResponse //Кастомная аннотация для документации метода с не найденным User'ом
    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "returnUserFallback")
    public EntityModel<UserDto> findUserById(@PathVariable("id") Long id){
        return EntityModel.of(userService.findUserById(id),
                                getSelfLink(id),
                                getUpdateLink(),
                                getDeleteLink(id),
                                getUsersLink());
    }

    @Operation(summary = "Get all users", description = "Returns all users")
    @OkFindUsersResponse //Кастомная аннотация для документации метода с успешной работой поиска всех User'ов
    @GetMapping
    @CircuitBreaker(name = "userService", fallbackMethod = "returnUsersFallback")
    public CollectionModel<EntityModel<UserDto>> findAllUsers(){
        //Добавление каждому UserDto в Users нужных ссылок
        List<EntityModel<UserDto>> users = userService.findAllUsers().stream()
                .map(user -> EntityModel.of(user,
                                            getSelfLink(user.getId()),
                                            getUpdateLink(),
                                            getDeleteLink(user.getId())))
                .toList();
        return CollectionModel.of(users,
                                    getUsersLink().withSelfRel(),
                                    getCreateLink(new UserDto()));
    }

    @Operation(summary = "Update single user", description = "Updates user's information")
    @OkUpdateUserResponse
    @UserNotFoundResponse
    @UserBadRequest
    @PutMapping
    @CircuitBreaker(name = "userService", fallbackMethod = "returnUserFallback")
    public EntityModel<UserDto> updateUser(@Valid @RequestBody UserDto userDto){
        return EntityModel.of(userService.updateUser(userDto),
                                getSelfLink(userDto.getId()),
                                getDeleteLink(userDto.getId()),
                                getUsersLink());
    }

    @Operation(summary = "Delete user by Id", description = "Deletes single user")
    @NoContentDeleteResponse
    @UserNotFoundResponse
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//Код 204
    @CircuitBreaker(name = "userService", fallbackMethod = "returnEntityFallback")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().header(HttpHeaders.LINK, getUsersLink().toString()).build();
    }

    private Link getSelfLink(Long id){
        return linkTo(methodOn(UserController.class).findUserById(id)).withSelfRel().withType("GET");
    }
    private Link getUpdateLink(){
        return WebMvcLinkBuilder.linkTo(methodOn(UserController.class).updateUser(new UserDto())).withRel("update").withType("PUT");
    }
    private Link getDeleteLink(Long id){
        return WebMvcLinkBuilder.linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete").withType("DELETE");
    }
    private Link getUsersLink(){
        return linkTo(methodOn(UserController.class).findAllUsers()).withRel("users").withType("GET");
    }
    private Link getCreateLink(UserDto userDto){
        return linkTo(methodOn(UserController.class).saveUser(userDto)).withRel("save").withType("POST");
    }
    private EntityModel<UserDto> returnUserFallback(Exception e){
        return EntityModel.of(new UserDto(0L, "Service unavailable","", 503, LocalDateTime.now()));
    }
    private ResponseEntity<Void> returnEntityFallback(Exception e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).header(HttpHeaders.LINK, getUsersLink().toString()).build();
    }
    private CollectionModel<EntityModel<UserDto>> returnUsersFallback(Exception e){
        List<EntityModel<UserDto>> users = List.of(EntityModel.of(new UserDto(0L, "Service unavailable","", 503, LocalDateTime.now())));
        return CollectionModel.of(users,
                getUsersLink().withSelfRel(),
                getCreateLink(new UserDto()));
    }
}
