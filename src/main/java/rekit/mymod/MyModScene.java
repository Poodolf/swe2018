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
import rekit.mymod.quiz.questgenerator.Quest;
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
	    // Change this to add a custom handler when the player attacks (space key)
	    this.setAttackHandler((a) -> this.addGameElement(new FlyingText(this.getPlayer().getPos().addY(-1.5F), "Attack")));
	    
	    this.setOffsetWildCard(true);
	    this.setCameraTarget(this.getPlayer());
	}

	@Override
	public void start() {
		super.start();
		screenOffsetInBlocks = 0;
		MathQuestGenerator mqs = new MathQuestGenerator();
		
		Quest q1 = mqs.binearToDezimalQuest();
		displayQuestion(new Question(q1.getFrage(), new Answer(q1.getAntwortT(), true), new Answer(q1.getAntwortF1()),
				new Answer(q1.getAntwortF2()), new Answer(q1.getAntwortF3())));
		
		Quest q2 = mqs.determinantQuest();
		displayQuestion(new Question(q2.getFrage(), new Answer(q2.getAntwortT(), true), new Answer(q2.getAntwortF1()),
				new Answer(q2.getAntwortF2()), new Answer(q2.getAntwortF3())));
		
		Quest q3 = mqs.determinantQuest();
		displayQuestion(new Question(q3.getFrage(), new Answer(q3.getAntwortT(), true), new Answer(q3.getAntwortF1()),
				new Answer(q3.getAntwortF2()), new Answer(q3.getAntwortF3())));
		
		Quest q4 = mqs.determinantQuest();
		displayQuestion(new Question(q4.getFrage(), new Answer(q4.getAntwortT(), true), new Answer(q4.getAntwortF1()),
				new Answer(q4.getAntwortF2()), new Answer(q4.getAntwortF3())));
		
		Quest q5 = mqs.determinantQuest();
		displayQuestion(new Question(q5.getFrage(), new Answer(q5.getAntwortT(), true), new Answer(q5.getAntwortF1()),
				new Answer(q5.getAntwortF2()), new Answer(q5.getAntwortF3())));
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
		
		for (int i = 0; i < 5; i++) {
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