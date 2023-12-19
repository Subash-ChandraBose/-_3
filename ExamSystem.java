import java.util.Scanner;

class User {
    String username;
    String password;
    String profile;

    User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}

class Exam {
    String[] questions;
    String[] correctAnswers;
    String[] userAnswers;

    Exam(String[] questions, String[] correctAnswers) {
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.userAnswers = new String[questions.length];
    }
}

public class ExamSystem {
    static User currentUser;
    static Exam currentExam;
    static boolean examInProgress = false;
    static int score = 0;
    static final int EXAM_DURATION = 60; 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Exam System!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your profile (Student/Administrator): ");
        String profile = scanner.nextLine();

        currentUser = new User(username, password, profile);

         while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

 if (currentUser != null && username.equals(currentUser.username) && password.equals(currentUser.password)) {
            if (currentUser.profile.equals("Administrator")) {
                adminMenu();
            } else {
                studentMenu();
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    static void adminMenu() {
        System.out.println("Welcome, Administrator!");
    }

    static void studentMenu() {
        System.out.println("Welcome, Student!");
      System.out.println("Your Exam is Ready...");
        String[] questions = {
                "What is the capital of Tamilnadu?",
                "What is 2 + 2?",
                "What is 5 * 0?"
        };
        String[] correctAnswers = {"Chennai", "4", "0"};

        currentExam = new Exam(questions, correctAnswers);

        System.out.println("---- Exam Questions ----");
        for (int i = 0; i < currentExam.questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + currentExam.questions[i]);
            System.out.print("Your Answer: ");
            Scanner answerScanner = new Scanner(System.in);
            String answer = answerScanner.nextLine();
            currentExam.userAnswers[i] = answer;
        }

        startTimer();

        System.out.println("---- Your Answers ----");
        for (int i = 0; i < currentExam.userAnswers.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + currentExam.userAnswers[i]);
        }

        calculateScore();
    }

    static void startTimer() {
        Thread timerThread = new Thread(() -> {
            try {
                for (int i = EXAM_DURATION; i > 0; i--) {
                    System.out.println("Time remaining: " + i + " seconds");
                    Thread.sleep(1000); // Sleep for 1 second
                }
                autoSubmitExam();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timerThread.start();
    }

    static void autoSubmitExam() {
        System.out.println("Time's up! Exam auto-submitted.");
        examInProgress = false;
        calculateScore();
    }

    static void calculateScore() {
        score = 0;
        for (int i = 0; i < currentExam.questions.length; i++) {
            if (currentExam.userAnswers[i].equalsIgnoreCase(currentExam.correctAnswers[i])) {
                score++;
            }
        }
        System.out.println("Your Score: " + score + " out of " + currentExam.questions.length);
    }
}
