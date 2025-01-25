package board.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardDto {
    private int boardIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String createdDt;
    private String createdId;
    private String updatorDt;
    private String updatorId;

    //첨부 파일 정보 저장할 필드
    private List<BoardFileDto> fileInfoList;
}
