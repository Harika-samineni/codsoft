import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

class QuizQuestion {
    private String question;
    private String[] options;
    private int correctOption;

    public QuizQuestion(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

public class QuizApp {
    private int score = 0;
    private int currentQuestionIndex = 0;
    private Timer timer;
    private boolean timedOut = false;

    private QuizQuestion[] questions = {
        new QuizQuestion("What is the capital of France?",
            new String[]{"A. London", "B. Berlin", "C. Paris", "D. Madrid"}, 2),
        new QuizQuestion("Which planet is known as the Red Planet?",
            new String[]{"A. Venus", "B. Earth", "C. Mars", "D. Jupiter"}, 2),
        new QuizQuestion("What is the largest mammal in the world?",
            new String[]{"A. Elephant", "B. Giraffe", "C. Blue Whale", "D. Dolphin"}, 2)
    };

    public void startQuiz() {
        System.out.println("Welcome to the Quiz!");
        for (int i = 0; i < questions.length; i++) {
            QuizQuestion question = questions[i];
            displayQuestion(question);
            waitForAnswer(question);
            if (timedOut) {
                System.out.println("Time's up! The correct answer was: " + question.getOptions()[question.getCorrectOption()]);
            }
            timedOut = false;
        }
        displayResult();
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + question.getQuestion());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i]);
        }
    }

    private void waitForAnswer(QuizQuestion question) {
        Scanner scanner = new Scanner(System.in);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                timedOut = true;
                scanner.nextLine(); // Consume any pending input
                timer.cancel();
            }
        }, 15000); // 15 seconds timer

        System.out.print("Enter your choice (A/B/C/D): ");
        String userAnswer = scanner.nextLine().trim().toUpperCase();
        timer.cancel();

        int correctOption = question.getCorrectOption();
        if (userAnswer.equals("A") || userAnswer.equals("B") || userAnswer.equals("C") || userAnswer.equals("D")) {
            if (userAnswer.charAt(0) - 'A' == correctOption) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong!");
            }
        } else {
            System.out.println("Invalid choice!");
        }
        currentQuestionIndex++;
    }

    private void displayResult() {
        System.out.println("\nQuiz ended!");
        System.out.println("Your score: " + score + " out of " + questions.length);
    }

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.startQuiz();
    }
}
