package ru.isu.webproject.kanplan.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.Board;
import ru.isu.webproject.kanplan.model.Label;
import ru.isu.webproject.kanplan.service.BoardService;
import ru.isu.webproject.kanplan.service.LabelService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class LabelController {
    
    
    @Autowired
    private LabelService labelService;
    
    @Autowired
    private BoardService boardService;
    
    @GetMapping("/labels/boardId={boardId}")
    public ResponseEntity<List<Label>> getLabels(@PathVariable("boardId") Long boardId) throws WrongBoardException{
        List<Label> labels = labelService.getLabelsByBoard(boardId);
        return ResponseEntity.ok(labels);
    }
    
    @GetMapping("/labels/cardId={cardId}")
    public ResponseEntity<?> getLabelsForCard(@PathVariable("cardId") Long cardId) throws WrongBoardException{
        List<Label> labels = labelService.getLabelsByCard(cardId);
        return ResponseEntity.ok(labels);
    }
    
     @PostMapping("/addLabel/{boardId}")
        public Label createLabel(@PathVariable("boardId") Long boardId, @RequestBody Label label) {
        return labelService.addLabel(boardId, label);
    }
        
    @PostMapping("/addLabelsOnCard/{cardId}")
        public ResponseEntity<Set<Label>> addLabelOnCard(@PathVariable("cardId") Long cardId, @RequestBody Set<Long> labels) throws WrongBoardException {
            for (Long label : labels) { 
                System.out.println("-->label: " + label);
            }
        Set<Label> updatedLabels = labelService.addLabelsInCard(cardId, labels);
        return ResponseEntity.ok(updatedLabels);
    }    
        
    @PutMapping("/updateLabel/{labelId}")
	public ResponseEntity<Label> updateLabel(@PathVariable("labelId") Long labelId, @RequestBody Label new_label) throws WrongBoardException{
	Label updatedLabel = labelService.updateLabelById(labelId, new_label);		
	return ResponseEntity.ok(updatedLabel);
    }
        
    @PostMapping("/deleteLabels")
    public ResponseEntity<Map<String, Boolean>> deleteLabel(@RequestBody Set<Long> labelsId) throws WrongBoardException{
        Map<String, Boolean> response = labelService.deleteLabelsById(labelsId);		
        return ResponseEntity.ok(response);
    }
    
}
