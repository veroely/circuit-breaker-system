# Circuit Breaker System

Implementación del patrón Circuit Breaker en una arquitectura de microservicios con Java Spring Boot, utilizando Resilience4j para manejo de fallos y recuperación.

## 🚀 Características

- Implementación del patrón Circuit Breaker con Resilience4j
- Monitoreo en tiempo real con Prometheus y Grafana
- Configuración dinámica mediante variables de entorno
- Simulación de fallos con Toxiproxy

## 🛠️ Requisitos Previos

- Docker 20.10 o superior
- Docker Compose 2.0 o superior

## 🚀 Iniciar el Proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/circuit-breaker-system.git
   cd circuit-breaker-system
   ```

2. **Configurar variables de entorno**:
   Editar el archivo `.env` según sea necesario:
   ```bash
   # Tiempos de espera en milisegundos
   CONNECT_TIMEOUT_MS=3500
   READ_TIMEOUT_MS=3500
   CONNECT_TIMEOUT_MS02=3500
   READ_TIMEOUT_MS02=3500
   ```

3. **Iniciar los servicios con Docker Compose**:
   ```bash
   docker-compose up --build
   ```

## 🌐 Servicios Desplegados

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| Electric Bill Service | 5000 | Servicio principal con Circuit Breaker |
| Bill Management Service | 9050 | Servicio consumidor |
| Prometheus | 9090 | Monitoreo y métricas |
| Grafana | 3000 | Dashboard de monitoreo |
| Toxiproxy | 8474 | Simulación de fallos |

## 🔍 Escenarios de Prueba

### 1. Prueba de Circuit Breaker Cerrado (Flujo Normal)

```bash
# Realizar petición al servicio
curl -X GET "http://localhost:5000/api/electric-bill/REF-12345"
```

### 2. Simular Fallos con Toxiproxy

1. **Activar toxicidad (lentitud)**:
   ```bash
   curl -X POST "http://localhost:8474/proxies/electric-provider/toxics" \
   -H "Content-Type: application/json" \
   -d '{
     "name": "latency",
     "type": "latency",
     "stream": "downstream",
     "toxicity": 1.0,
     "attributes": {
       "latency": 5000,
       "jitter": 0
     }
   }'
   ```

2. **Verificar estado del Circuit Breaker**:
   ```bash
   curl -X GET "http://localhost:5000/actuator/health"
   ```

3. **Ver métricas del Circuit Breaker**:
   ```bash
   curl -X GET "http://localhost:5000/actuator/metrics/resilience4j.circuitbreaker.calls"
   ```

### 3. Visualización en Grafana

1. Acceder a Grafana: `http://localhost:3000`
   - Usuario: `admin`
   - Contraseña: `admin`

2. Importar el dashboard de Resilience4j:
   - ID del dashboard: `15314`
   - Seleccionar el datasource: `Prometheus`

## 🛠️ Desarrollo

### Estructura del Proyecto

```
circuit-breaker-system/
├── internal-architecture/
│   ├── electric-bill-service/    # Servicio con Circuit Breaker
│   └── bill-management-service/   # Servicio consumidor
├── monitoring/
│   ├── prometheus/               # Configuración de Prometheus
│   └── grafana/                  # Dashboards de Grafana
├── docker-compose.yml            # Configuración de Docker
└── .env                         # Variables de entorno
```

### Variables de Entorno Clave

| Variable | Valor por Defecto | Descripción |
|----------|------------------|-------------|
| `PROVIDER_URL` | http://toxiproxy:6000 | URL del proveedor de facturas |
| `ELECTRIC_BILL_URL` | http://electric-bill-service:5000 | URL del servicio de facturas |
| `CONNECT_TIMEOUT_MS` | 3500 | Tiempo de conexión en ms |
| `READ_TIMEOUT_MS` | 3500 | Tiempo de lectura en ms |

## 📊 Monitoreo

El sistema incluye monitoreo con:

- **Prometheus**: Recolección de métricas
- **Grafana**: Visualización de métricas
- **Actuator**: Endpoints de salud y métricas

Acceso:
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

## 📝 Notas Adicionales

- El Circuit Breaker se configura en `application.yml` dentro de cada servicio
- Los parámetros como umbrales de fallos y tiempos de espera son ajustables
- Se recomienda monitorear las métricas en Grafana durante las pruebas

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.