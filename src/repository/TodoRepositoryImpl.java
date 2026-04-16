package repository;

import vo.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoRepositoryImpl implements TodoRepository {
    private Map<String, List<Todo>> todoMap = new HashMap<>();

    public TodoRepositoryImpl() {
    }

    public Map<String, List<Todo>> getTodoMap() {
        return todoMap;
    }

    public void setTodoMap(Map<String, List<Todo>> todoMap) {
        this.todoMap = todoMap;
    }

    @Override
    public void add(String date, Todo todo) {
        System.out.println("[TodoRepositoryImpl.add()]");
        // 맵에 키가 없다면 빈 리스트 생성
        if (!todoMap.containsKey(date)) {
            todoMap.put(date, new ArrayList<>());
        }
        // 가져온 todo를 리스트에 추가하기
        // ListMTodo> todoList = todoMap.get(date);
        // todoList.add(todo);
        todoMap.get(date).add(todo);


        System.out.println("할 일 추가 완료");
    }

    @Override
    public List<Todo> findByDate(String date) {
        System.out.println("[TodoRepositoryImpl.findByDate()]");

        if (!todoMap.containsKey(date)) {
            return new ArrayList<>();
        }
        return todoMap.get(date);
    }

    @Override
    public Map<String, List<Todo>> findAll() {
        System.out.println("[TodoRepositoryImpl.findAll()]");

        // Map.of() 대신, 전체 데이터가 담긴 todoMap 자체를 반환합니다.
        return this.todoMap;
    }

    @Override
    public void update(String date, int index, Todo todo) {
        System.out.println("[TodoRepositoryImpl.update()]");

        List<Todo> todos = this.todoMap.get(date);

        // 해당 날짜에 리스트가 있고, 사용자가 입력한 번호(index)가 유효한 범위일 때만 수정
        if (todos != null && index >= 0 && index < todos.size()) {
            todos.set(index, todo); // 기존 데이터를 새 todo로 덮어쓰기
            System.out.println("수정 완료");
        } else {
            System.out.println("오류: 해당 날짜에 할 일이 없거나 번호가 잘못되었습니다.");
        }
    }

    @Override
    public void delete(String date, int index) {
        System.out.println("[TodoRepositoryImpl.delete()]");

        List<Todo> todos = this.todoMap.get(date);

        if (todos != null && index >= 0 && index < todos.size()) {
            todos.remove(index); // 데이터 지우기
            System.out.println("삭제 완료");

            // 만약 지우고 났더니 리스트가 텅 비었다면, 날짜(Key) 자체를 맵에서 지워줍니다.
            if (todos.isEmpty()) {
                this.todoMap.remove(date);
            }
        } else {
            System.out.println("오류: 해당 날짜에 할 일이 없거나 번호가 잘못되었습니다.");
        }
    }

    @Override
    public void complete(String date, int index) {
        System.out.println("[TodoRepositoryImpl.complete()]");

        List<Todo> todos = this.todoMap.get(date);

        if (todos != null && index >= 0 && index < todos.size()) {
            Todo todo = todos.get(index);
            todo.setCompleted(true); // 해당 할 일의 상태를 '완료'로 변경
            System.out.println("상태 변경 완료");
        } else {
            System.out.println("오류: 해당 날짜에 할 일이 없거나 번호가 잘못되었습니다.");
        }
    }
}