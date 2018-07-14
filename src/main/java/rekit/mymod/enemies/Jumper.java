package rekit.mymod.enemies;

import java.util.Random;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils.LoadMe;

/**
 * Enemy who chases the player and jumps
 *
 * @author Jianan Ye
 */
@LoadMe
public final class Jumper extends Enemy {			

	private static final int POINTS = 0;
	
	private float x = 0.0f;
	private float y = 0.0f;

	/**
	 * Prototype constructor used to dynamically {@link Jumper#create(Vec, String...)}
	 * clones without knowing the concrete type.
	 */
	public Jumper() {
		super();
	}

	/**
	 * Standard constructor that saves the initial position.
	 *
	 * @param startPos
	 *            the initial position of the Enemy.
	 */
	public Jumper(Vec startPos) {
		super(startPos, new Vec(), new Vec(1));
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "roboindustries/walker_idle.png", true, true, false, false);
	}

	@Override
	protected void innerLogicLoop() {
		super.innerLogicLoop();
//		if (getScene().getPlayer().getPos().x > this.getPos().x) {
//			this.currentAngle += this.deltaTime * -Jumper.ANGLE_SPEED;
//		} else {
//			this.currentAngle += this.deltaTime * Jumper.ANGLE_SPEED;
//		}
	}

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}

		// If hit from above:
		if (dir == Direction.UP) {
			// give the player points
			this.getScene().getPlayer().addPoints(Jumper.POINTS);
			// Let the player jump 
			element.killBoost();
			// kill the enemy
			this.addDamage(1);
		} else {
			// Touched dangerous side
			// Give player damage
			element.addDamage(1);
			// Kill the enemy itself
			this.destroy();
		}
	}

	@Override
	public void collidedWithSolid(Frame collision, Direction dir) {
		// standard behavior, that prevents clipping into other blocks
		super.collidedWithSolid(collision, dir);
		

		if (getScene().getPlayer().getPos().x > this.getPos().x) {
			x = 7.0f;
		} else {
			x = -7.0f;
		}
		if (getScene().getPlayer().getPos().y < this.getPos().y) {
			y = -12.0f;
		}
		this.setVel(new Vec(x , y));
	}

	@Override
	public Jumper create(Vec startPos, String... options) {
		return new Jumper(startPos);
	}
}