package board.service;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardDto> selectBoardList() {
        return boardMapper.selectBoardList();
    }
    @Override
    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) {
        boardDto.setCreatedId("Lee"); //사용자 id로 변경하기
        boardMapper.insertBoard(boardDto);
        try {
            List<BoardFileDto> fileInfoList = fileUtils.parseFileInfo(boardDto.getBoardIdx(), request);
            if (!CollectionUtils.isEmpty(fileInfoList)) {
                boardMapper.insertBoardFileList(fileInfoList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }



    @Transactional
    @Override
    public BoardDto selectBoardDetail(int boardIdx){
        boardMapper.updateHitCnt(boardIdx);

        BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> boardFileInfoList = boardMapper.selectBoardFileList(boardIdx);
        boardDto.setFileInfoList(boardFileInfoList);

        return boardDto;
    }
    @Override
    public void updateBoard(BoardDto boardDto){
        boardDto.setUpdatorId("go");
        boardMapper.updateBoard(boardDto);
    }

    @Override
    public void deleteBoard(int boardIdx){
        BoardDto boardDto=new BoardDto();
        boardDto.setBoardIdx(boardIdx);
        boardDto.setUpdatorId("go");
        boardMapper.deleteBoard(boardDto);
    }
    @Override
    public BoardFileDto selectBoardFileInfo(int idx, int boardIdx){
        return boardMapper.selectBoardFileInfo(idx,boardIdx);
    }

}
