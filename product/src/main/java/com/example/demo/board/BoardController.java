package com.example.demo.controller;


import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.entity.BoardFile;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.BoardService;
import com.example.demo.DTO.BoardDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final FileRepository fileRepository;

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @GetMapping(value = {"/paging", "/"})
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boards = boardService.paging(pageable);

        // 보여지는 페이지 수
        int blockLimit = 3;

        // 보여지는 페이지 시작 번호
        int startPage = (int) (Math.ceil((double) pageable.getPageNumber() / blockLimit) - 1) * blockLimit + 1;

        // 보여지는 페이지 끝 번호
        int endPage = ((startPage + blockLimit - 1) < boards.getTotalPages()) ? (startPage + blockLimit - 1) : boards.getTotalPages();

        model.addAttribute("boardList", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        List<BoardFile> boardFiles = fileRepository.findByBoardId(id);
        model.addAttribute("files", boardFiles);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO,
                         @RequestParam MultipartFile[] files) throws IOException {
        boardService.update(boardDTO, files);
        return "redirect:/board/";
    }

    @GetMapping("/{id}")
    public String paging(@PathVariable Long id, Model model, @PageableDefault(page = 1) Pageable pageable) {
        BoardDTO boardDTO = boardService.findById(id);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        List<BoardFile> byBoardFiles = fileRepository.findByBoardId(id);
        model.addAttribute("files", byBoardFiles);

        return "detail";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO,
                       @RequestParam MultipartFile[] files,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        boardDTO.setCreateTime(LocalDateTime.now());
        boardService.save(boardDTO, files, customUserDetails.getUser());

        return "redirect:/board/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/paging";
    }
}


