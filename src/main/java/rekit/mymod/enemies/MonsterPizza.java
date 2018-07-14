package rekit.mymod.enemies;

import java.util.Random;

import net.jafama.FastMath;
import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

/**
 * Enemy who chases the player and jumps a little bit
 *
 * @author Angelo Aracri
 */
@LoadMe
public final class MonsterPizza extends Enemy {			

	private static final int POINTS = 0;
	private static final RGBAColor COLOR_OUTER = new RGBAColor(226,60,68,255);
	private static final RGBAColor COLOR_INNER = new RGBAColor(248,100,101,255);
	private static final RGBAColor COLOR_SALAMI = new RGBAColor(87,87,65,255);
	private static final Vec SIZE_OUTER = new Vec(0.7f, 0.7f);
	private static final Vec SIZE_INNER = new Vec(0.6f, 0.6f);
	private static final Vec SIZE_SALAMI = new Vec(0.1f, 0.1f);
	private static int NUM_SALAMI = 10;
	private static float ANGLE_SPEED = 0.010f;
	
	private static Random RNG = new Random();
	private float[] radius;
	private float currentAngle;
	private float x = 0.0f;
	private float y = 0.0f;
	/**
	 * Prototype constructor used to dynamically {@link MonsterPizza#create(Vec, String...)}
	 * clones without knowing the concrete type.
	 */
	public MonsterPizza() {
		super();
	}

	/**
	 * Standard constructor that saves the initial position.
	 *
	 * @param startPos
	 *            the initial position of the Enemy.
	 */
	public MonsterPizza(Vec startPos) {
		super(startPos, new Vec(), MonsterPizza.SIZE_OUTER);

		this.radius = new float[MonsterPizza.NUM_SALAMI];
		for (int i = 0; i < MonsterPizza.NUM_SALAMI; i++) {
			this.radius[i] = 0.1f + MonsterPizza.RNG.nextFloat() * 0.15f;
		}
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawCircle(this.getPos(), this.getSize(), MonsterPizza.COLOR_OUTER);
		f.drawCircle(this.getPos(), MonsterPizza.SIZE_INNER, MonsterPizza.COLOR_INNER);

		float phi = this.currentAngle;
		for (int i = 0; i < MonsterPizza.NUM_SALAMI; i++) {
			// get polar coordinates
			phi += 2 * Math.PI / MonsterPizza.NUM_SALAMI;
			float rad = this.radius[i];

			// convert to x, y
			Vec pos = new Vec(FastMath.sinQuick(phi) * rad, FastMath.cosQuick(phi) * rad);

			// SALAMI
			f.drawCircle(this.getPos().add(pos), MonsterPizza.SIZE_SALAMI, MonsterPizza.COLOR_SALAMI);
		}
	}

	@Override
	protected void innerLogicLoop() {
		// Do usual entity logic (applying velocity, ...)
		super.innerLogicLoop();

		// newAngle = time * angleSpeed
		// deltaTime is time in ms since last call of innerLogicLoop
		if (getScene().getPlayer().getPos().x > this.getPos().x) {
			this.currentAngle += this.deltaTime * -MonsterPizza.ANGLE_SPEED;
		} else {
			this.currentAngle += this.deltaTime * MonsterPizza.ANGLE_SPEED;
		}
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
			this.getScene().getPlayer().addPoints(MonsterPizza.POINTS);
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
			y = -6.0f;
		}
		this.setVel(new Vec(x , y));
	}

	@Override
	public MonsterPizza create(Vec startPos, String... options) {
		return new MonsterPizza(startPos);
	}
}