import React, { useState, useEffect } from "react";
import "../styles/TaskViewer.css";

/**
 * TaskViewer Component
 * Displays the task description, the editable code, and actions to validate or reset the task.
 */
const TaskViewer = ({ taskCode, errorMessage, fetchNewTask, isLoading }) => {
    const [editableValues, setEditableValues] = useState({});
    const [activeIndex, setActiveIndex] = useState(null);
    const [feedbackMessage, setFeedbackMessage] = useState("");
    const [showEvaluation, setShowEvaluation] = useState(false);
    const [expectedError, setExpectedError] = useState("");
    const [actualError, setActualError] = useState("");
    const [evaluation, setEvaluation] = useState("");
    const [isTaskComplete, setIsTaskComplete] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);

    const originalValues = taskCode.split(/(\[\[.*?\]\])/).map((part) =>
        part.startsWith("[[") && part.endsWith("]]") ? part.slice(2, -2) : null
    );


    /**
     * Dynamically adjusts the width of input fields based on their content.
     *
     * @param {HTMLElement} element - The input element.
     * @param {string} value - The current value of the input field.
     */
    const adjustInputWidth = (element, value) => {
        const length = value.length || 1;
        element.style.width = `${length + 1}ch`;
    };

    /**
     * Adjusts input widths when the component mounts or when the taskCode changes.
     */
    useEffect(() => {
        const inputs = document.querySelectorAll(".editable-input");
        inputs.forEach((input) => {
            adjustInputWidth(input, input.defaultValue);
        });
    }, [taskCode]);

    /**
     * Handles changes in input fields, adjusting widths and managing the active index.
     *
     * @param {number} index - The index of the input field.
     * @param {string} value - The new value of the input field.
     * @param {HTMLElement} element - The input element.
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
     * Resets the task to its original state, clearing edits and feedback messages.
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
    };

    /**
     * Sends the user's code to the server for validation and displays the response.
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

            console.log("API-Antwort erhalten:", response); // Debugging

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
            console.error("Fehler:", error); // Debugging
            setFeedbackMessage(`Fehler beim Senden der Anfrage: ${error.message}`);
        }
    };

    /**
     * Renders the code with input fields for editable sections.
     *
     * @returns {JSX.Element[]} The rendered code with editable inputs.
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
                            backgroundColor: isDisabled || (activeIndex !== null && activeIndex !== index) ? "#444" : "#2e2e2e",
                            color: isDisabled || (activeIndex !== null && activeIndex !== index) ? "#888" : "#fff",
                        }}
                    />
                );
            }
            return <span key={index}>{part}</span>;
        });
    };

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
                        <p><strong>Zielfehlermeldung:</strong></p>
                        <pre>{expectedError}</pre>
                        <p><strong>Tatsächlicher Output des Compilers:</strong></p>
                        <pre>{actualError}</pre>
                        <p><strong>Auswertung:</strong></p>
                        <pre> {evaluation} </pre>
                    </div>
                )}
            </div>

            <div className="actions">
                <button onClick={validateCode} className="validate-button" disabled={isLoading}>
                    Aufgabe prüfen
                </button>
                <button onClick={resetTask} className="reset-button" disabled={isLoading}>
                    Zurücksetzen
                </button>
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
                {feedbackMessage && <p className="feedback">{feedbackMessage}</p>}
            </div>
        </div>
    );
};

export default TaskViewer;