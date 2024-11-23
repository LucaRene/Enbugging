import React from "react";
import TaskViewer from "./components/TaskViewer.jsx";
import "./App.css";

function App() {
    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", minHeight: "100vh" }}>
            <TaskViewer />
        </div>
    );
}

export default App;