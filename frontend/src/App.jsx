import React, { useEffect, useState } from "react";
import TaskViewer from "./components/TaskViewer";

function App() {
    const [taskData, setTaskData] = useState({ taskCodeWithGaps: "", expectedErrorMessage: "" });

    useEffect(() => {
        fetch("http://localhost:8080/task")
            .then((response) => response.json())
            .then((data) => {
                setTaskData(data);
            })
            .catch((error) => console.error("Error fetching task:", error));
    }, []);

    return (
        <div>
            <TaskViewer taskCode={taskData.taskCodeWithGaps} errorMessage={taskData.expectedErrorMessage} />
        </div>
    );
}

export default App;