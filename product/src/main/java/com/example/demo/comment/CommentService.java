package com.example.demo.comment;

import com.example.demo.comment.CommentDTO;
import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.board.BoardRepository;
import com.example.demo.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public Comment save(CommentDTO commentDTO) {
        Optional<Board> optionalBoard =
                boardRepository.findById(commentDTO.getBoardId());

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            Comment commet = commentDTO.toEntity();
            commet.toUpdate(board);
            return commentRepository.save(commet);
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId).get();
        java.util.List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment commentEntity : commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Transactional
    public Comment update(Long commentId, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.updateFromDTO(commentDTO);
            comment = commentRepository.save(comment);

            return comment;
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Comment comment = optionalComment.get();
        commentRepository.delete(comment);

    }
}


