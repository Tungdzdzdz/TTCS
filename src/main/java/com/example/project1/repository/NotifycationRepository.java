package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project1.Model.Notifycation;
import com.example.project1.Model.User;

@Repository
public interface NotifycationRepository extends JpaRepository<Notifycation, Long>{
    @Query(nativeQuery = true, value = "SELECT * FROM notifycations WHERE user_id = ?1 ORDER BY id DESC OFFSET ?2 ROWS FETCH NEXT 10 ROWS ONLY")
    List<Notifycation> findByUserOrderByIdDesc(Integer userId, Integer from);   
    List<Notifycation> findByUser(User user);
}
