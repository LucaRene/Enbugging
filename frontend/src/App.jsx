import React, { useEffect, useState } from "react";
import TaskViewer from "./components/TaskViewer";

function App() {
    const [taskData, setTaskData] = useState({ taskCodeWithGaps: "", expectedErrorMessage: "" });

    const fetchNewTask = async () => {
        try {
            const response = await fetch("http://localhost:8080/task");
            if (response.ok) {
                const data = await response.json();
                setTaskData(data); // Aktualisiert die Aufgabe
            } else {
                console.error("Fehler beim Abrufen der Aufgabe.");
            }
        } catch (error) {
            console.error("Fehler beim Abrufen der Aufgabe:", error);
        }
    };

    useEffect(() => {
        fetchNewTask();
    }, []);

    return (
        <div>
            <TaskViewer
                taskCode={taskData.taskCodeWithGaps}
                errorMessage={taskData.expectedErrorMessage}
                fetchNewTask={fetchNewTask} // Funktion an TaskViewer übergeben
            />
        </div>
    );
}

export default App;