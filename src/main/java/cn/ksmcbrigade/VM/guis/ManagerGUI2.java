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
import net.minecraft.network.chat.TranslatableComponent;

public class ManagerGUI2 extends Screen {

    private OptionsList optionsList;

    public ManagerGUI2() {
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

        for(int i=0;i<VapeManager.Config.getMods().length;i++){
            ModInfo mod = VapeManager.Config.getMods()[i];
            if(mod.getScreen()!=null){
                this.optionsList.addBig(CycleOption.createOnOff(I18n.get(mod.getName())+" GUI",(p_168280_) -> {
                    return false;
                }, (p_168282_, p_168283_, p_168284_) -> {
                    try {
                        mod.OpenScreen();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

        }

        this.addWidget(this.optionsList);

        this.addRenderableWidget(new Button((this.width - 200) / 2,this.height - 25,200,20, CommonComponents.GUI_DONE,(p_96057_) -> {
            Minecraft.getInstance().setScreen(new ManagerGUI());
        }));

        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        this.optionsList.render(poseStack,mouseX,mouseY,partialTicks);
        drawCenteredString(poseStack,font,this.title.getString(),this.width / 2, 6, 16777215);
        drawCenteredString(poseStack,font,I18n.get("gui.vm.waring"),this.width / 2, 16, 16777215);
        super.render(poseStack,mouseX,mouseY,partialTicks);
    }
}
