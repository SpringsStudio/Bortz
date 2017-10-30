package studio.springs.bortz;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import java.util.HashMap;
import java.util.Map;

import studio.springs.bortz.engine.pieces.PieceType;

public class ThemeManager {
    enum Theme {
        KANJI
    }

    static class GuidesMap {
        private final Map<PieceType,Integer> guidesMap;
        GuidesMap() {
            guidesMap = new HashMap<>();
            guidesMap.put(PieceType.LION, R.drawable.ic_lion_guides);
            guidesMap.put(PieceType.CHICK, R.drawable.ic_chick_guides);
            guidesMap.put(PieceType.CHICKEN, R.drawable.ic_chicken_guides);
            guidesMap.put(PieceType.GIRAFFE, R.drawable.ic_giraffe_guides);
            guidesMap.put(PieceType.ELEPHANT, R.drawable.ic_elephant_guides);
        }
        Integer get(PieceType type){
           return guidesMap.get(type);
        }
    }

    static Map<PieceType,Integer> getResourceMap(Theme theme){
        Map<PieceType,Integer> resourceMap = new HashMap<>();
        switch (theme){
            case KANJI:
                resourceMap.put(PieceType.LION, R.drawable.ic_lion_kanji);
                resourceMap.put(PieceType.CHICK, R.drawable.ic_chick_kanji);
                resourceMap.put(PieceType.CHICKEN, R.drawable.ic_chicken_kanji);
                resourceMap.put(PieceType.GIRAFFE, R.drawable.ic_giraffe_kanji);
                resourceMap.put(PieceType.ELEPHANT, R.drawable.ic_elephant_kanji);
                break;
        }
        return resourceMap;
    }

    public static final Theme DEFAULT_THEME = Theme.KANJI;
    public static final boolean DEFAULT_GUIDES = true;

    private final Theme theme;
    private final boolean guidesEnabled;
    private final Map<PieceType,Integer> resourcesMap;
    private final GuidesMap guidesMap;
    private Map<PieceType,Drawable[]> drawableMap;

    public ThemeManager() {
        if (false) { //Check if setting are set

        } else {
            theme = DEFAULT_THEME;
            guidesEnabled = DEFAULT_GUIDES;
        }
        guidesMap = new GuidesMap();
        resourcesMap = getResourceMap(theme);
    }

    public void createDrawableMap(Resources res){
        drawableMap = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            drawableMap.put(type,new Drawable[guidesEnabled ? 2 : 1]);
            drawableMap.get(type)[0] = res.getDrawable(resourcesMap.get(type));
            if (guidesEnabled) drawableMap.get(type)[1] = res.getDrawable(guidesMap.get(type));
        }
    }

    public LayerDrawable getPieceDrawable(PieceType type){return new LayerDrawable(drawableMap.get(type));}
}