package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//Все нужные методы уже описаны в JpaRepository. (save, findAll/id и т.д.)
public interface UserRepository extends JpaRepository<User, Long> {

}
