package ru.isu.webproject.kanplan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.webproject.kanplan.model.Card;


@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    @Query("SELECT all_cards FROM Card all_cards WHERE all_cards.pillar.id = :pillar_id")
    List<Card> findCardsByPillar(@Param("pillar_id") Long pillar_id);
    
    @Query("SELECT my_card FROM Card my_card WHERE my_card.id = :card_id")
    Card findCardById(@Param("card_id") Long card_id);
}
