package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
}
