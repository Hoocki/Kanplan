package ru.isu.webproject.kanplan.model;

import java.time.LocalDate;
import java.util.Date;
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
@Table(name = "card")
@Getter 
@Setter 
@NoArgsConstructor
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "card_date")
    private Date card_date;
    
    @ManyToOne(fetch = FetchType.EAGER) 
    private Pillar pillar;
    
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "card_label", 
//            joinColumns = @JoinColumn(name="card_id"), 
//            inverseJoinColumns = @JoinColumn(name = "label_id"))
//    private Set<Label> labels;
    
}
