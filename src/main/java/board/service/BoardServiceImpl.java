package board.service;

import board.dto.BoardDto;
import board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardDto> selectBoardList() {
        return boardMapper.selectBoardList();
    }
    @Override
    public void insertBoard(BoardDto boardDto) {
        boardDto.setCreatedId("Lee"); //사용자 id로 변경하기
        boardMapper.insertBoard(boardDto);
    }
    @Override
    public BoardDto selectBoardDetail(int boardIdx){
        boardMapper.updateHitCnt(boardIdx);
        return boardMapper.selectBoardDetail(boardIdx);
    }

}
