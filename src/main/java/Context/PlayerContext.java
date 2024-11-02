package Context;

import java.util.Arrays;
import java.util.HashMap;

public class PlayerContext extends ContextStrategy {

    public PlayerContext() {
        attributeMethodMap = new HashMap<>();
        attributeMethodMap.put("leben", new String[]{"heilen", "erleideSchaden"});
        attributeMethodMap.put("schaden", new String[]{"erhoeheSchaden", "reduziereSchaden"});
        attributeMethodMap.put("ruestung", new String[]{"erhoeheRuestung", "reduziereRuestung"});
        attributeMethodMap.put("geschwindigkeit", new String[]{"erhoeheGeschwindigkeit", "reduziereGeschwindigkeit"});
        attributeMethodMap.put("level", new String[]{"levelUp"});
        attributeMethodMap.put("mana", new String[]{"regeneriereMana", "verbraucheMana"});
        attributeMethodMap.put("ausdauer", new String[]{"erhoeheAusdauer", "reduziereAusdauer"});
        attributeMethodMap.put("intelligenz", new String[]{"erhoeheIntelligenz", "reduziereIntelligenz"});
        attributeMethodMap.put("staerke", new String[]{"erhoeheStaerke", "reduziereStaerke"});
        attributeMethodMap.put("beweglichkeit", new String[]{"erhoeheBeweglichkeit", "reduziereBeweglichkeit"});
        attributeMethodMap.put("charaktername", new String[]{"zeigeName", "aendereName"});
        attributeMethodMap.put("klasse", new String[]{"zeigeKlasse", "aendereKlasse"});
        attributeMethodMap.put("waffenart", new String[]{"zeigeWaffenart", "aendereWaffenart"});
        attributeMethodMap.put("rasse", new String[]{"zeigeRasse", "aendereRasse"});

        attributeValueMap = new HashMap<>();
        attributeValueMap.put("leben", Arrays.asList(100, 150, 200, 250, 300, 350, 400, 450));
        attributeValueMap.put("schaden", Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80));
        attributeValueMap.put("ruestung", Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40));
        attributeValueMap.put("geschwindigkeit", Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5));
        attributeValueMap.put("level", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        attributeValueMap.put("mana", Arrays.asList(50, 100, 150, 200, 250, 300, 350, 400));
        attributeValueMap.put("ausdauer", Arrays.asList(30, 60, 90, 120, 150, 180, 210, 240));
        attributeValueMap.put("intelligenz", Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40));
        attributeValueMap.put("staerke", Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40));
        attributeValueMap.put("beweglichkeit", Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40));
        attributeValueMap.put("charaktername", Arrays.asList("Aragorn", "Legolas", "Gimli", "Frodo", "Gandalf", "Eowyn", "Boromir", "Sam"));
        attributeValueMap.put("klasse", Arrays.asList("Krieger", "Magier", "Schurke", "Bogenschütze", "Priester", "Paladin", "Druide", "Hexenmeister"));
        attributeValueMap.put("waffenart", Arrays.asList("Schwert", "Bogen", "Zauberstab", "Dolch", "Axt", "Speer", "Hammer", "Armbrust"));
        attributeValueMap.put("rasse", Arrays.asList("Mensch", "Elf", "Zwerg", "Hobbit", "Ork", "Troll", "Goblin", "Dämon"));
    }

    @Override
    public String getClassName() {
        return "Spielfigur";
    }
}
