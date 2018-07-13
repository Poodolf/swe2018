package rekit.mymod.inanimates;

import java.util.ArrayList;

import rekit.core.GameGrid;
import rekit.logic.filters.Filter;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.filter.TrueFilter;
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


	private boolean isCorrectAnswer = false;
	
	private AnswerBox() {
		super();
		blockadeBoxes = new ArrayList<BlockadeBox>();
		startPos = new Vec(0,0);
	}

	private float animValue = -2f;
	private boolean inJump = false;

	public AnswerBox(Vec startPos, ArrayList<BlockadeBox> blockadeBoxes, boolean isCorrectAnswer) {

        super(startPos, new Vec(1), new RGBAColor(0,0,0,1));
        this.startPos = startPos;
	    this.blockadeBoxes = blockadeBoxes;
	    this.isCorrectAnswer = isCorrectAnswer;

	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "custom/question.png", true, true, false, false);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();

        if(inJump && this.getPos().y  <= startPos.y){

            animValue += (deltaTime / 1000f) * 4;

            this.setVel(new Vec(0,velocityOverTime(animValue)));
            this.setPos(getPos().add(getVel().scalar(deltaTime / 1000f)));
        } else {
            animValue = -2f;
            inJump = false;
            this.setVel(new Vec(0,0));
            this.setPos(startPos);
        }

    }
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);

		if (Math.abs(dir.getAngle() - 3.14d) <  0.1f ) {

		    inJump = true;

		    if(!isCorrectAnswer) {

                MyModScene.getInstance().getModel().setFilter(Filter.get(WrooongFilter.class));
                getScene().getPlayer().addDamage(100);
                return;
            }

            for (BlockadeBox box : blockadeBoxes) {
                box.destroy();
            }

            MyModScene.getInstance().getModel().setFilter(Filter.get(TrueFilter.class));

        }


	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new AnswerBox(startPos, blockadeBoxes, isCorrectAnswer);
	}
	
	@Override
	public Integer getZHint() {
		return 1;
	}


	private double velocityOverTime(double animValue){
		return (Math.pow(animValue, 3));
	}
}
