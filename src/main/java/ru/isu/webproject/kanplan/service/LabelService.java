package ru.isu.webproject.kanplan.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.Board;
import ru.isu.webproject.kanplan.model.Card;
import ru.isu.webproject.kanplan.model.Label;
import ru.isu.webproject.kanplan.repository.BoardRepository;
import ru.isu.webproject.kanplan.repository.CardRepository;
import ru.isu.webproject.kanplan.repository.LabelRepository;

@Service
public class LabelService {
    
    @Autowired
   private BoardRepository boardRepository;
        
    @Autowired
    private LabelRepository labelRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    public  List<Label> getLabelsByBoard(Long board_id) throws WrongBoardException {
        try {
            List<Label> labels = labelRepository.findLabelsByBoard(board_id); 
            return labels;
        } catch (Exception e) {
            throw new WrongBoardException("Board don't exist with id: " + board_id);        
        }
    }
    
    public  List<Label> getLabelsByCard(Long card_id) throws WrongBoardException {
        try {
            List<Label> labels = labelRepository.findLabelsByCard(card_id); 
            return labels;
        } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + card_id);        
        }
    }
    
    public Label addLabel(Long boardId,Label label) {
        Board board = boardRepository.findBoardById(boardId);
        if (label.getName() == null || label.getName() == "") {
            label.setName("");
        }        
        label.setBoard(board);
        return labelRepository.save(label);
    }
    
    public Set<Label> addLabelsInCard(Long cardId, Set<Long> labels) throws WrongBoardException {
        try {
            Set<Label> updatedLabels = new HashSet(); 
            for (Long label : labels) {
//                System.out.println("---> label: " + label);
        Label current = labelRepository.findLabelById(label);
        Card card = cardRepository.findCardById(cardId);
//        Board board = boardRepository.findBoardById(label.getBoard().getId());
//        label.setBoard(board);
        if (!current.getCards().contains(card)){
             current.getCards().add(card);
        }       
        Label updatedLabel = labelRepository.save(current);
        updatedLabels.add(updatedLabel);
            }
        return updatedLabels;
        } catch (Exception e) {
            throw new WrongBoardException("Card don't exist with id: " + cardId);        
        }
    }
    
    public Label updateLabelById (Long label_id, Label new_label) throws WrongBoardException {
         try {
            Label label = labelRepository.findLabelById(label_id);     
            if (new_label.getName() != null || new_label.getName() != "") {
                label.setName(new_label.getName());
            }
            Label updatedLabel = labelRepository.save(label);
            return updatedLabel;
        } catch (Exception e) {
            throw new WrongBoardException("Label don't exist with id: " + label_id);        
        }
    }
    
        public Map<String, Boolean> deleteLabelsById (Set<Long> labelsId) throws WrongBoardException{
        try {
            Map<String, Boolean> response = new HashMap<>();
            for (Long labelId : labelsId) {
            Label label = labelRepository.findLabelById(labelId);       
            labelRepository.delete(label);            
            response.put("deleted", Boolean.TRUE);
            }
            return response;
          } catch (Exception e) {
            throw new WrongBoardException("Label don't exist");        
        }
    }
    
}
