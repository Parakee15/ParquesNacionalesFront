package ioc;

public class App {
    public String getGreeting() {
        return "IoC / DI Intro.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
