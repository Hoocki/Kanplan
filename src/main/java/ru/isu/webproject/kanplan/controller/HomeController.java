package ru.isu.webproject.kanplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.isu.webproject.kanplan.model.MessageResponse;


@RestController
@CrossOrigin(origins="http://localhost:4200")
public class HomeController {
//    @GetMapping("/")
//    public String startPage() {
//        return "Start page";
//    }

    @GetMapping("/admin")
    public ResponseEntity<?> helloAdmin() {
        return ResponseEntity.ok(new MessageResponse("Hello Admin"));
    }

//    @PostMapping("/admin/save")
//    public ResponseEntity<?> getAdminData(@RequestBody Project project) {
//        System.out.println("--> Saved:"+project);
//        return ResponseEntity.ok(new MessageResponse("Saved project "+project.getTitle()));
//    }
}
