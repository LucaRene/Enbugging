import React, { useState, useEffect } from "react";
import "../styles/TaskViewer.css";

const TaskViewer = ({ taskCode, errorMessage }) => {
    const [editableValues, setEditableValues] = useState({});
    const [activeIndex, setActiveIndex] = useState(null);
    const originalValues = taskCode.split(/(\[.*?\])/).map((part) =>
        part.startsWith("[") && part.endsWith("]") ? part.slice(1, -1) : null
    );

    const adjustInputWidth = (element, value) => {
        const length = value.length || 1;
        element.style.width = `${length + 1}ch`;
    };

    useEffect(() => {
        const inputs = document.querySelectorAll(".editable-input");
        inputs.forEach((input) => {
            adjustInputWidth(input, input.defaultValue);
        });
    }, [taskCode]);

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

    const renderCodeWithInputs = () => {
        const parts = taskCode.split(/(\[.*?\])/);
        return parts.map((part, index) => {
            if (part.startsWith("[") && part.endsWith("]")) {
                const cleanPart = part.slice(1, -1);
                return (
                    <input
                        key={index}
                        type="text"
                        defaultValue={editableValues[index] || cleanPart}
                        className="editable-input"
                        onChange={(e) => handleInputChange(index, e.target.value, e.target)}
                        disabled={activeIndex !== null && activeIndex !== index}
                        style={{
                            border: "1px solid #ccc",
                            borderRadius: "4px",
                            padding: "2px 4px",
                            margin: "0 2px",
                            fontFamily: "monospace",
                            minWidth: `${cleanPart.length + 1}ch`,
                            backgroundColor: activeIndex !== null && activeIndex !== index ? "#444" : "#2e2e2e",
                            color: activeIndex !== null && activeIndex !== index ? "#888" : "#fff",
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
                <p>
                    Verändere den Code, damit folgender Fehler entsteht: <br /> {errorMessage}{" "}
                </p>
            </header>

            <section>
                <pre>{renderCodeWithInputs()}</pre>
            </section>
        </div>
    );
};

export default TaskViewer;