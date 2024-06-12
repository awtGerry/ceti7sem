class main {
    void main() {
        int[] numbers = new int[5];
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        /* pedir numeros */
        for (int i = 0; i < numbers.length; i++) {
            System.out.print("Numero [" + (i + 1) + "]: ");
            numbers[i] = scanner.nextInt();
        }

        /* ordenar numeros */
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] > numbers[j]) {
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }

        /* mostrar numeros ordenados */
        System.out.println("Lista ordenada:");
        System.out.print("[");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + ", ");
        }
        System.out.println("\b\b]"); // borrar ultima coma y espacio
        scanner.close();
    }
}
