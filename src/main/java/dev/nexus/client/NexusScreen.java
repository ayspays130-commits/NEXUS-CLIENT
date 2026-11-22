
package dev.nexus.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NexusScreen extends Screen {

    private boolean playerEsp = false;
    private boolean fullBright = false;
    private boolean sprint = false;
    private boolean hud = true;

    public NexusScreen() {
        super(Component.literal("NEXUS CLIENT"));
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int startY = this.height / 2 - 65;
        int buttonWidth = 180;

        addRenderableWidget(Button.builder(
                Component.literal("Player ESP: OFF"),
                button -> {
                    playerEsp = !playerEsp;
                    button.setMessage(Component.literal(
                            "Player ESP: " + (playerEsp ? "ON" : "OFF")
                    ));
                }
        ).bounds(center - buttonWidth / 2, startY, buttonWidth, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("FullBright: OFF"),
                button -> {
                    fullBright = !fullBright;
                    button.setMessage(Component.literal(
                            "FullBright: " + (fullBright ? "ON" : "OFF")
                    ));
                }
        ).bounds(center - buttonWidth / 2, startY + 25, buttonWidth, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Auto Sprint: OFF"),
                button -> {
                    sprint = !sprint;
                    button.setMessage(Component.literal(
                            "Auto Sprint: " + (sprint ? "ON" : "OFF")
                    ));
                }
        ).bounds(center - buttonWidth / 2, startY + 50, buttonWidth, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("HUD: ON"),
                button -> {
                    hud = !hud;
                    button.setMessage(Component.literal(
                            "HUD: " + (hud ? "ON" : "OFF")
                    ));
                }
        ).bounds(center - buttonWidth / 2, startY + 75, buttonWidth, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                button -> onClose()
        ).bounds(center - buttonWidth / 2, startY + 105, buttonWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, 0xC0101018);

        int center = this.width / 2;

        graphics.fill(
                center - 115,
                this.height / 2 - 105,
                center + 115,
                this.height / 2 + 95,
                0xFF20202E
        );

        graphics.drawCenteredString(
                this.font,
                "NEXUS CLIENT",
                center,
                this.height / 2 - 95,
                0xFF55FFFF
        );

        super.render(graphics, mouseX, mouseY, delta);
    }
}
