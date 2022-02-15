package it.unicam.cs.progetto.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findUserByEmail(String email);
}
