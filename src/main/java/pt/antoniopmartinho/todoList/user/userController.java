package pt.antoniopmartinho.todoList.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import at.favre.lib.crypto.bcrypt.BCrypt;
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private IuserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody userModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            //status code 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
     var PasswordHashed =  BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
         userModel.setPassword(PasswordHashed);
     var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }
}
