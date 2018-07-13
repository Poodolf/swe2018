package rekit.mymod.inanimates;

import java.util.ArrayList;

import rekit.core.GameGrid;
import rekit.logic.filters.Filter;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.inanimates.states.IdleState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

import rekit.mymod.MyModScene;
import rekit.mymod.filter.WrooongFilter;

@LoadMe
public class AnswerBox extends DynamicInanimate {

	private Polygon triangle;
	private Vec startPos;
	private ArrayList<BlockadeBox> blockadeBoxes;
	
	private AnswerBox() {
		super();
		blockadeBoxes = new ArrayList<BlockadeBox>();
	}
	
	public AnswerBox(Vec startPos, ArrayList<BlockadeBox> blockadeBoxes) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));

		this.startPos = startPos;
		this.blockadeBoxes = blockadeBoxes;
		
		this.triangle = new Polygon(new Vec(), new Vec[] {
				new Vec(-0.3, -0.5),
				new Vec(0.3, -0.5)
		});
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "custom/question.png", true, true, false, false);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
		this.setPos(getPos().add(getVel().scalar(deltaTime / 1000f)));
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);
		
		MyModScene.getInstance().addGameElement(new FlyingText(startPos.addY(-1), "BOOM MUTHERFUCKER"));
		for (BlockadeBox box : blockadeBoxes) {
			box.destroy();
		}
		
		MyModScene.getInstance().getModel().setFilter(Filter.get(WrooongFilter.class));
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new AnswerBox(startPos, blockadeBoxes);
	}
	
	@Override
	public Integer getZHint() {
		return 1;
	}
}
