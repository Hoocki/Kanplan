package ru.isu.webproject.kanplan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.model.AutoUser;
import ru.isu.webproject.kanplan.model.Board;
import ru.isu.webproject.kanplan.repository.AutoUserRepository;
import ru.isu.webproject.kanplan.repository.BoardRepository;

@Service
public class BoardService {
    
   @Autowired
   private BoardRepository boardRepository;
   
    @Autowired
   private AutoUserRepository autoUserRepository;
    
   public  List<Board> getUserBoards(Long user_id) {
        List<Board> boards = boardRepository.findBoardsByAutoUser(user_id); 
        return boards;
    }
   
    
   public Board getBoardById(Long boardId) throws WrongBoardException {
    try {
        Board board = boardRepository.findBoardById(boardId);
        return board;
    } catch (Exception e) {
        throw new WrongBoardException("Board don't exist with id: " + boardId);
    }
}

    
    public Board addBoard(Long userId,Board board) {
        System.out.println("---> (addBoard) board: " + board.getName());       
        AutoUser user = autoUserRepository.getAutoUserById(userId);
        System.out.println("---> user: " + user.getMail());
        board.getAutoUsers().add(user);
        return boardRepository.save(board);
    }
    
    public Board updateBoardById (Long board_Id, Board new_board) throws WrongBoardException {
        try {
            Board board = boardRepository.findBoardById(board_Id);
            board.setName(new_board.getName());     
            Board updatedBoard = boardRepository.save(board);        
            return updatedBoard;
        } catch (Exception e) {
            throw new WrongBoardException("Board don't exist with id: " + board_Id);        
        }
    }

    public Board updateBoardUsers (Long board_Id, AutoUser user) throws WrongBoardException {
        try {
            Board board = boardRepository.findBoardById(board_Id);
            AutoUser new_user = autoUserRepository.findAutoUserByUserMail(user.getMail());
            board.getAutoUsers().add(new_user);
            Board updatedBoard = boardRepository.save(board);       
            return updatedBoard ;
         } catch (Exception e) {
            throw new WrongBoardException("Board don't exist with id: " + board_Id);        
        }
    } 
   
    public Map<String, Boolean> deleteBoardById (Long board_Id) throws WrongBoardException{
        try {
            Board board = boardRepository.findBoardById(board_Id);	
            boardRepository.delete(board);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
         } catch (Exception e) {
            throw new WrongBoardException("Board don't exist with id: " + board_Id);        
        }
    }
   
}
