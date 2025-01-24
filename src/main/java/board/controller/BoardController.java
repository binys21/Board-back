package board.controller;

import board.dto.BoardDto;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

}
