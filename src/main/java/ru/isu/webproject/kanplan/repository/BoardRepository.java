package ru.isu.webproject.kanplan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.webproject.kanplan.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT all_boards FROM Board all_boards JOIN all_boards.autoUsers my_autoUsers WHERE my_autoUsers.id = :user_id")
    List<Board> findBoardsByAutoUser(@Param("user_id") Long user_id);
    
    @Query("SELECT my_board FROM Board my_board WHERE my_board.id = :board_id")
    Board findBoardById(@Param("board_id") Long board_id);
}
