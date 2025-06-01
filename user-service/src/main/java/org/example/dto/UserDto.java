package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UserDto {

    @Schema(description = "User's ID", example = "1")
    private Long id;

    @Schema(description = "User's full name", example = "John Doe")
    @NotNull
    private String name;

    @Schema(description = "User's email", example = "example@example.ru")
    @NotNull
    @Email(regexp = "\\w+@\\w+\\.\\w+", message = "Invalid Email")
    private String email;

    @Schema(description = "User's age", example = "23")
    @Min(value = 1, message = "Age must be at least 1")
    private int age;

    @Schema(description = "User's time of creation", example = "2025-05-12T13:16:24.775222")
    private LocalDateTime createdAt;

    public UserDto(){}
    public UserDto(Long id, String name, String email, int age, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                '}';
    }
}
