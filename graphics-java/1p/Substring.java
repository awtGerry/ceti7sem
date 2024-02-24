class main {
    void main() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Ingrese la cadena: ");
        String phrase = scanner.nextLine();

        for (int i = phrase.length(); i > 0; i--) {
            System.out.println(phrase.substring(0, i));
        }

        for (int i = phrase.length() - 1; i >= 0; i--) {
            System.out.println(phrase.substring(i));
        }

        scanner.close();
    }
}
