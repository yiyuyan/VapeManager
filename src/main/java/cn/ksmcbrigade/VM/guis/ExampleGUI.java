package cn.ksmcbrigade.VM.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class ExampleGUI extends Screen {

    public ExampleGUI() {
        super(new TranslatableComponent("gui.vm.title"));
    }

    @Override
    protected void init() {

        this.addRenderableWidget(new Button((this.width - 200) / 2,this.height - 25,200,20, CommonComponents.GUI_DONE,(p_96057_) -> {
            this.onClose();
        }));

        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack,font,this.title.getString(),this.width / 2, 8, 16777215);
        super.render(poseStack,mouseX,mouseY,partialTicks);
    }
}
