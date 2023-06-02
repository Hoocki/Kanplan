package ru.isu.webproject.kanplan.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.webproject.kanplan.model.Label;


@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {       
    @Query("SELECT my_label FROM Label my_label WHERE my_label.id = :label_id")
    Label findLabelById(@Param("label_id") Long label_id);
    
    @Query("SELECT my_labels FROM Label my_labels WHERE my_labels.board.id = :board_id")
    List<Label> findLabelsByBoard(@Param("board_id") Long board_id);
    
    @Query("SELECT all_labels FROM Label all_labels JOIN all_labels.cards my_card WHERE my_card.id = :card_id")
    List<Label> findLabelsByCard(@Param("card_id") Long card_id);
    
    
}
