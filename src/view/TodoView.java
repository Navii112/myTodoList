package view;

import service.TodoService;
import vo.Todo;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TodoView {
    private final Scanner sc;
    private final TodoService todoService;

    public TodoView(Scanner sc, TodoService todoService) {
        this.sc = sc;
        this.todoService = todoService;
    }

    public void start() {
        int choice = 0;
        while (true) {
            printMenu();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addTodo();
                    break;
                case 2:
                    updateTodo();
                    break;
                case 3:
                    deleteTodo();
                    break;
                case 4:
                    completeTodo();
                    break;
                case 5:
                    printByDate();
                    break;
                case 6:
                    printAll();
                    break;
                case 7:
                    System.out.println("프로그램 종료");
                    return;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== TODO LIST =====");
        System.out.println("1. 할 일 입력");
        System.out.println("2. 할 일 수정");
        System.out.println("3. 할 일 삭제");
        System.out.println("4. 완료 체크");
        System.out.println("5. 일별 출력");
        System.out.println("6. 전체 출력");
        System.out.println("7. 종료");
        System.out.print("번호 선택: ");
    }

    private void addTodo() {
        System.out.print("날짜 입력(예: 2026-04-14): ");
        String date = sc.next();

        System.out.print("시간 입력(예: 09:00): ");
        String time = sc.next();

        System.out.print("할 일 입력: ");
        String task = sc.next();

        //todoService.addTodo 호출
        todoService.addTodo(date, time, task);
        System.out.println("저장 완료!");
    }

    private void updateTodo() {
        System.out.print("날짜 입력: ");
        String date = sc.next();

        List<Todo> list = todoService.getTodosByDate(date);
        printList(date, list);

        if (list == null || list.isEmpty()) {
            return;
        }

        System.out.print("수정할 번호 선택: ");
        int index = sc.nextInt() - 1;

        // 할 일 리스트 중 선택한 할 일이 이미 완료되어 있다면
        // 그냥 리턴
        if (list.get(index).isCompleted()) {
            System.out.println("이미 완료 된 할 일 입니다.");
            System.out.println("다시 선택");
            return;
        }

        System.out.print("새 시간 입력: ");
        String time = sc.next();

        System.out.print("새 할 일 입력: ");
        String task = sc.next();

        // todoService.updateTodo 호출
        todoService.updateTodo(date, index, time, task);
        System.out.println("수정 완료!");
    }

    private void deleteTodo() {
        System.out.print("날짜 입력: ");
        String date = sc.next();

        List<Todo> list = todoService.getTodosByDate(date);
        printList(date, list);

        if (list == null || list.isEmpty()) {
            return;
        }

        System.out.print("삭제할 번호 선택: ");
        int index = sc.nextInt() - 1;

        // todoService.deleteTodo 호출
        todoService.deleteTodo(date, index);
        System.out.println("삭제 완료");
    }

    private void completeTodo() {
        System.out.print("날짜 입력: ");
        String date = sc.next();

        List<Todo> list = todoService.getTodosByDate(date);
        printList(date, list);

        if (list == null || list.isEmpty()) {
            return;
        }

        System.out.print("완료 처리할 번호 선택: ");
        int index = sc.nextInt() - 1;

        // todoService.completeTodo 호출
        todoService.completeTodo(date, index);
        System.out.println("완료 처리 완료!");
    }

    private void printByDate() {
        System.out.print("날짜 입력: ");
        String date = sc.next();

        List<Todo> list = todoService.getTodosByDate(date);
        printList(date, list);
    }

    private void printAll() {
        Map<String, List<Todo>> all = todoService.getAllTodos();

        if (all.isEmpty()) {
            System.out.println("등록된 할 일이 없습니다.");
            return;
        }

        for (String date : all.keySet()) {
            List<Todo> list = all.get(date);
            printList(date, list);
        }
    }

    private void printList(String date, List<Todo> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("해당 날짜의 할 일이 없습니다.");
            return;
        }

        System.out.println("\n[" + date + "]");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}