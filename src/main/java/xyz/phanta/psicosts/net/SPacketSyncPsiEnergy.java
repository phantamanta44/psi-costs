package xyz.phanta.psicosts.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.internal.IPlayerData;
import xyz.phanta.psicosts.util.PsiReflect;

import javax.annotation.Nullable;

public class SPacketSyncPsiEnergy implements IMessage {

    private int psiEnergy;

    public SPacketSyncPsiEnergy(int psiEnergy) {
        this.psiEnergy = psiEnergy;
    }

    public SPacketSyncPsiEnergy() {
        // NO-OP
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        psiEnergy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(psiEnergy);
    }

    public static class Handler implements IMessageHandler<SPacketSyncPsiEnergy, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(SPacketSyncPsiEnergy message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            //noinspection Convert2Lambda
            mc.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    IPlayerData psiData = PsiAPI.internalHandler.getDataForPlayer(mc.player);
                    if (PsiReflect.tPlayerData.isInstance(psiData)) {
                        PsiReflect.fPlayerData_AvailablePsi.set(psiData, message.psiEnergy);
                    }
                }
            });
            return null;
        }

    }

}
