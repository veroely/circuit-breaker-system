# Circuit Breaker System

EJecución de las pruebas en K6

# Configuración en el cliente Toxiproxy

## Crear el proxy

1. Ejecuta el siguiente comando en tu terminal:
   ```bash
   curl --location 'http://localhost:8474/proxies' \
   --header 'Content-Type: application/json' \
   --data '{
       "name": "electric_api_proxy",
       "listen": "0.0.0.0:6000",
       "upstream": "wiremock:8080",
       "enabled": true
   }'

# Crear la latencia
Ejecuta el siguiente comando en tu terminal:
bash
curl --location 'http://localhost:8474/proxies/electric_api_proxy/toxics' \
--header 'Content-Type: application/json' \
--data '{
    "name": "test_latency",
    "type": "latency",
    "stream": "downstream",
    "toxicity": 1.0,
    "attributes": {
        "latency": 2000,
        "jitter": 500 
    }
}'

# Comandos para ejecutar el script de carga
Ejecuta el siguiente comando en tu terminal:
bash
k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\