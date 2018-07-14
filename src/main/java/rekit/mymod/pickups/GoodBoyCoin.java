package rekit.mymod.pickups;

import rekit.logic.gameelements.type.Coin;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

/**
 * A new coin which has a value of [0,300].
 *
 * @author Dominik Fuchss
 *
 */
@LoadMe
public class GoodBoyCoin extends Coin {
	/**
	 * The default color of the coin.
	 */
	private static RGBAColor color = new RGBAColor(205, 205, 60);
	/**
	 * The shadow color of the coin.
	 */
	private static RGBAColor darkColor = new RGBAColor(255, 255, 0);

	/**
	 * Prototype constructor.
	 */
	public GoodBoyCoin() {
		super();
	}

	/**
	 * Constructor with position.
	 *
	 * @param startPos
	 *            the position.
	 */
	public GoodBoyCoin(Vec startPos) {
		super(startPos);
	}

	@Override
	protected RGBAColor getColor() {
		return GoodBoyCoin.color;
	}

	@Override
	protected RGBAColor getDarkerColor() {
		return GoodBoyCoin.darkColor;
	}

	@Override
	public Coin create(Vec startPos, String... options) {
		return new GoodBoyCoin(startPos);
	}

	@Override
	protected int getValue() {
		return (int) (Math.random() * 300);
	}

}
