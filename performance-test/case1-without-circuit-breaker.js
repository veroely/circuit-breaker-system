import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
  stages: [
    { duration: '30s', target: 20 }, // Aumenta gradualmente a 20 usuarios virtuales en 30 segundos
    { duration: '1m', target: 20 },  // Mantiene 20 usuarios virtuales durante 1 minuto
    { duration: '10s', target: 0 },   // Disminuye a 0 usuarios
  ],
  thresholds: {
    // Se puede configurar un umbral para que la prueba falle si el rendimiento se degrada demasiado.
    'http_req_failed': ['rate<0.01'], // Falla si más del 1% de las peticiones fallan.
    'http_req_duration': ['p(95)<200'], // Falla si el 95% de las peticiones tardan más de 200ms.
  },
};

export default function () {
  const url = 'http://localhost:9050/api/payment/getBill';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  check(res, {
    'status is 200': (r) => r.status === 200
  });
  sleep(1);
}