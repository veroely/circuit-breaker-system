import http from "k6/http";
import { check, sleep } from "k6";

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

  // Generación de datos dinámicos
  const uniqueClientId = `CLI${__VU}`; // Genera un ID de cliente único por cada usuario virtual. Ej: CLI1, CLI2, etc.
  const uniqueReferenceNumber = `${__VU}${__ITER}${Date.now()}`; // Genera un número de referencia único para cada petición. Ej: REF-1-0-1678886400000

  const headers = {
    'x-cm-client-request-id': '123',
    'x-cm-client-user-agent': '123',
    'Content-Type': 'application/json'
  };
  const body = {
    "idClient": uniqueClientId,
    "idService": "ELECTRICIDAD01",
    "referenceNumber": uniqueReferenceNumber
  };

  const payload = JSON.stringify(body);
  const res = http.post(url, payload, { headers });

  console.log("petición");
  console.log(body);

  check(res, {
    'status was 200': (r) => r.status === 200,
    'status was not 5xx': (r) => r.status < 500,
  });
}