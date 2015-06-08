package nl.SugCube.DirtyArrows.ability;

import nl.SugCube.DirtyArrows.DirtyArrows;
import nl.SugCube.DirtyArrows.util.Util;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class Iron implements Runnable, Listener {

	public DirtyArrows plugin;
	
	public Iron(DirtyArrows instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		for (FallingBlock fb : plugin.anvils.keySet()) {
			for (Entity en : fb.getWorld().getEntities()) {
				if (!(en instanceof LivingEntity)) {
					continue;
				}
				if (Util.inRegionOf(fb.getLocation(), en.getLocation(), 1)) {
					((LivingEntity) en).damage(3.0f);
				}
			}
			
			plugin.anvils.put(fb, plugin.anvils.get(fb) - 1);
			if (plugin.anvils.get(fb) < 0) {
				plugin.anvils.remove(fb);
			}
		}
	}
	
	@EventHandler
	public void onBlockChange(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof FallingBlock) {
			if (plugin.anvils.containsKey(e.getEntity())) {
				for (Entity ent : e.getEntity().getWorld().getEntities()) {
					if (ent instanceof LivingEntity) {
						LivingEntity len = (LivingEntity) ent;
						if (Util.inRegionOf(len.getLocation(), e.getEntity().getLocation(), 3)) {
							if (len instanceof Player) {
								((Player) len).playSound(e.getEntity().getLocation(), Sound.ANVIL_LAND, 1, 1);
							}
							len.damage(10.0f);
						}
					}
				}
			}
			plugin.anvils.remove(e.getEntity());
		}
	}
	
}
