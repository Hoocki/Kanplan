package ru.isu.webproject.kanplan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.Card;
import ru.isu.webproject.kanplan.model.Pillar;
import ru.isu.webproject.kanplan.repository.CardRepository;
import ru.isu.webproject.kanplan.repository.PillarRepository;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private PillarRepository pillarRepository;
    
    public  List<Card> getPillarCards(Long pillar_id) throws WrongBoardException {
        try {
            List<Card> cards = cardRepository.findCardsByPillar(pillar_id); 
            return cards;
        } catch (Exception e) {
            throw new WrongBoardException("Pillar don't exist with id: " + pillar_id);        
        }
    }
    
    public Card getCardById(Long card_id) throws WrongBoardException {
        try {
            Card card = cardRepository.findCardById(card_id);        
            return card;
         } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + card_id);        
        }
    }

    public Card addCard(Long pillarId,Card card) {
        Pillar pillar = pillarRepository.findPillarById(pillarId);
        if (card.getName() == null || card.getName() == "") {
            card.setName("");
        }
        if (card.getDescription()== null || card.getDescription() == "") {
            card.setDescription("");
        }
        if (card.getCard_date()== null) {
            card.setCard_date(new Date());
        }
        card.setPillar(pillar);
        return cardRepository.save(card);
    }    
   
    public Card updateCardById (Long card_id, Card new_card) throws WrongBoardException {
         try {
            Card card = cardRepository.findCardById(card_id);     
            if (new_card.getName() != null && new_card.getName() != "") {
                card.setName(new_card.getName());
            }
            if (new_card.getDescription() != null && new_card.getDescription() != "") {
                card.setDescription(new_card.getDescription());
            }
            if (new_card.getCard_date() != null) {
                card.setCard_date(new_card.getCard_date());
            }
//            if (new_card.getLabels() != null) {
//                card.setLabels(new_card.getLabels());
//            }
    //        card.setPillar(new_card.getPillar());
            Card updatedEmployee = cardRepository.save(card);
            return updatedEmployee;
        } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + card_id);        
        }
    }
    
     public Card moveCardByPillar (Long card_id, Pillar new_pillar) throws WrongBoardException {
         try {
            Card card = cardRepository.findCardById(card_id);
            Pillar currentPillar = pillarRepository.findPillarById(new_pillar.getId());
            card.setPillar(currentPillar);

            Card updatedEmployee = cardRepository.save(card);
            return updatedEmployee;
         } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + card_id);        
        }
    }
    
    public Map<String, Boolean> deleteCardById (Long card_Id) throws WrongBoardException{
        try {
            Card card = cardRepository.findCardById(card_Id);       
            cardRepository.delete(card);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
          } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + card_Id);        
        }
    }
}
