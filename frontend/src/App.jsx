import React, { useEffect, useState } from "react";
import TaskViewer from "./components/TaskViewer";

/**
 * Main component of the application, responsible for task management and logic.
 * This component fetches new tasks from the backend and passes them to the TaskViewer component.
 */
function App() {
    const [taskData, setTaskData] = useState({
        taskCodeWithGaps: "",
        expectedErrorMessage: "",
        hintMessage: "",
        solutionMessage: ""
    });

    const [isLoading, setIsLoading] = useState(false);
    const [resetCount, setResetCount] = useState(0);

    /**
     * Fetches a new task from the backend and resets the reset counter.
     */
    const fetchNewTask = async () => {
        setIsLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/task?timestamp=${Date.now()}`);
            if (response.ok) {
                const data = await response.json();
                console.log("New task received:", data);
                setTaskData(data);
                setResetCount(0);
            } else {
                console.error("Error fetching the task.");
            }
        } catch (error) {
            console.error("Error fetching the task:", error);
        } finally {
            setIsLoading(false);
        }
    };

    /**
     * Fetches the first task when the component is initialized.
     */
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
                resetCount={resetCount}
                setResetCount={setResetCount}
            />
        </div>
    );
}

export default App;