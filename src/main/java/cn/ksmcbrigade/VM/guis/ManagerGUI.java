package cn.ksmcbrigade.VM.guis;

import cn.ksmcbrigade.VM.ModInfo;
import cn.ksmcbrigade.VM.VapeManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class ManagerGUI extends Screen {

    private OptionsList optionsList;

    public ManagerGUI() {
        super(new TranslatableComponent("gui.vm.title"));
    }

    @Override
    protected void init() {
        this.optionsList = new OptionsList(
                this.minecraft, this.width, this.height,
                24,
                this.height - 32,
                25
        );

        for(ModInfo mod:VapeManager.Config.getMods()){

            this.optionsList.addBig(CycleOption.createOnOff(mod.getName(),(p_168280_) -> {
                return mod.isEnabled();
            }, (p_168282_, p_168283_, p_168284_) -> {
                mod.setEnabled(p_168284_);
            }));
        }

        this.addWidget(this.optionsList);

        this.addRenderableWidget(new Button((this.width - 400) / 2,this.height - 25,80,20, Component.nullToEmpty("Mod guis"),(p_96057_) -> {
            Minecraft.getInstance().setScreen(new ManagerGUI2());
        }));

        this.addRenderableWidget(new Button((this.width - 200) / 2,this.height - 25,200,20, CommonComponents.GUI_DONE,(p_96057_) -> {
            this.onClose();
        }));

        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        this.optionsList.render(poseStack,mouseX,mouseY,partialTicks);
        drawCenteredString(poseStack,font,this.title.getString(),this.width / 2, 8, 16777215);
        super.render(poseStack,mouseX,mouseY,partialTicks);
    }
}
