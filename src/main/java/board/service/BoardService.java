package board.service;

import board.dto.BoardDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request);
    BoardDto selectBoardDetail(int boardIdx);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(int boardIdx);
}
