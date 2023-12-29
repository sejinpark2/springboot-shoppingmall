package com.example.demo.comment;

import com.example.demo.comment.CommentDTO;
import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {

        Comment comment = commentService.save(commentDTO);

        List<CommentDTO> all = commentService.findAll(commentDTO.getBoardId());

        if (comment != null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("게시글이 없음.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/{Id}")
    public ResponseEntity<List<CommentDTO>> list(@PathVariable Long boardId) {
        List<CommentDTO> comments = commentService.findAll(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/update/{Id}")
    public String commentUpdate(@ModelAttribute CommentDTO commentDTO) {
        commentService.update(commentDTO.getId(), commentDTO);
        return "redirect:/board/paging";
    }

    @PostMapping("/delete/{Id}")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return "redirect:/board/paging";
    }

}














