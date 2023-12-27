package com.example.demo.service;

import com.example.demo.entity.BoardFile;
import com.example.demo.repository.BoardRepository;
import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;

import com.example.demo.repository.FileRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    String filePath = "C:/Users/G/Desktop/green/Board Files/";

    @Transactional
    public void save(BoardDTO dto, MultipartFile[] files, User user) throws IOException {

        // ** 게시글 DB에 저장 후 pk을 받아옵니다.
        Long id = boardRepository.save(dto.toEntity()).getId();
        Board board = boardRepository.findById(id).get();
        board.updateFromUser(user);


        // 추가
        if (!files[0].isEmpty()) {
            Path uploadPath = Paths.get(filePath);

            // 만약 경로가 없다면... 경로 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            for (MultipartFile file : files) {
                // 파일명 추출
                String originalFilename = file.getOriginalFilename();

                // 확장자 추출
                String formatType = originalFilename.substring(
                        originalFilename.lastIndexOf("."));

                // UUID 생성
                String uuid = UUID.randomUUID().toString();

                // 경로 지정
                String path = filePath + uuid + originalFilename;

                // 파일을 물리적으로 저장 (DB에 저장 X)
                file.transferTo(new File(path));

                BoardFile boardFile = BoardFile.builder()
                        .filePath(filePath)
                        .fileName(originalFilename)
                        .uuid(uuid)
                        .fileType(formatType)
                        .fileSize(file.getSize())
                        .board(board)
                        .build();

                fileRepository.save(boardFile);
            }
        }
    }

    public BoardDTO findById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            return BoardDTO.toBoardDTO(board);
        }
        return null;
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int size = 5;
        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, size));
        return boards.map(board -> new BoardDTO(
                board.getId(),
                board.getWriter(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime(),
                board.getUpdateTime()));
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(BoardDTO boardDTO, MultipartFile[] files) throws IOException {
        Optional<Board> boardOptional = boardRepository.findById(boardDTO.getId());

        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.updateFromDTO(boardDTO);

            List<BoardFile> existingFiles = fileRepository.findByBoardId(board.getId()  );
            for (BoardFile existingFile : existingFiles) {
                deleteFile(existingFile);
                fileRepository.delete(existingFile);
            }

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String originalFileName = file.getOriginalFilename();
                        String formatType = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String uuid = UUID.randomUUID().toString();
                        String path = filePath + uuid + originalFileName;
                        file.transferTo(new File(path));

                        BoardFile boardFile = BoardFile.builder()
                                .filePath(filePath)
                                .fileName(originalFileName)
                                .uuid(uuid)
                                .fileType(formatType)
                                .fileSize(file.getSize())
                                .board(board)
                                .build();

                        fileRepository.save(boardFile);
                    }
                }
            }
            boardRepository.save(board);
        }
    }

    private void deleteFile(BoardFile boardFile) {
        String filePath = boardFile.getFilePath() + boardFile.getUuid() + boardFile.getFileName();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}




