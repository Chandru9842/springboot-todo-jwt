package Dev.techtide.HelloWorld.repository;


import Dev.techtide.HelloWorld.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {


}
