package ru.isu.webproject.kanplan.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.webproject.kanplan.model.Card;
import ru.isu.webproject.kanplan.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.Pillar;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @GetMapping("/cards/pillarId={pillarId}")
    public ResponseEntity<?> getCards(@PathVariable("pillarId") Long pillar_id) throws WrongBoardException{
        List<Card> cards = cardService.getPillarCards(pillar_id);
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/card/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable("cardId") Long cardId) throws WrongBoardException{        
        Card card = cardService.getCardById(cardId);        
        return ResponseEntity.ok(card);
    }
    
    @PostMapping("/addCard/{pillarId}")
        public Card createCard(@PathVariable("pillarId") Long pillarId, @RequestBody Card card) {
        return cardService.addCard(pillarId, card);
    }
    
    @PutMapping("/updateCard/{cardId}")
	public ResponseEntity<Card> updateCard(@PathVariable("cardId") Long cardId, @RequestBody Card new_card) throws WrongBoardException{
        System.out.println("Card_name: " + new_card.getName());
	Card updatedCard = cardService.updateCardById(cardId, new_card);		
	return ResponseEntity.ok(updatedCard);
    }
        
    @PutMapping("/moveCard/{cardId}")
	public ResponseEntity<Card> moveCard(@PathVariable("cardId") Long cardId, @RequestBody Pillar new_pillar) throws WrongBoardException{
	Card updatedCard = cardService.moveCardByPillar(cardId, new_pillar);		
	return ResponseEntity.ok(updatedCard);
    }
        
    @PostMapping("/deleteCard/{cardId}")
	public ResponseEntity<Map<String, Boolean>> deleteCard(@PathVariable("cardId") Long cardId) throws WrongBoardException{
            System.out.println("CardId: " + cardId);
	Map<String, Boolean> response = cardService.deleteCardById(cardId);		
	return ResponseEntity.ok(response);
	}
    
}
