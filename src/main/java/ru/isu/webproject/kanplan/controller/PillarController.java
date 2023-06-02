package ru.isu.webproject.kanplan.controller;

import java.util.List;
import java.util.Map;
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
import ru.isu.webproject.kanplan.model.Pillar;
import ru.isu.webproject.kanplan.service.PillarService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class PillarController {
    
    @Autowired
    private PillarService pillarService;
    
    @GetMapping("/pillars/boardId={boardId}")
    public ResponseEntity<?> getPillars(@PathVariable("boardId")Long board_id) throws WrongBoardException{
        List<Pillar> pillars = pillarService.getBoardPillars(board_id);      
        return ResponseEntity.ok(pillars);
    }
    
    @GetMapping("/pillar/{pillarId}")
    public ResponseEntity<Pillar> getPillar(@PathVariable("pillarId") Long pillarId) throws WrongBoardException{        
        Pillar pillar = pillarService.getPillarById(pillarId);        
        return ResponseEntity.ok(pillar);
    }
    
    @PostMapping("/addPillar/{boardId}")
        public Pillar createPillar(@PathVariable("boardId") Long boardId,@RequestBody Pillar pillar) {
        return pillarService.addPillar(boardId, pillar);
    }
        
    @PutMapping("/updatePillar/{pillarId}")
	public ResponseEntity<Pillar> updatePillar(@PathVariable("pillarId") Long pillarId, @RequestBody Pillar new_pillar) throws WrongBoardException{
	Pillar updatedPillar = pillarService.updatePillarById(pillarId, new_pillar);		
	return ResponseEntity.ok(updatedPillar);
	}
    
    @PostMapping("/deletePillar/{pillarId}")
    public ResponseEntity<Map<String, Boolean>> deleteCard(@PathVariable("pillarId") Long pillarId) throws WrongBoardException{
        System.out.println("Delete PillarId: " + pillarId);
	Map<String, Boolean> response = pillarService.deletePillarById(pillarId);		
	return ResponseEntity.ok(response);
	}
}
