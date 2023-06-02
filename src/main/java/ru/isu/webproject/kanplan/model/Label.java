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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "label")
@Getter 
@Setter 
@NoArgsConstructor
public class Label {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER) 
    private Board board;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "card_label", 
            joinColumns = @JoinColumn(name="label_id"), 
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private Set<Card> cards;
    
}
