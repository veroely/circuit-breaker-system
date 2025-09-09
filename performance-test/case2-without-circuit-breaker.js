import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
  stages: [
    { duration: '2m', target: 50 },   // Fase 1: Normal
    { duration: '3m', target: 100 },  // Fase 2: Presión moderada
    { duration: '5m', target: 200 },  // Fase 3: Fallos por timeout
    { duration: '2m', target: 300 },  // Fase 4: Descenso
    { duration: '3m', target: 200 },  // Fase 5: Recuperación
    { duration: '2m', target: 0 }     // Fase 6: Normalización
  ]

  //   stages: [
  //   { duration: '2m', target: 100 },// Warm-up más gradual
  //   { duration: '10m', target: 500 }, // Carga media sostenida media
  //   { duration: '15m', target: 1000 },// Alta carga sostenida
  //   { duration: '5m', target: 1500 },// Pico de estrés
  //   { duration: '10m', target: 500 },// Recuperación parcial
  //   { duration: '5m', target: 0 }// Recuperación completa
  // ]
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  check(res, {
    'status is 200': (r) => r.status === 200
  });

  sleep(1);
}