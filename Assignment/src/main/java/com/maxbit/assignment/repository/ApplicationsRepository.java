package com.maxbit.assignment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maxbit.assignment.entity.UserApplication;

@Repository
public interface ApplicationsRepository extends CrudRepository<UserApplication, Long>{

	Optional<UserApplication> findByEmail(String email);

}
