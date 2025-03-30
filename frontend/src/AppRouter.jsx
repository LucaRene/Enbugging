import React, { useEffect, useState } from "react";
import { Routes, Route, useLocation } from "react-router-dom";
import TaskViewer from "./components/TaskViewer";
import StartPage from "./components/StartPage";

function AppRouter() {
    const [taskData, setTaskData] = useState({
        taskCodeWithGaps: "",
        expectedErrorMessage: "",
        hintMessage: "",
        solutionMessage: ""
    });
    const [isLoading, setIsLoading] = useState(false);
    const [resetCount, setResetCount] = useState(0);
    const [selectedCategory, setSelectedCategory] = useState("full");

    const location = useLocation();

    const fetchNewTask = async (category = selectedCategory) => {
        setIsLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/task?type=${category}&timestamp=${Date.now()}`);
            if (response.ok) {
                const data = await response.json();
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

    useEffect(() => {
        fetch('http://localhost:8080/config-used')
            .then(res => res.json())
            .then(configUsed => {
                if (configUsed) {
                    setSelectedCategory('full');
                    navigate('/task');
                }
            });
    }, []);

    return (
        <Routes>
            <Route path="/" element={
                <StartPage
                    setSelectedCategory={setSelectedCategory}
                    fetchNewTask={fetchNewTask}
                />
            } />
            <Route path="/task" element={
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
            } />
        </Routes>
    );
}

export default AppRouter;