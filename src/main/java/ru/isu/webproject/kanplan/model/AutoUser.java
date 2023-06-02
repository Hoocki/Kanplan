//package ru.isu.webproject.kanplan.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import java.util.Collection;
//import java.util.Set;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Entity
//@Table(name = "auto_user")
//@Getter 
//@Setter 
//@NoArgsConstructor
//@ToString
//public class AutoUser implements UserDetails {
//    
//     
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @Column(name = "name")
//    private String name;
//    
//    @Column(name = "mail")
//    private String mail;
//    
//    @JsonIgnore
//    @Column(name = "password")
//    private String password;
//    
//    @JsonIgnore
//    @Column(name = "role")
//    private String role;
//    
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return AuthorityUtils.createAuthorityList(this.role);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//    
//    public AutoUser(String name, String password, String mail) {
//        this.name = name;
//        this.password = password;
//        this.mail = mail;
//    }
//
//  
//        
//}

package ru.isu.webproject.kanplan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "auto_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @JsonIgnore
    @Column(name = "role")
    private String role;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(this.role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AutoUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
        public AutoUser(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
}



