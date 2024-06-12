class main {
    void main() {
        int n1 = (int)(Math.random() * 100);
        int n2 = (int)(Math.random() * 100);

        System.out.println("Primer numero: " + n1);
        System.out.println("Segundo numero: " + n2);
        System.out.println("Numero mayor: " + (n1 > n2 ? n1 : n2));
    }
}
