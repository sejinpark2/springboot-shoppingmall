package com.example.demo.boardFile;

import com.example.demo.boardFile.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<BoardFile, Long> {
    List<BoardFile> findByBoardId(Long boardId);
}
