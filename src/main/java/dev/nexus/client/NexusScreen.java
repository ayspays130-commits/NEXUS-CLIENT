
package dev.nexus.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class NexusScreen extends Screen {
    private static final List<String> TABS =
            List.of("PLAYER", "COMBAT", "RENDER", "MOVEMENT", "MISC");

    private static final Map<String, List<String>> MODULES = new LinkedHashMap<>();
    private static final Map<String, Boolean> ENABLED = new LinkedHashMap<>();

    static {
        MODULES.put("PLAYER", List.of("HUD", "Keystrokes", "CPS Counter", "Sprint Indicator"));
        MODULES.put("COMBAT", List.of("Aim Practice (dummy world)", "Target Display"));
        MODULES.put("RENDER", List.of("Coordinates", "FPS Display", "Armor Status"));
        MODULES.put("MOVEMENT", List.of("Direction Display", "Movement Info"));
        MODULES.put("MISC", List.of("Client Sounds", "Notifications"));
        MODULES.values().forEach(list -> list.forEach(name -> ENABLED.putIfAbsent(name, false)));
    }

    private String selectedTab = "PLAYER";
    private int panelLeft;
    private int panelTop;
    private int panelWidth = 360;
    private int panelHeight = 230;

    public NexusScreen() {
        super(Component.literal("NEXUS CLIENT"));
    }

    @Override
    protected void init() {
        panelLeft = (width - panelWidth) / 2;
        panelTop = (height - panelHeight) / 2;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);

        int x = panelLeft;
        int y = panelTop;

        g.fill(x, y, x + panelWidth, y + panelHeight, 0xF014141B);
        g.fill(x, y, x + panelWidth, y + 3, 0xFF9B5CFF);
        g.fill(x, y + 3, x + panelWidth, y + 34, 0xFF20202A);
        g.drawString(font, "NEXUS", x + 14, y + 13, 0xFFFFFFFF, false);
        g.drawString(font, "FABRIC 1.21.11", x + panelWidth - 104, y + 13, 0xFFB9A2D8, false);

        int tabY = y + 46;
        int tabX = x + 12;
        for (String tab : TABS) {
            int tabW = 65;
            boolean active = tab.equals(selectedTab);
            g.fill(tabX, tabY, tabX + tabW - 3, tabY + 22,
                    active ? 0xFF7040A8 : 0xFF2A2A35);
            g.drawCenteredString(font, tab, tabX + (tabW - 3) / 2, tabY + 7,
                    active ? 0xFFFFFFFF : 0xFFB8B8C5);
            tabX += tabW;
        }

        g.fill(x + 12, y + 77, x + panelWidth - 12, y + 78, 0xFF393945);

        int rowY = y + 89;
        for (String module : MODULES.get(selectedTab)) {
            boolean enabled = ENABLED.getOrDefault(module, false);
            g.fill(x + 14, rowY - 4, x + panelWidth - 14, rowY + 20,
                    enabled ? 0xFF30243D : 0xFF22222C);
            g.drawString(font, module, x + 23, rowY + 4, 0xFFEAEAF0, false);

            int toggleColor = enabled ? 0xFF9B5CFF : 0xFF555560;
            g.fill(x + panelWidth - 48, rowY + 3, x + panelWidth - 25, rowY + 14, toggleColor);
            g.fill(enabled ? x + panelWidth - 36 : x + panelWidth - 46,
                    rowY + 4, enabled ? x + panelWidth - 27 : x + panelWidth - 37,
                    rowY + 13, 0xFFFFFFFF);
            rowY += 30;
        }

        g.drawString(font, "RIGHT SHIFT  •  CLOSE MENU", x + 14,
                y + panelHeight - 18, 0xFF888895, false);

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        int x = panelLeft;
        int y = panelTop;

        int tabY = y + 46;
        int tabX = x + 12;
        for (String tab : TABS) {
            int tabW = 65;
            if (mouseX >= tabX && mouseX < tabX + tabW - 3
                    && mouseY >= tabY && mouseY <= tabY + 22) {
                selectedTab = tab;
                return true;
            }
            tabX += tabW;
        }

        int rowY = y + 89;
        for (String module : MODULES.get(selectedTab)) {
            if (mouseX >= x + 14 && mouseX <= x + panelWidth - 14
                    && mouseY >= rowY - 4 && mouseY <= rowY + 20) {
                ENABLED.put(module, !ENABLED.getOrDefault(module, false));
                return true;
            }
            rowY += 30;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
