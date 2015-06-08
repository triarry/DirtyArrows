package nl.SugCube.DirtyArrows.ability;

import nl.SugCube.DirtyArrows.DirtyArrows;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class Airship implements Runnable {

public static DirtyArrows plugin;
	
	public Airship(DirtyArrows instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		for (Projectile proj : plugin.airship) {
			if (proj.getShooter() instanceof Player) {
				Player player = (Player) proj.getShooter();
				player.setVelocity(proj.getVelocity().multiply(0.8));
			}
		}
	}
	
}
