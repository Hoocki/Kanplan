package ru.isu.webproject.kanplan.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.webproject.kanplan.exception.WrongBoardException;
import ru.isu.webproject.kanplan.exception.WrongMailException;
import ru.isu.webproject.kanplan.model.AutoUser;
import ru.isu.webproject.kanplan.model.Board;
import ru.isu.webproject.kanplan.model.Card;
import ru.isu.webproject.kanplan.model.Pillar;
import ru.isu.webproject.kanplan.service.AutoUserService;
import ru.isu.webproject.kanplan.service.BoardService;
import ru.isu.webproject.kanplan.service.CardService;
import ru.isu.webproject.kanplan.service.PillarService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private AutoUserService autoUserService;
   
    @GetMapping("/boards/userId={userId}")
    public ResponseEntity<List<Board>> getBoards(@PathVariable("userId") Long user_id){
        List<Board> boards = boardService.getUserBoards(user_id);      
        return ResponseEntity.ok(boards);
     }
    
    @GetMapping("/board/{boardId}")
    public ResponseEntity<Board> getBoard(@PathVariable("boardId") Long board_id) throws WrongBoardException {
        System.out.println("--->getBoard: " + board_id);
        Board board = boardService.getBoardById(board_id);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(board);
    }
    
    @GetMapping("/user/{mail}")
    public ResponseEntity<AutoUser> getUser(@PathVariable("mail") String mail) throws WrongMailException {
        AutoUser autoUser = autoUserService.getAutoUserByMail(mail);
        if (autoUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autoUser);
    }
    
    @PostMapping("/addUserOnBoard/{boardId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Board> addUserOnBoard(@PathVariable("boardId") Long board_id, @RequestBody AutoUser user) throws WrongBoardException, WrongMailException {
        AutoUser autoUser = autoUserService.getAutoUserByMail(user.getMail());
        if (autoUser == null) {
            return ResponseEntity.notFound().build();
        }
        Board updatedBoard = boardService.updateBoardUsers(board_id, user);        
        return ResponseEntity.ok(updatedBoard);
    }
    
    @PostMapping("/addBoard/{userId}")
        public Board createBoard(@PathVariable("userId") Long userId, @RequestBody Board board) {
            System.out.println("---> (createBoard) board.name: " + board.getName());            
        return boardService.addBoard(userId, board);
    }
    
    @PutMapping("/updateBoard/{boardId}")
	public ResponseEntity<Board> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody Board new_board) throws WrongBoardException{
	Board updatedBoard = boardService.updateBoardById(boardId, new_board);		
	return ResponseEntity.ok(updatedBoard);
	}
        
    @PostMapping("/deleteBoard/{boardId}")
	public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable("boardId") Long boardId) throws WrongBoardException{
	Map<String, Boolean> response = boardService.deleteBoardById(boardId);		
	return ResponseEntity.ok(response);
	}

}
