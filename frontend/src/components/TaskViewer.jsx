import React, { useState, useEffect } from "react";
import "../styles/TaskViewer.css";

/**
 * TaskViewer Component
 * Manages the display of task descriptions, editable code sections, and actions for validation, resetting, and hints/solutions.
 *
 * @param {Object} props - The props passed to the component.
 * @param {string} props.taskCode - The code with gaps for the user to edit.
 * @param {string} props.errorMessage - The expected compiler error message for the task.
 * @param {string} props.hint - The hint message to assist the user.
 * @param {string} props.solution - The solution message to guide the user after multiple resets.
 * @param {function} props.fetchNewTask - A function to fetch a new task from the backend.
 * @param {boolean} props.isLoading - Indicates if a new task is currently being loaded.
 * @param {number} props.resetCount - The number of times the task has been reset.
 * @param {function} props.setResetCount - A function to update the reset counter.
 */
const TaskViewer = ({
                        taskCode,
                        errorMessage,
                        hint,
                        solution,
                        fetchNewTask,
                        isLoading,
                        resetCount,
                        setResetCount,
                    }) => {
    const [editableValues, setEditableValues] = useState({});
    const [activeIndex, setActiveIndex] = useState(null);
    const [feedbackMessage, setFeedbackMessage] = useState("");
    const [showEvaluation, setShowEvaluation] = useState(false);
    const [expectedError, setExpectedError] = useState("");
    const [actualError, setActualError] = useState("");
    const [evaluation, setEvaluation] = useState("");
    const [isTaskComplete, setIsTaskComplete] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);
    const [showHint, setShowHint] = useState(false);
    const [showSolution, setShowSolution] = useState(false);
    const [userInteractions, setUserInteractions] = useState([]);

    // Extracts the original values of the editable sections from the provided taskCode.
    const originalValues = taskCode.split(/(\[\[.*?\]\])/).map((part) =>
        part.startsWith("[[") && part.endsWith("]]") ? part.slice(2, -2) : null
    );

    /**
     * Dynamically adjusts the width of input fields based on their content.
     *
     * @param {HTMLElement} element - The input element whose width needs adjustment.
     * @param {string} value - The value currently entered the input field.
     */
    const adjustInputWidth = (element, value) => {
        const length = value.length || 1;
        element.style.width = `${length + 1}ch`;
    };

    /**
     * Toggles the visibility of the hint modal.
     */
    const toggleHintModal = () => {
        setShowHint((prev) => !prev);
    };

    /**
     * Toggles the visibility of the solution modal.
     */
    const toggleSolutionModal = () => {
        setShowSolution((prev) => !prev);
    };

    /**
     * Automatically adjusts the width of editable input fields when the component mounts or when taskCode changes.
     */
    useEffect(() => {
        const inputs = document.querySelectorAll(".editable-input");
        inputs.forEach((input) => {
            adjustInputWidth(input, input.defaultValue);
        });
    }, [taskCode]);

    /**
     * Handles changes to input fields, adjusts their width dynamically, and manages the active index.
     *
     * @param {number} index - The index of the input field being changed.
     * @param {string} value - The new value entered the input field.
     * @param {HTMLElement} element - The input element being modified.
     */
    const handleInputChange = (index, value, element) => {
        adjustInputWidth(element, value);
        setEditableValues((prevValues) => ({
            ...prevValues,
            [index]: value,
        }));

        if (value !== originalValues[index]) {
            setActiveIndex(index);
        } else {
            setActiveIndex(null);
        }
    };

    /**
     * Resets the task to its original state, clearing user edits, feedback, and resetting relevant state variables.
     */
    const resetTask = () => {
        setEditableValues({});
        setActiveIndex(null);
        setFeedbackMessage("");
        setExpectedError("");
        setActualError("");
        setEvaluation("");
        setShowEvaluation(false);
        setIsTaskComplete(false);
        setIsDisabled(false);
        setResetCount((prevCount) => prevCount + 1);
    };

    /**
     * Sends the user's code to the server for validation and processes the response.
     */
    const validateCode = async () => {
        const parts = taskCode.split(/(\[\[.*?\]\])/);
        const userCode = parts
            .map((part, index) =>
                part.startsWith("[[") && part.endsWith("]]")
                    ? editableValues[index] !== undefined
                        ? editableValues[index]
                        : part.slice(2, -2)
                    : part
            )
            .join("");

        try {
            const response = await fetch("http://localhost:8080/api/compiler", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ code: userCode, expectedError: errorMessage }),
            });

            console.log("API response received:", response); // Debugging

            const result = await response.json();
            if (response.ok) {
                setExpectedError(result.expectedError);
                setActualError(result.actualError);
                setEvaluation(result.evaluation);
                setIsTaskComplete(result.evaluation.includes("Richtig!"));
                setShowEvaluation(true);
                setIsDisabled(true);
            } else {
                setFeedbackMessage("Ein Fehler ist aufgetreten.");
            }
        } catch (error) {
            console.error("Error:", error); // Debugging
            setFeedbackMessage(`Fehler beim Senden der Anfrage: ${error.message}`);
        }
    };

    /**
     * Renders the code with editable inputs for user modification.
     *
     * @returns {JSX.Element[]} The rendered code with inputs in place of editable sections.
     */
    const renderCodeWithInputs = () => {
        const parts = taskCode.split(/(\[\[.*?\]\])/);
        return parts.map((part, index) => {
            if (part.startsWith("[[") && part.endsWith("]]")) {
                const cleanPart = part.slice(2, -2);
                return (
                    <input
                        key={index}
                        type="text"
                        value={editableValues[index] !== undefined ? editableValues[index] : cleanPart}
                        className="editable-input"
                        onChange={(e) => handleInputChange(index, e.target.value, e.target)}
                        disabled={(activeIndex !== null && activeIndex !== index) || isDisabled}
                        style={{
                            border: "1px solid #ccc",
                            borderRadius: "4px",
                            padding: "2px 4px",
                            margin: "0 2px",
                            fontFamily: "monospace",
                            minWidth: `${cleanPart.length + 1}ch`,
                            backgroundColor:
                                isDisabled || (activeIndex !== null && activeIndex !== index)
                                    ? "#444"
                                    : "#2e2e2e",
                            color:
                                isDisabled || (activeIndex !== null && activeIndex !== index)
                                    ? "#888"
                                    : "#fff",
                        }}
                    />
                );
            }
            return <span key={index}>{part}</span>;
        });
    };

    /**
     * Ruft die User-Interaktionen vom Backend ab.
     */
    const fetchUserInteractions = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/tracking/interactions");
            const data = await response.json();

            if (Array.isArray(data) && data.length > 0) {
                setUserInteractions(data);
                console.log("Erhaltene User-Interaktionen:", data); // Debugging
            } else {
                console.warn("API hat keine oder fehlerhafte Daten geliefert.", data);
                setUserInteractions([]);
            }
        } catch (error) {
            console.error("Fehler beim Laden der Tracking-Daten:", error);
        }
    };

    /**
     * Erstellt eine CSV-Datei aus den API-Daten.
     */
    const generateCSV = () => {
        if (userInteractions.length === 0) {
            console.warn("Keine User-Interaktionsdaten vorhanden.");
            return "";
        }

        let csvContent = "TaskType,StartTime,EndTime,Attempts,SolvedCorrectly\n";

        userInteractions.forEach((interaction) => {
            csvContent += `${interaction.taskType},${interaction.startTime},${interaction.endTime},${interaction.attemptCount},${interaction.solvedCorrectly}\n`;
        });

        console.log("Generierte CSV-Daten:", csvContent); // Debugging
        return csvContent;
    };

    /**
     * Erstellt eine CSV-Datei und startet den Download.
     */
    const downloadCSV = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/tracking/interactions");
            const data = await response.json();

            if (!Array.isArray(data) || data.length === 0) {
                console.warn("Keine aktuellen Tracking-Daten verfügbar.");
                return;
            }

            console.log("Erhaltene User-Interaktionsdaten für CSV:", data);

            let csvContent = "TaskType,StartTime,EndTime,Attempts,SolvedCorrectly\n";
            data.forEach((interaction) => {
                csvContent += `${interaction.taskType},${interaction.startTime},${interaction.endTime},${interaction.attemptCount},${interaction.solvedCorrectly}\n`;
            });

            console.log("Generierte CSV-Daten:", csvContent); // Debugging

            const blob = new Blob([csvContent], { type: "text/csv" });
            const url = URL.createObjectURL(blob);

            const a = document.createElement("a");
            a.href = url;
            a.download = "user_interactions.csv";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Fehler beim Abrufen oder Erstellen der CSV-Datei:", error);
        }
    };

    useEffect(() => {
        fetchUserInteractions();
    }, []);

    return (
        <div className="container">
            <header>
                <h1>Aufgabenstellung</h1>
                <p> Verändere den Code, damit folgender Fehler entsteht: </p>
                <p className="expected-error-message"> {errorMessage} </p>
            </header>

            <div className="content">
                <section className="code-section">
                    <pre>{renderCodeWithInputs()}</pre>
                </section>

                {showEvaluation && (
                    <div
                        className={`evaluation-panel ${
                            isTaskComplete ? "correct" : "incorrect"
                        }`}
                    >
                        <h3>Auswertung</h3>
                        <p>
                            <strong>Zielfehlermeldung:</strong>
                        </p>
                        <pre>{expectedError}</pre>
                        <p>
                            <strong>Tatsächlicher Output des Compilers:</strong>
                        </p>
                        <pre>{actualError}</pre>
                        <p>
                            <strong>Auswertung:</strong>
                        </p>
                        <pre> {evaluation} </pre>
                    </div>
                )}
            </div>

            <div className="actions">
                <button onClick={validateCode} className="validate-button" disabled={isLoading}>
                    Compile
                </button>
                <button onClick={resetTask} className="reset-button" disabled={isLoading}>
                    Reset
                </button>
                {!showEvaluation && !isTaskComplete && (
                    <button onClick={toggleHintModal} className="hint-button">
                        Hinweis
                    </button>
                )}
                {resetCount >= 3 && !isTaskComplete && (
                    <button onClick={toggleSolutionModal} className="solution-button">
                        Lösung
                    </button>
                )}
                {showEvaluation && isTaskComplete && (
                    <button
                        onClick={() => {
                            resetTask();
                            fetchNewTask();
                        }}
                        className="next-task-button"
                        disabled={isLoading}
                    >
                        {isLoading ? "Lädt..." : "Nächste Aufgabe"}
                    </button>
                )}
                <button onClick={downloadCSV} className="download-button">
                    CSV herunterladen
                </button>
                {feedbackMessage && <p className="feedback">{feedbackMessage}</p>}
            </div>

            {showHint && (
                <div className="hint-modal">
                    <div className="hint-modal-content">
                        <p>{hint || "Kein Hinweis verfügbar"}</p>
                        <button className="close-button" onClick={toggleHintModal}>
                            Schließen
                        </button>
                    </div>
                </div>
            )}

            {showSolution && (
                <div className="solution-modal">
                    <div className="solution-modal-content">
                        <p>{solution || "Keine Lösung verfügbar"}</p>
                        <button className="close-button" onClick={toggleSolutionModal}>
                            Schließen
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default TaskViewer;