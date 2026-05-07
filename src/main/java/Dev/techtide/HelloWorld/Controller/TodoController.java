package Dev.techtide.HelloWorld.Controller;

import Dev.techtide.HelloWorld.Service.TodoService;
import Dev.techtide.HelloWorld.models.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;
    @GetMapping("/get")
    String getTodo(){


        return "Todo";
    }
//    Path varaible
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Todo Retrieved Succesufully"),
            @ApiResponse(responseCode = "404",description = "Todo was not found!")
    })
    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        try {
            Todo createdTodo=todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo, HttpStatus.OK);
        }catch (RuntimeException exception){

            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping
    ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<List<Todo>>(todoService.getTodos(),HttpStatus.OK);
    }
//    request Parem
@GetMapping("/")
String getTodoByIdParam(@RequestParam("todoId") int id){
    return "Todo with Id"+id;
}
@GetMapping("/create")
String createUser(@RequestParam String userid,String password ){
    return "Todo with username"+userid+" "+"Password: "+password;
}

@PostMapping
ResponseEntity<Todo> createUser(@RequestBody Todo todo){

            Todo createdTodo=todoService.createTodo(todo);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);


}
@PutMapping
ResponseEntity<Todo> UpdateTodoById(@RequestBody Todo todo) {
    return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
}
    @DeleteMapping("/{id}")
    void DeleteTodoById(@PathVariable long id) {
        todoService.deleteTodoById(id);


    }
   @GetMapping("/page")
   ResponseEntity<Page<Todo>>getTodosPaged(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPages(page,size),HttpStatus.OK);


   }





}
