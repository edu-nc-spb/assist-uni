package ru.niuitmo.shostina.services;

import java.util.List;

public class TeacherService {
    private static DBService service;

    private static TeacherService teacherService;

    public static TeacherService instance(){
        if (teacherService == null) {
            teacherService = new TeacherService();
            try {
                teacherService.service.addTask("Header1", "Problem1");
            } catch (ServiceException e){
                System.out.println(e.getMessage());
            }

        }

        return teacherService;
    }

    public TeacherService() {
        service = new DBService();
    }

    public void addTask(String header, String problem) throws ServiceException {
        service.addTask(header, problem);
    }

    public List<String> getAllTasks() throws ServiceException {
        return service.getAllTasks();
    }
}
