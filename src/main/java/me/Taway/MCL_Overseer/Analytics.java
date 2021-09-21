package me.Taway.MCL_Overseer;

import static me.Taway.MCL_Overseer.MCL_Overseer.LoggerInstance;

public class Analytics {
    protected static void Run() {
        try{
            // TODO: This ^
        } catch (Exception exception) {
            String message;
            String fileName = Get.CurrentDate().replace("/", "_");
            message = "[> Analytics (run) <] <DATE: " + Get.CurrentDate() + " TIME: " + Get.CurrentTime() + " >";
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", "\n\n\n" + message);
            FileManager.writeToFile("ExceptionLog/" + fileName + ".txt", exception.getMessage());
        }
    }
}
