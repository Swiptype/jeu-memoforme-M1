package com.memoform.main;

import com.memoform.game.DrawingStep;
import com.memoform.game.ImportFileModelStep;
import com.memoform.game.MemoForm;
import com.memoform.game.MemoForm.MFContainer;
import com.memoform.game.ScoringStep;
import com.memoform.game.ShowContainerStep;
import com.memoform.game.StepLink;
import com.memoform.model.strategy.SimpleEvaluationStrategy;

@SuppressWarnings("unused")
public class GameMain {
    
    public static void main(String[] args) {
        basicGame();
    }

    private static void basicGame() {
        MemoForm game = new MemoForm();

        StepLink importModel = new ImportFileModelStep();
        
        StepLink show = new ShowContainerStep(10);
        importModel.setNext(show);

        StepLink draw = new DrawingStep(MFContainer.REPLICA);
        show.setNext(draw);

        StepLink score = new ScoringStep(new SimpleEvaluationStrategy());
        draw.setNext(score);

        game.launch(importModel);
    }

    private static void testEval() {
        MemoForm game = new MemoForm();

        StepLink importModel = new ImportFileModelStep();
        StepLink importReplica = new ImportFileModelStep(MFContainer.REPLICA); 
        StepLink score = new ScoringStep(new SimpleEvaluationStrategy());

        importModel.setNext(importReplica);
        importReplica.setNext(score);

        game.launch(importModel);
    }

}
