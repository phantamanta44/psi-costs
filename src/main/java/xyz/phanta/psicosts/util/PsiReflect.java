package xyz.phanta.psicosts.util;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;

public class PsiReflect {

    public static final Class<?> tPlayerData;
    public static final MirrorUtils.IField<Integer> fPlayerData_AvailablePsi;

    static {
        try {
            tPlayerData = Class.forName("vazkii.psi.common.core.handler.PlayerDataHandler$PlayerData");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to reflect class!", e);
        }
        fPlayerData_AvailablePsi = MirrorUtils.reflectField(tPlayerData, "availablePsi");
    }

}
