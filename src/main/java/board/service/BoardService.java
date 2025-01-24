package board.service;

import board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto);
}
