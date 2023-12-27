package com.example.demo.DTO;

import com.example.demo.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    private String writer;

    private String title;

    private String contents;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Board toEntity(){
        return Board.builder()
                .writer(writer)
                .title(title)
                .contents(contents)
                .createTime(createTime)
                .updateTime(updateTime)
                .build();
    }

    public static BoardDTO toBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getWriter(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime(),
                board.getUpdateTime());
    }
}
