package de.pfannekuchen.lotas.mixin.render.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pfannekuchen.lotas.core.utils.ConfigUtils;
import de.pfannekuchen.lotas.gui.SeedListScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

@Mixin(SelectWorldScreen.class)
public abstract class MixinSelectWorldScreen extends Screen {
	
    protected MixinSelectWorldScreen(Text title) {
		super(title);
	}

    private CheckboxWidget widget;
    private CheckboxWidget challenges;
    @Shadow
    private WorldListWidget levelList;
    
	@Inject(at = @At("TAIL"), method = "init")
    public void injectinit(CallbackInfo ci) {
        this.addButton(new ButtonWidget(2, 2, 98, 20, "Seed List", button -> {
            this.minecraft.openScreen(new SeedListScreen());
        }));
        this.addButton(widget = new CheckboxWidget(width - 180, 4, 180, 20, "Open ESC when joining world", ConfigUtils.getBoolean("tools", "hitEscape")));
        this.addButton(challenges = new CheckboxWidget(width - 180, 26, 180, 20, "Show TAS Challenge Maps", !ConfigUtils.getBoolean("tools", "hideMaps")));
        
		if (!ConfigUtils.getBoolean("tools", "hideMaps")) {
//			for (ChallengeMap map : LoTAS.maps) {
//				ChallengeMapEntry entry = new ChallengeMapEntry(levelList, map);
//				entry.loc = map.resourceLoc;
//				levelList.children.add(entry);
//			}
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean b = super.mouseClicked(mouseX, mouseY, button);
		ConfigUtils.setBoolean("tools", "hitEscape", widget.isChecked());
		ConfigUtils.setBoolean("tools", "hideMaps", !challenges.isChecked());
		ConfigUtils.save();
		
//		for (Entry entry : new ArrayList<>(levelList.children)) {
//			if (entry instanceof ChallengeMapEntry) levelList.children.remove(entry); 
//		}
//		if (!ConfigUtils.getBoolean("tools", "hideMaps")) {
//			for (ChallengeMap map : LoTAS.maps) {
//				ChallengeMapEntry entry = new ChallengeMapEntry(levelList, map);
//				entry.loc = map.resourceLoc;
//				levelList.children.add(entry);
//			}
//		}
		return b;
	}
	
}
