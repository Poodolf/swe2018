package rekit.mymod.pickups;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Pickup;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils.LoadMe;

/**
 * An extra life which has a value of [0,300] and a life.
 */
@LoadMe
public class ExtraLife extends Pickup {

	/**
	 * Constructor with position.
	 *
	 * @param startPos
	 *            the position.
	 */
	public ExtraLife(Vec startPos) {
		super(startPos, new Vec(), new Vec(1));
	}

	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "custom/scuffed_glasses.png", true, true, false, false);
	}
	
	@Override
	public ExtraLife create(Vec startPos, String... options) {
		return new ExtraLife(startPos);
	}

	protected int getValue() {
		return (int) (Math.random() * 300);
	}

	@Override
	public void perform(GameElement arg0) {
		this.getScene().getPlayer().addPoints(this.getValue());
		this.getScene().getPlayer().setLives(getScene().getPlayer().getLives() + 1);
		this.addDamage(1);
	}

}