package board.controller;

import board.dto.BoardDto;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService; //서비스 계층 주입받음

    @GetMapping("/board/openBoardList.do")
    public ModelAndView openBoardList() throws Exception {
        //결과 렌더링 할 뷰 설정
        ModelAndView mv = new ModelAndView("/board/boardList");
        List<BoardDto> list =boardService.selectBoardList();
        mv.addObject("list",list);

        return mv;
    }
    //글쓰기 요청 처리 메서드
    @GetMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        return "/board/boardWrite";
    }
    //글 저장 요청 처리하는 메서드
    @PostMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) throws Exception {
        boardService.insertBoard(boardDto,request);
        return "redirect:/board/openBoardList.do";
    }
    @GetMapping("/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(@RequestParam("boardIdx") int boardIdx) throws Exception {
        BoardDto boardDto =boardService.selectBoardDetail(boardIdx);

        ModelAndView mv =new ModelAndView("/board/boardDetail");
        mv.addObject("board",boardDto);
        return mv;
    }

    //수정 요청 처리 메서드
    @PostMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto boardDto) throws Exception {
        boardService.updateBoard(boardDto);
        return "redirect:/board/openBoardList.do";
    }

    //삭제 요청 처리 메서드
    @PostMapping("/board/deleteBoard.do")
    public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }

}
