import React, { useEffect, useState } from "react";
import { Routes, Route, useLocation, useNavigate } from "react-router-dom";
import TaskViewer from "./components/TaskViewer";
import StartPage from "./components/StartPage";

/**
 * Main routing component responsible for navigation and task management.
 *
 * Controls the loading of tasks, state management, and conditional navigation
 * based on the presence of predefined configuration on the backend.
 *
 * @component
 */
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
    const navigate = useNavigate();

    /**
     * Fetches a new task from the backend based on the provided category.
     * Defaults to the currently selected category if none is specified.
     *
     * @async
     * @param {string} [category=selectedCategory] - The category of tasks to fetch.
     */
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

    /**
     * Checks if a predefined configuration file exists on the backend.
     * If present, skips the start page and navigates directly to the task page.
     */
    useEffect(() => {
        fetch('http://localhost:8080/config-used')
            .then(res => res.json())
            .then(configUsed => {
                if (configUsed) {
                    setSelectedCategory('full');
                    navigate('/task');
                }
            });
    }, [navigate]);

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