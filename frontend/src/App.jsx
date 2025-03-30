import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AppRouter from "./AppRouter";

function App() {
    return (
        <Router>
            <AppRouter />
        </Router>
    );
}

export default App;