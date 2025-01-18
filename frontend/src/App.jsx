import React, { useEffect, useState } from "react";
import TaskViewer from "./components/TaskViewer";

function App() {
    const [taskData, setTaskData] = useState({
        taskCodeWithGaps: "",
        expectedErrorMessage: "",
        hintMessage: "",
        solutionMessage: ""
    });
    const [isLoading, setIsLoading] = useState(false);

    const fetchNewTask = async () => {
        setIsLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/task?timestamp=${Date.now()}`);
            if (response.ok) {
                const data = await response.json();
                console.log("Neue Aufgabe erhalten:", data);
                setTaskData(data);
            } else {
                console.error("Fehler beim Abrufen der Aufgabe.");
            }
        } catch (error) {
            console.error("Fehler beim Abrufen der Aufgabe:", error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        console.log("Aktueller Zustand von taskData:", taskData);
    }, [taskData]);

    useEffect(() => {
        fetchNewTask();
    }, []);

    return (
        <div>
            <TaskViewer
                taskCode={taskData.taskCodeWithGaps}
                errorMessage={taskData.expectedErrorMessage}
                hint={taskData.hintMessage}
                solution={taskData.solutionMessage}
                fetchNewTask={fetchNewTask}
                isLoading={isLoading}
            />
        </div>
    );
}

export default App;