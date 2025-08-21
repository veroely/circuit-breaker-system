import http from "k6/http";
import { check } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
  stages: [
    { duration: '30s', target: 200 },// Una rampa de subida agresiva para saturar los hilos rápidamente
    { duration: '1m', target: 200 }, // Mantenemos la carga para observar el colapso total
    { duration: '15s', target: 0 },// Rampa de bajada
  ]
};

export default function () {
  const url = 'http://localhost:9050/api/payment/getBill';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  check(res, {
    'status is 200': (r) => r.status === 200
  });
}