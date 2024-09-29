import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger beautifulNicknameLength3 = new AtomicInteger();
    private static final AtomicInteger beautifulNicknameLength4 = new AtomicInteger();
    private static final AtomicInteger beautifulNicknameLength5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        String[] texts = nicknameGenerator();

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {

                StringBuilder builder = new StringBuilder();
                for (int j = text.length() - 1; j >= 0; j--) {
                    builder.append(text.charAt(j));
                }

                if (text.contentEquals(builder)) increasingCounter(text);

            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {

                if (text.contains("a") && !text.contains("b") && !text.contains("c")) increasingCounter(text);
                else if (!text.contains("a") && text.contains("b") && !text.contains("c")) increasingCounter(text);
                else if (!text.contains("a") && !text.contains("b") && text.contains("c")) increasingCounter(text);

            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {

                for (int i = 1; i < text.length(); i++) {
                    if (!(text.charAt(i) < text.charAt(i - 1))) {
                        increasingCounter(text);
                    }
                }

            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(beautifulNicknameLength3);
        System.out.println(beautifulNicknameLength4);
        System.out.println(beautifulNicknameLength5);
    }

    private static String[] nicknameGenerator() {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void increasingCounter(String text) {
        if (text.length() == 3) beautifulNicknameLength3.addAndGet(1);
        else if (text.length() == 4) beautifulNicknameLength4.addAndGet(1);
        else beautifulNicknameLength5.addAndGet(1);
    }
}
