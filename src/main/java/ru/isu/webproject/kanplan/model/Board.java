package ru.isu.webproject.kanplan.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "board")
@Getter 
@Setter 
@NoArgsConstructor
public class Board {
    
      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auto_user_board", 
            joinColumns = @JoinColumn(name="board_id"), 
            inverseJoinColumns = @JoinColumn(name = "auto_user_id"))
    private Set<AutoUser> autoUsers;
        
}
