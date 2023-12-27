package com.example.demo.entity;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Board {

    /*
     * 최초작성자 : 박세진
     * 최초작성일 : 2023.11.10.
     * 최종변경일 : 2023.11.27.
     * 목적 : 게시판 CRUD 처리하기
     * 개정이력 : 홍길동,2023.11.27,utf-8지원
     * 홍길동,2023.11.27,코드 오류수정 및 최적화
     */

    // ** pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String writer;

    // ** 게시물 제목
    @Column(length = 50)
    private String title;

    // ** 내용
    @Column(length = 50)
    private String contents;

    // ** 최초 작성 시간
    private LocalDateTime createTime;

    // ** 최근 수정 시간
    private LocalDateTime updateTime;

    // ** 1:다
    // ** 소유(One쪽) & 비소유(Many쪽)
    // ** cascade = CascadeType.REMOVE : 게시물이 삭제되면 댓글을 자동으로 지워준다.
    // ** orphanRemoval = true : 연결 관계가 끊어지면 삭제.
    // ** fetch = FetchType.LAZY : 지원로딩 (성능 최적화)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> files = new ArrayList<>();

    @ManyToOne
    private User user;

    @Builder
    public Board(Long id, String writer, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Board(BoardDTO dto, User user){
        this.title = dto.getTitle();
        this.user = user;
    }

    public void updateFromDTO(BoardDTO boardDTO){

        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
        this.updateTime = boardDTO.getUpdateTime();
    }

    public void updateFromUser(User user){
        this.user = user;
    }

}




