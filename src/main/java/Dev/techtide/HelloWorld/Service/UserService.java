package Dev.techtide.HelloWorld.Service;

import Dev.techtide.HelloWorld.models.User;
import Dev.techtide.HelloWorld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

//Bean
//@Bean
@Service
public class UserService {
    //Autowire
    @Autowired
    private UserRepository userRepository;

   public User createUser(User user){
       return userRepository.save(user);

   }

   public User getUserById(Long id){
       return userRepository.findById(id).orElseThrow(() ->new RuntimeException("User not found"));
   }



}
