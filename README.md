# Conversor de Monedas – Java (CLI)

Challenge Alura Latam — Backend con Java. Convierte entre monedas usando una API de tasas de cambio.

## 🔎 Funcionalidades
- 6 conversiones preconfiguradas + modo libre
- Consumo de API con manejo de errores (clave inválida, códigos no soportados, cuota, etc.)
- Formateo de moneda por localidad
- Menú en loop y validaciones de entrada

## 🧰 Stack
Java 17 · Maven · java.net.http.HttpClient · Gson · JUnit 5

## 🗂️ Estructura
```
src/main/java/com/alura/conversor/
  Main.java                # CLI (menú/loop)
  ExchangeRateClient.java  # Cliente HTTP
  CurrencyFormatter.java   # Formateo de moneda
docs/img/                  # pon aquí tus capturas
```

## 🚀 Requisitos
- Java 17 y Maven instalados
- Cuenta gratuita en servicio de tasas (obtén tu **API Key**)

### Variables de entorno
Configura tu clave **antes** de ejecutar:

- Linux/macOS:
  ```bash
  export EXCHANGE_RATE_API_KEY=TU_KEY
  ```
- Windows PowerShell:
  ```powershell
  $env:EXCHANGE_RATE_API_KEY="TU_KEY"
  ```

## ▶️ Ejecución
```bash
mvn clean package
java -cp target/conversor-monedas-java-1.0.0.jar com.alura.conversor.Main
```

## 📸 Capturas / Demo
Guarda imágenes en `docs/img/` y referencia aquí, por ejemplo:
- ![Menú principal](docs/img/menu.png)
- ![Ejemplo de conversión](docs/img/ejemplo-1.png)

## 🆘 Troubleshooting
- `invalid-key` → revisa `EXCHANGE_RATE_API_KEY`.
- `unsupported-code` → verifica códigos ISO-4217 (ej. USD, CLP, BRL).
- `quota-reached` → espera el reset diario o usa plan superior.