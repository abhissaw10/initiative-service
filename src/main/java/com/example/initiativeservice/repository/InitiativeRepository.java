package com.example.initiativeservice.repository;


import com.example.initiativeservice.entity.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative,Long> {

}
