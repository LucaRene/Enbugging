import React, { useEffect, useState } from "react";
import { Routes, Route, useLocation, useNavigate } from "react-router-dom";
import TaskViewer from "./components/TaskViewer";
import StartPage from "./components/StartPage";

/**
 * Main component handling routing, task loading, and persistence of the selected category.
 *
 * Manages task fetching based on categories, navigation logic, and stores the user's task category selection
 * persistently using Local Storage.
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

    const [selectedCategory, setSelectedCategory] = useState(
        localStorage.getItem("selectedCategory") || "full"
    );

    const location = useLocation();
    const navigate = useNavigate();

    /**
     * Fetches a new task from the backend based on the selected category.
     *
     * @param {string} category - The category of task to fetch.
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
     * Checks with the backend if a predefined configuration file is used.
     * If so, skips the start menu and navigates directly to the task page.
     */
    useEffect(() => {
        fetch('http://localhost:8080/config-used')
            .then(res => res.json())
            .then(configUsed => {
                if (configUsed) {
                    setSelectedCategory('full');
                    localStorage.setItem("selectedCategory", "full");
                    navigate('/task');
                }
            });
    }, [navigate]);

    /**
     * Automatically fetches a new task when directly accessing or reloading the task page,
     * using the category stored in Local Storage.
     */
    useEffect(() => {
        if (location.pathname === '/task') {
            const category = localStorage.getItem("selectedCategory") || "full";
            setSelectedCategory(category);
            fetchNewTask(category);
        }
    }, [location.pathname]);

    /**
     * Updates the selected category both in state and in Local Storage.
     *
     * @param {string} category - The category selected by the user.
     */
    const handleSetSelectedCategory = (category) => {
        setSelectedCategory(category);
        localStorage.setItem("selectedCategory", category);
    };

    return (
        <Routes>
            <Route path="/" element={
                <StartPage
                    setSelectedCategory={handleSetSelectedCategory}
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