package board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_jpa_board")
@Data
@DynamicUpdate
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int boardIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCnt=0;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDt;

    @Column(nullable = false,updatable = false)
    private String createdId;

    @UpdateTimestamp
    private LocalDateTime updateDt;

    private String updatorId;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="board_idx")
    private List<BoardFileEntity> fileInfoList;
}
