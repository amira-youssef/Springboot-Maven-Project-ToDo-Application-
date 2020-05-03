package hu.elte.ToDo.repositories;

import hu.elte.ToDo.entities.ToDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    
}
