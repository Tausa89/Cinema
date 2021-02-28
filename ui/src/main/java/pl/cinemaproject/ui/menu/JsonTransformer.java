package pl.cinemaproject.ui.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

// TA KLASA ZAJMIE SIE AUTOMATYCZNYM KONWERTOWANIEM TEGO CO
// ZWRACACIE W RAMACH REQUESTA DO FORMATU JSON A POMOZE JEJ
// W TYM GSON CZYLI SPECJALNY OBIEKT
public class JsonTransformer implements ResponseTransformer {
    // setPrettyPrinting jest po to zeby json mial ladny format
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
