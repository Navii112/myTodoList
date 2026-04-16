package service;

import repository.TodoRepository;
import vo.Todo;

import java.util.List;
import java.util.Map;

public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void addTodo(String date, String time, String task) {
        System.out.println("[TodoService.addTodo()]");

        Todo todo = new Todo(time, task, false);
        System.out.println("생성된 할 일: " + todo);

        todoRepository.add(date, todo);

        System.out.println("저장소에 할 일 추가 지시 완료");
    }

    @Override
    public List<Todo> getTodosByDate(String date) {
        System.out.println("[TodoService.getTodosByDate()]");

        // List.of() 대신, Repository에서 실제 데이터를 가져와서 반환합니다.
        // Repository의 메서드 이름이 이전 코드 기준 findByDate 였으므로 이에 맞춥니다.
        return todoRepository.findByDate(date);
    }

    @Override
    public Map<String, List<Todo>> getAllTodos() {
        System.out.println("[TodoService.getAllTodos()]");

        // Map.of() 대신, Repository에서 실제 전체 데이터를 가져옵니다.
        return todoRepository.findAll();
    }

    @Override
    public void updateTodo(String date, int index, String time, String task) {
        System.out.println("[TodoService.updateTodo()]");

        // 1. 수정할 내용이 담긴 새로운 Todo 객체를 만듭니다.
        // (수정 시 완료 여부는 기존 것을 유지하거나 초기화해야 하지만, 여기선 false로 세팅합니다)
        Todo updatedTodo = new Todo(time, task, false);

        // 2. Repository에 날짜, 번호, 그리고 새로 만든 객체를 넘겨서 바꿔치기(update)를 지시합니다.
        todoRepository.update(date, index, updatedTodo);
    }

    @Override
    public void deleteTodo(String date, int index) {
        System.out.println("[TodoService.deleteTodo()]");

        // Repository에 해당 날짜의 특정 번호를 지우라고 지시합니다.
        todoRepository.delete(date, index);
    }

    @Override
    public void completeTodo(String date, int index) {
        System.out.println("[TodoService.completeTodo()]");

        // Repository에 해당 날짜의 특정 번호를 완료 처리하라고 지시합니다.
        todoRepository.complete(date, index);
    }
}