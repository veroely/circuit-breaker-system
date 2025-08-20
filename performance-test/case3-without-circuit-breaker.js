import http from "k6/http";
import { check } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
  stages: [
    // Una rampa de subida agresiva para saturar los hilos rápidamente
    { duration: '30s', target: 200 },

    // Mantenemos la carga para observar el colapso total
    { duration: '1m', target: 200 },

    // Rampa de bajada
    { duration: '15s', target: 0 },
  ],
  thresholds: {
    // Esperamos que falle, así que un umbral bajo de éxito es aceptable
    'http_reqs{status:200}': ['count>0'],
    'http_req_failed': ['rate<0.95'], // Permitimos hasta un 95% de fallos
  }
};

export default function () {
  const url = 'http://localhost:9050/api/payment/getBill';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  console.log("petición");
  console.log(body);

  check(res, {
    'status was 200': (r) => r.status === 200,
    'status was not 5xx': (r) => r.status < 500,
  });
}