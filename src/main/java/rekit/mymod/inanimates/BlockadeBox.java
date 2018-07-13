package rekit.mymod.inanimates;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.inanimates.states.IdleState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

import rekit.mymod.MyModScene;

@LoadMe
public class BlockadeBox extends DynamicInanimate {

	private Polygon triangle;
	
	private BlockadeBox() {
		super();
	}
	
	public BlockadeBox(Vec startPos) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));

		this.triangle = new Polygon(new Vec(), new Vec[] {
				new Vec(-0.3, -0.5),
				new Vec(0.3, -0.5)
		});
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "custom/plasmablockade.png", true, true, false, false);
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new BlockadeBox(startPos);
	}
	
	@Override
	public Integer getZHint() {
		return 1;
	}
}
