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
        private final boolean guidesEnabled;
        public GuidesMap(boolean guidesEnabled) {
            guidesMap = new HashMap<>();
            this.guidesEnabled = guidesEnabled;
            if (!guidesEnabled) return;
            guidesMap.put(PieceType.LION, R.drawable.ic_lion_guides);
            guidesMap.put(PieceType.CHICK, R.drawable.ic_chick_guides);
            guidesMap.put(PieceType.CHICKEN, R.drawable.ic_chicken_guides);
            guidesMap.put(PieceType.GIRAFFE, R.drawable.ic_giraffe_guides);
            guidesMap.put(PieceType.ELEPHANT, R.drawable.ic_elephant_guides);
        }
        public Integer get(PieceType type){
           return guidesEnabled ? guidesMap.get(type) : R.drawable.ic_nothing;
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

    final Theme defaultTheme;
    final boolean defaultGuides;
    final Map<PieceType,Integer> resourcesMap;
    private final GuidesMap guidesMap;

    public ThemeManager() {
        defaultTheme = Theme.KANJI;
        defaultGuides = true;
        guidesMap = new GuidesMap(defaultGuides);

        resourcesMap = getResourceMap(defaultTheme);
    }

    public LayerDrawable getPieceDrawable(PieceType type, Resources res){return new LayerDrawable(new Drawable[] {
            res.getDrawable(resourcesMap.get(type)),
            res.getDrawable(guidesMap.get(type))
    });}
}
