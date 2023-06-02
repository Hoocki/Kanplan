package ru.isu.webproject.kanplan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.Board;
import ru.isu.webproject.kanplan.model.Pillar;
import ru.isu.webproject.kanplan.repository.BoardRepository;
import ru.isu.webproject.kanplan.repository.PillarRepository;

@Service
public class PillarService {
    @Autowired
    private PillarRepository pillarRepository;
    
    @Autowired
    private BoardRepository boardRepository;
    
   public  List<Pillar> getBoardPillars(Long board_id) throws WrongBoardException {
        try {
            List<Pillar> pillars = pillarRepository.findPillarsByBoard(board_id); 
            return pillars;
     } catch (Exception e) {
            throw new WrongBoardException("Board don't exist with id: " + board_id);        
        }
    }
   
   public Pillar getPillarById(Long pillar_id) throws WrongBoardException {
       try {
        Pillar pillar = pillarRepository.findPillarById(pillar_id);     
        return pillar;
    } catch (Exception e) {
            throw new WrongBoardException("Pillar don't exist with id: " + pillar_id);        
        }
    }
   
   public Pillar updatePillarById (Long pillar_id, Pillar new_pillar) throws WrongBoardException {
       try {
            Pillar pillar = pillarRepository.findPillarById(pillar_id);
            pillar.setName(new_pillar.getName());   
            Pillar updatedBoard = pillarRepository.save(pillar);
            return updatedBoard;            
         } catch (Exception e) {
            throw new WrongBoardException("Pillar don't exist with id: " + pillar_id);        
        }
    } 
   
   public Map<String, Boolean> deletePillarById (Long pillar_Id) throws WrongBoardException{
        try {
            Pillar pillar = pillarRepository.findPillarById(pillar_Id);	
            pillarRepository.delete(pillar);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
          } catch (Exception e) {
            throw new WrongBoardException("Pillar don't exist with id: " + pillar_Id);        
        }
    }
   
    public Pillar addPillar(Long board_id, Pillar pillar) {      
        Board board = boardRepository.findBoardById(board_id);       
        pillar.setBoard(board);
        return pillarRepository.save(pillar);
    } 
}
