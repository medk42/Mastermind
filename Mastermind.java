import java.util.Scanner;

class Mastermind {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        int positionCount = getPositiveInteger(scanner, "Enter position count: ");
        int colorCount = getPositiveInteger(scanner, "Enter color count: ");

        System.out.printf("Position count: %d    Color count: %d\n", positionCount, colorCount);

        scanner.close();
    }

    public static int getPositiveInteger(Scanner scanner, String message) {
        System.out.print(message);
        int retVal = -1;
        while (retVal <= 0) {
            try {
                retVal = Integer.parseInt(scanner.nextLine());
            } catch(Exception e) {}
            if (retVal <= 0) {
                System.out.println("Please enter postive integer!");
                System.out.print(message);
            }
        }
        return retVal;
    }
}