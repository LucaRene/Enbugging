import React from "react";
import { useNavigate } from "react-router-dom";

const categories = [
    { name: "Syntaxfehler", value: "syntax" },
    { name: "Variablen-/Deklarationsfehler", value: "declaration" },
    { name: "Typfehler", value: "type" },
    { name: "Volle Erfahrung", value: "full" },
];

const StartPage = ({ setSelectedCategory, fetchNewTask }) => {
    const navigate = useNavigate();

    const handleSelect = async (category) => {
        setSelectedCategory(category);
        await fetchNewTask(category);
        navigate('/task');
    };

    return (
        <div className="start-container">
            <h1>Willkommen!</h1>
            <p>Bitte wähle eine Fehlerkategorie:</p>
            <div className="button-grid">
                {categories.map((c) => (
                    <button
                        key={c.value}
                        onClick={() => handleSelect(c.value)}
                        className="big-button"
                    >
                        {c.name}
                    </button>
                ))}
            </div>
        </div>
    );
};

export default StartPage;