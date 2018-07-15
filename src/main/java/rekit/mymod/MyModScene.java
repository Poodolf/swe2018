package rekit.mymod;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import rekit.config.GameConf;
import rekit.logic.GameModel;
import rekit.logic.ILevelScene;
import rekit.logic.level.LevelFactory;
import rekit.logic.scene.LevelScene;
import rekit.mymod.inanimates.AnswerBox;
import rekit.mymod.inanimates.BlockadeBox;
import rekit.mymod.inanimates.FlyingText;
import rekit.mymod.quiz.Answer;
import rekit.mymod.quiz.Question;
import rekit.mymod.quiz.questgenerator.MathQuestGenerator;
import rekit.persistence.level.LevelDefinition;
import rekit.persistence.level.LevelType;
import rekit.primitives.geometry.Vec;
import rekit.util.LambdaUtil;

/**
 * A test scene which can be used in {@link GameConf#DEBUG} context.
 */
public final class MyModScene extends LevelScene {

    private static MyModScene instance;
    
	private MyModScene(GameModel model) {
		super(model, LevelFactory.createLevel(LambdaUtil.invoke(MyModScene::getTestLevel)));
		instance = this;
	}

	private static LevelDefinition getTestLevel() throws IOException {
		PathMatchingResourcePatternResolver resolv = new PathMatchingResourcePatternResolver();
		Resource res = resolv.getResource("/conf/mymod.dat");
		return new LevelDefinition(res.getInputStream(), LevelType.Test);
	}

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
	    this.setOffsetWildCard(true);
	    this.setCameraTarget(this.getPlayer());
	}

	@Override
	public void start() {
		super.start();
		screenOffsetInBlocks = 0;
		MathQuestGenerator mqs = new MathQuestGenerator();
		
		displayQuestion(new Question("Es steht eine pull request aus" + '\n' + "was wirst du tuen?", new Answer("annehmen", true), 
				new Answer("ablehnen"), new Answer("ignorieren"), new Answer("kacken")));
		displayQuestion(mqs.determinantQuest());
		displayQuestion(mqs.binearToDezimalQuest());
		displayQuestion(mqs.determinantQuest());
		displayQuestion(mqs.binearToDezimalQuest());
		displayQuestion(mqs.determinantQuest());
		displayQuestion(mqs.binearToDezimalQuest());
		displayQuestion(mqs.determinantQuest());
		displayQuestion(mqs.binearToDezimalQuest());
	}
	
	private int screenOffsetInBlocks = 0;
	private static final int SCREEN_WIDTH_IN_BLOCKS = 24;
	private static final double ANSWER_Y_POSITION = 4.7;
	private static final double ANSWER_BLOCK_Y_POSITION = 5;
	private static final int SPACE_BETWEEN_ANSWERS = 3;
	private static final int FIRST_ANSWER_POSITION = 8;
	private static final double QUESTION_Y_POSITION = 2;
	
	public void displayQuestion(Question question) {
		this.addGameElement(new FlyingText(new Vec(screenOffsetInBlocks + SCREEN_WIDTH_IN_BLOCKS / 2, QUESTION_Y_POSITION), question.getText()));

		ArrayList<BlockadeBox> blockadeList = new ArrayList<BlockadeBox>();
		
		//set blockadeheight
		int blockadeheight = 8;
		
		for (int i = 0; i < blockadeheight; i++) {
			BlockadeBox blockade = new BlockadeBox(new Vec(screenOffsetInBlocks + SCREEN_WIDTH_IN_BLOCKS, 7 - i));
			blockadeList.add(blockade);
			this.addGameElement(blockade);
		}
		
		int extraOffset = FIRST_ANSWER_POSITION;
		
		for (Answer answer : question.getShuffledAnswers()) {
			int xPos = screenOffsetInBlocks + extraOffset;
			this.addGameElement(new FlyingText(new Vec(xPos, ANSWER_Y_POSITION), answer.getText()));
			this.addGameElement(new AnswerBox(new Vec(xPos, ANSWER_BLOCK_Y_POSITION), blockadeList, answer.isRight()));
			extraOffset += SPACE_BETWEEN_ANSWERS;
		}
		
		screenOffsetInBlocks += SCREEN_WIDTH_IN_BLOCKS;
	}
}