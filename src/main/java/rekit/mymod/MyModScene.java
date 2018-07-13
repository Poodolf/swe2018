package rekit.mymod;

import java.io.IOException;
import java.util.ArrayList;

import rekit.logic.gameelements.*;
import rekit.logic.gameelements.inanimate.Inanimate;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import rekit.config.GameConf;
import rekit.logic.GameModel;
import rekit.logic.ILevelScene;
import rekit.logic.level.LevelFactory;
import rekit.logic.scene.LevelScene;
import rekit.mymod.enemies.Pizza;
import rekit.mymod.enemies.SpriteDummy;
import rekit.mymod.inanimates.AnswerBox;
import rekit.mymod.inanimates.BlockadeBox;
import rekit.mymod.inanimates.FlyingText;
import rekit.mymod.inanimates.ParticleDummy;
import rekit.persistence.level.LevelDefinition;
import rekit.persistence.level.LevelType;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.LambdaUtil;

/**
 * A test scene which can be used in {@link GameConf#DEBUG} context.
 */
public final class MyModScene extends LevelScene {

	private MyModScene(GameModel model) {
		super(model, LevelFactory.createLevel(LambdaUtil.invoke(MyModScene::getTestLevel)));
		instance = this;
	}

	private static LevelDefinition getTestLevel() throws IOException {
		PathMatchingResourcePatternResolver resolv = new PathMatchingResourcePatternResolver();
		Resource res = resolv.getResource("/conf/mymod.dat");
		return new LevelDefinition(res.getInputStream(), LevelType.Test);
	}

	
	
	
	
    private static MyModScene instance;
    

    public static MyModScene getInstance(){
        return instance;
    }
	
	
	
	
	/**
	 * Create the scene by model and options
	 *
	 * @param model
	 *            the model
	 * @param options
	 *            the options
	 * @return the new scene
	 */
	public static ILevelScene create(GameModel model, String... options) {
		return new MyModScene(model);
	}

	@Override
	  public void init() {
	    super.init();
	    // Change this to add a custom handler when the player attacks (space key)
	    this.setAttackHandler((a) -> this.addGameElement(new FlyingText(this.getPlayer().getPos().addY(-1.5F), "Attack")));
	  }

	@Override
	public void start() {
		super.start();
		
		Pizza pizza = new Pizza(new Vec(12, 4));
		this.addGameElement(pizza);
		
				
		
		this.addGameElement(new FlyingText(new Vec(12, 2), "This is\nthe Pizza added\nin MyModScene"));
		

		BlockadeBox bb1 = new BlockadeBox(new Vec(20, 4));
		BlockadeBox bb2 = new BlockadeBox(new Vec(20, 5));
		BlockadeBox bb3 = new BlockadeBox(new Vec(20, 6));
		BlockadeBox bb4 = new BlockadeBox(new Vec(20, 7));
		this.addGameElement(bb1);
		this.addGameElement(bb2);
		this.addGameElement(bb3);
		this.addGameElement(bb4);
		
		ArrayList<BlockadeBox> bbList = new ArrayList<BlockadeBox>();
		bbList.add(bb1);
		bbList.add(bb2);
		bbList.add(bb3);
		bbList.add(bb4);
		
		this.addGameElement(new AnswerBox(new Vec(2, 5), bbList));
		
	}
	
	
}
