package rekit.mymod.enemies;

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
	}

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}
		if (dir == Direction.UP) {
			element.killBoost();
			this.addDamage(1);
		} else {
			element.addDamage(1);
		}
	}

	@Override
	public void collidedWithSolid(Frame collision, Direction dir) {
		
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