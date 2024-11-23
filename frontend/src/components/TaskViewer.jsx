import React, { useEffect, useState } from "react";

const TaskViewer = () => {
    const [task, setTask] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/task")
            .then((response) => response.json())
            .then((data) => {
                setTask(data.taskCodeWithGaps);
                setErrorMessage(data.expectedErrorMessage);
            })
            .catch((error) => console.error("Error fetching task:", error));
    }, []);

    return (
        <div>
            <h1>Aufgabe</h1>
            <p>Verändere den Code, damit folgender Fehler entsteht:</p>
            <h2>{errorMessage}</h2>
            <pre style={{ background: "#f4f4f4", padding: "10px", borderRadius: "5px" }}>
        {task}
      </pre>
        </div>
    );
};

export default TaskViewer;