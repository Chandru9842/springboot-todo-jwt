package Dev.techtide.HelloWorld.Service;

import Dev.techtide.HelloWorld.models.Todo;
import Dev.techtide.HelloWorld.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

//Bean
//@Bean
@Service
public class TodoService {
    //Autowire
    @Autowired
    private TodoRepository todoRespository;

   public Todo createTodo(Todo todo){
       return todoRespository.save(todo);

   }
   public Todo getTodobyId(Long id){
       return todoRespository.getReferenceById(id);

   }
   public Todo getTodoById(Long id){
       return todoRespository.findById(id).orElseThrow(() ->new RuntimeException("Todo not found"));
   }
    public Page<Todo> getAllTodosPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRespository.findAll(pageable);
    }
   public List<Todo> getTodos(){
       return todoRespository.findAll();
   }
   public Todo updateTodo(Todo todo){
       return todoRespository.save(todo);

   }
    public void deleteTodoById(Long id){

         todoRespository.delete(getTodoById(id));

    }
    public void deleteTodo(Todo todo){

        todoRespository.delete(todo);

    }


}
