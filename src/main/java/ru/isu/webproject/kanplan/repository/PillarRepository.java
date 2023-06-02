package ru.isu.webproject.kanplan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.webproject.kanplan.model.Pillar;


@Repository
public interface PillarRepository extends JpaRepository<Pillar, Integer> {
    @Query("SELECT all_pillars FROM Pillar all_pillars WHERE all_pillars.board.id = :board_id")
    List<Pillar> findPillarsByBoard(@Param("board_id") Long board_id);
    
    @Query("SELECT my_pillar FROM Pillar my_pillar WHERE my_pillar.id = :pillar_id")
    Pillar findPillarById(@Param("pillar_id") Long pillar_id);
}
