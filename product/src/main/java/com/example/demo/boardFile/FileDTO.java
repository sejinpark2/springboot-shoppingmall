package com.example.demo.boardFile;

import com.example.demo.boardFile.BoardFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class FileDTO {

    // ** 파일 경로
    private String filePath;

    // ** 파일 이름
    private String fileName;

    // ** uuid (랜덤 키)
    private String uuid;

    // ** 파일 포맷
    private String fileType;

    // ** 파일 크기
    private Long fileSize;

    // **  게시물 id
    private Long boardId;

    public BoardFile toEntity() {
        return BoardFile.builder()
                .filePath(filePath)
                .fileName(fileName)
                .uuid(uuid)
                .fileType(fileType)
                .fileSize(fileSize)
                .build();
    }
}
