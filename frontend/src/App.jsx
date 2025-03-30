import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import React, { useEffect, useState } from "react";
import TaskViewer from "./components/TaskViewer";
import StartPage from './components/StartPage.jsx';


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
    const [selectedCategory, setSelectedCategory] = useState('full');


    /**
     * Fetches a new task from the backend and resets the reset counter.
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

    return (
        <Router>
            <Routes>
                <Route path="/" element={
                    <StartPage
                        setSelectedCategory={setSelectedCategory}
                        fetchNewTask={fetchNewTask}
                    />}
                />
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
                    />}
                />
            </Routes>
        </Router>
    );
}

export default App;