package com.example.demo.model.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board;
//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BlogRepository extends JpaRepository<Board, Long>{
    //List<Board> findAll();
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}