export function buildHeaders() {
    return {
        'x-cm-client-request-id': crypto.randomUUID(),
        'x-cm-client-user-agent': 'Mozilla/5.0',
        'Content-Type': 'application/json'
    };
}

export function buildPayload(vu, iter) {
    return JSON.stringify(
        {
            "idClient": `CLI${vu}`,
            "idService": "ELECTRICIDAD01",
            "referenceNumber": `${vu}${iter}${Date.now()}`
        }
    );
}