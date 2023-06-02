package ru.isu.webproject.kanplan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isu.webproject.kanplan.model.AutoUser;

public interface AutoUserRepository extends JpaRepository<AutoUser, Integer> {
    @Query("SELECT my_user FROM AutoUser my_user WHERE my_user.username = :username")
    AutoUser findAutoUserByUsername(@Param("username") String username);
    
    @Query("SELECT my_user FROM AutoUser my_user WHERE my_user.mail = :mail")
    AutoUser findAutoUserByUserMail(@Param("mail") String mail);
    
    @Query("SELECT my_user FROM AutoUser my_user WHERE my_user.id = :user_id")
    AutoUser getAutoUserById(@Param("user_id") Long user_id);
    
    boolean existsByUsername(String username);
}
