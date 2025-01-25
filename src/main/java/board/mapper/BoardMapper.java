package board.mapper;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto);
    BoardDto selectBoardDetail(int boardIdx);
    void updateHitCnt(int boardIdx);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(BoardDto boardDto);
    void insertBoardFileList(List<BoardFileDto> fileInfoLIst);
    List<BoardFileDto> selectBoardFileList(int boardIdx);
}
