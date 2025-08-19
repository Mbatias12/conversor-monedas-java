package com.alura.conversor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Map<Integer, String[]> PRESETS = Map.of(
            1, new String[]{"USD", "ARS"},
            2, new String[]{"ARS", "USD"},
            3, new String[]{"USD", "BRL"},
            4, new String[]{"BRL", "USD"},
            5, new String[]{"USD", "COP"},
            6, new String[]{"COP", "USD"}
    );

    public static void main(String[] args) {
        String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        ExchangeRateClient client = new ExchangeRateClient(apiKey);

        Scanner sc = new Scanner(System.in);
        System.out.println("=== Conversor de Monedas (Java) ===");

        while (true) {
            printMenu();
            int op = readInt(sc, "Elige una opción: ");

            if (op == 0) {
                System.out.println("¡Gracias! Hasta la próxima.");
                break;
            } else if (op == 7) {
                String from = readCode(sc, "Código FROM (ej. USD): ");
                String to   = readCode(sc, "Código TO   (ej. CLP): ");
                BigDecimal amt = readAmount(sc, "Monto a convertir: ");
                convertAndShow(client, from, to, amt);
            } else if (PRESETS.containsKey(op)) {
                String[] pair = PRESETS.get(op);
                BigDecimal amt = readAmount(sc, "Monto en " + pair[0] + ": ");
                convertAndShow(client, pair[0], pair[1], amt);
            } else {
                System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    private static void convertAndShow(ExchangeRateClient client, String from, String to, BigDecimal amount) {
        try {
            var result = client.convert(from, to, amount);
            String left  = CurrencyFormatter.fmt(amount, from) + " (" + from + ")";
            String right = CurrencyFormatter.fmt(result, to) + " (" + to + ")";
            System.out.println(left + " = " + right);
        } catch (Exception e) {
            System.out.println("No se pudo convertir: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println(
                "-------------------------------\n" +
                "[1] USD -> ARS     [2] ARS -> USD\n" +
                "[3] USD -> BRL     [4] BRL -> USD\n" +
                "[5] USD -> COP     [6] COP -> USD\n" +
                "[7] Otra conversión (libre)\n" +
                "[0] Salir\n" +
                "-------------------------------"
        );
    }

    private static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Ingresa un número: "); }
        return sc.nextInt();
    }

    private static String readCode(Scanner sc, String prompt) {
        System.out.print(prompt);
        String code = sc.next().trim().toUpperCase();
        if (code.length() != 3) throw new IllegalArgumentException("Código ISO-4217 inválido.");
        return code;
    }

    private static BigDecimal readAmount(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextBigDecimal()) { sc.next(); System.out.print("Ingresa un monto válido: "); }
        BigDecimal v = sc.nextBigDecimal();
        if (v.signum() <= 0) throw new IllegalArgumentException("El monto debe ser > 0.");
        return v;
    }
}