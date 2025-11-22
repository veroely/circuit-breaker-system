import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";
import { Trend, Rate } from "k6/metrics";

export const success = new Rate('success_rate');

export const options = {
  stages: [
    { duration: '2m', target: 50 },   // Fase 1: Normal
    { duration: '3m', target: 100 },  // Fase 2: Presión moderada
    { duration: '5m', target: 200 },  // Fase 3: Fallos por timeout
    { duration: '2m', target: 300 },  // Fase 4: Descenso
    { duration: '3m', target: 200 },  // Fase 5: Recuperación
    { duration: '2m', target: 0 }     // Fase 6: Normalización
  ]
};



export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });


    // Registrar métricas
  success.add(res.status >= 200 && res.status < 400);


  // Validaciones del CB activo
  check(res, {
    "CB activo responde correctamente": (r) =>
      r.status >= 200 && r.status < 400,
  });

  //simula tiempo entre peticiones
  sleep(1);
}

//comandos para ejecutar script
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\E5_circuit_breaker_change_parameters.js