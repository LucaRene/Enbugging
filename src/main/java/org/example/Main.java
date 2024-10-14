package org.example;

import org.example.Tasks.Task;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("taskCode");
        System.out.println(task.getNewTask());
    }
}