import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  stages: [
    { duration: '30s', target: 20 }, // Aumenta gradualmente a 20 usuarios virtuales en 30 segundos
    { duration: '1m', target: 20 },  // Mantiene 20 usuarios virtuales durante 1 minuto
    { duration: '10s', target: 0 },   // Disminuye a 0 usuarios
  ],
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

    check(res, { 'status is 200': (r) => r.status === 200 });
    sleep(1);
}