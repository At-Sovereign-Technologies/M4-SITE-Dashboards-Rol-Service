# API.md - SITE Dashboard Service Documentation

## Overview

**Service**: SITE-M4-Dashboard-Service  
**Version**: 1.0.0  
**Type**: Read-Only Electoral Dashboard API  
**Architecture**: In-Memory Mock Data (No Database)  

The SITE Dashboard Service provides role-based mock data for the Electoral System (Sistema Electoral Transparente Electrónico v2.1). Each user role receives a customized dashboard view containing only the information relevant to their electoral responsibilities.

---

## Base URL

```
http://localhost:8082/api/v1
```

---

## Authentication

**Method**: HTTP Header-based Role Specification  
**Header Name**: `X-Mock-Role`  
**Required**: Yes  
**Valid Roles**: 
- `CANDIDATO` - Candidate Dashboard
- `TESTIGO` - Electoral Witness Dashboard
- `AUDITOR` - Audit Dashboard
- `DELEGADO_CNE` - CNE Delegate Dashboard
- `FISCALIA` - Electoral Prosecution Dashboard

---

## Endpoint

### Get Dashboard Summary

**Method**: `GET`  
**Path**: `/dashboard/resumen`  
**Full URL**: `GET http://localhost:8082/api/v1/dashboard/resumen`

#### Request Headers

| Header | Value | Required | Description |
|--------|-------|----------|-------------|
| `X-Mock-Role` | String | **Yes** | The role of the user (see valid roles above) |
| `Content-Type` | `application/json` | No | Content type (implied) |

#### Request Example

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: CANDIDATO" \
  -H "Content-Type: application/json"
```

---

## Response Formats by Role

### 1. CANDIDATO (Candidate Dashboard)

**Status Code**: `200 OK`

**Response Body**:
```json
{
  "votosPropiosPorMesa": {
    "Mesa 1 - Puesto Central": 450,
    "Mesa 2 - Puesto Sur": 320,
    "Mesa 3 - Puesto Norte": 510,
    "Mesa 4 - Puesto Occidente": 280,
    "Mesa 5 - Puesto Oriente": 395
  },
  "porcentajeMesasReportadas": 85.5,
  "estadoCandidatura": "ACEPTADA - ACTIVA",
  "alertasReclamacionesActivas": false
}
```

**Field Descriptions**:
- `votosPropiosPorMesa` (Map<String, Integer>): Vote count per polling station
- `porcentajeMesasReportadas` (Double): Percentage of stations that have reported (0-100)
- `estadoCandidatura` (String): Candidacy status (e.g., "ACEPTADA - ACTIVA", "SUSPENDIDA", "RECHAZADA")
- `alertasReclamacionesActivas` (Boolean): Active complaints/challenges flag

**Use Cases**:
- Monitor vote distribution across polling stations
- Track reporting coverage percentage
- Check candidacy status and active complaints

---

### 2. TESTIGO (Electoral Witness Dashboard)

**Status Code**: `200 OK`

**Response Body**:
```json
{
  "mesasBajoCobertura": [
    {
      "mesa": "Mesa 14",
      "estado": "ESCRUTINIO FINALIZADO"
    },
    {
      "mesa": "Mesa 15",
      "estado": "ACTA GENERADA"
    },
    {
      "mesa": "Mesa 16",
      "estado": "ACTA FIRMADA"
    },
    {
      "mesa": "Mesa 17",
      "estado": "ESCRUTINIO FINALIZADO"
    }
  ],
  "conteoVotosPartido": 1450
}
```

**Field Descriptions**:
- `mesasBajoCobertura` (List<MesaEstado>): Polling stations under witness coverage
  - `mesa` (String): Polling station ID
  - `estado` (String): Current status of the station (e.g., "ESCRUTINIO FINALIZADO", "ACTA GENERADA", "ACTA FIRMADA")
- `conteoVotosPartido` (Integer): Total vote count for the party

**Use Cases**:
- Monitor assigned polling stations status
- Track party vote accumulation
- Verify escrutinio (counting) completion

---

### 3. AUDITOR (Auditor Dashboard)

**Status Code**: `200 OK`

**Response Body**:
```json
{
  "actasDigitales": [
    {
      "id": "E14-9982",
      "hashSha256": "a2b4c6d8e0f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b",
      "estado": "FIRMADA_INMUTABLE"
    },
    {
      "id": "E15-9983",
      "hashSha256": "b3c5d7e9f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6",
      "estado": "FIRMADA_INMUTABLE"
    },
    {
      "id": "E16-9984",
      "hashSha256": "c4d6e8f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7",
      "estado": "EN_REVISION"
    }
  ],
  "alertasFraudeFRA": [
    "Alerta FRA-001: Anomalía estadística en Puesto Norte",
    "Alerta FRA-002: Intento de acceso remoto no autorizado"
  ]
}
```

**Field Descriptions**:
- `actasDigitales` (List<ActaDigital>): Digital election records
  - `id` (String): Record identifier (format: E{station}-{number})
  - `hashSha256` (String): SHA-256 cryptographic hash for integrity verification
  - `estado` (String): Record state (e.g., "FIRMADA_INMUTABLE", "EN_REVISION", "RECHAZADA")
- `alertasFraudeFRA` (List<String>): Fraud alerts from the Fraud Regression Auxiliary module

**Use Cases**:
- Verify digital record integrity using hashes
- Monitor record status and revisions
- Track fraud detection alerts
- Ensure immutable records for historical audit trail

---

### 4. DELEGADO_CNE (CNE Delegate Dashboard)

**Status Code**: `200 OK`

**Response Body**:
```json
{
  "resultadosConsolidados": {
    "Candidato A": 5400000,
    "Candidato B": 4900000,
    "Candidato C": 3200000,
    "Voto en Blanco": 250000,
    "Voto Nulo": 150000
  },
  "mesasActasPendientes": 14,
  "alertasInconsistencias": [
    "GIDD-099: Discrepancia Doble Verdad en Mesa 4",
    "GIDD-100: Diferencia de actas en Puesto Este",
    "GIDD-101: Recuento de votos fuera de rango esperado"
  ]
}
```

**Field Descriptions**:
- `resultadosConsolidados` (Map<String, Integer>): National consolidated results
  - Key: Candidate name or vote category (e.g., "Candidato A", "Voto en Blanco")
  - Value: Total vote count
- `mesasActasPendientes` (Integer): Number of polling stations with pending final acts
- `alertasInconsistencias` (List<String>): Inconsistency alerts from GIDD system (Double Truth Verification Module)

**Use Cases**:
- Monitor national election results in real-time
- Track pending report submissions
- Identify data consistency issues
- Support CNE decision-making

---

### 5. FISCALIA (Electoral Prosecution Dashboard)

**Status Code**: `200 OK`

**Response Body**:
```json
{
  "mapaGeorreferenciado": [
    {
      "coordenadas": "4.6097, -74.0817",
      "severidad": "CRITICA",
      "incidente": "Intento sistemático de voto doble"
    },
    {
      "coordenadas": "4.7110, -74.0721",
      "severidad": "ALTA",
      "incidente": "Acceso no autorizado a máquina de votación"
    },
    {
      "coordenadas": "4.6726, -74.0481",
      "severidad": "MEDIA",
      "incidente": "Interferencia de señal de comunicación"
    },
    {
      "coordenadas": "4.5527, -74.0359",
      "severidad": "BAJA",
      "incidente": "Retraso en reporte de actas"
    }
  ],
  "alertasAnomaliasTrafico": 12,
  "casosFraudeInvestigacion": [
    "CASO-FRA-992: Suplantación biométrica detectada",
    "CASO-FRA-993: Manipulación de actas en tránsito",
    "CASO-FRA-994: Votante múltiple identificado"
  ]
}
```

**Field Descriptions**:
- `mapaGeorreferenciado` (List<IncidenteGeorreferenciado>): Geolocated incidents
  - `coordenadas` (String): Geographic coordinates (format: "latitude, longitude")
  - `severidad` (String): Incident severity level (CRITICA, ALTA, MEDIA, BAJA)
  - `incidente` (String): Incident description
- `alertasAnomaliasTrafico` (Integer): Count of network traffic anomalies detected
- `casosFraudeInvestigacion` (List<String>): Fraud cases under investigation

**Use Cases**:
- Visualize incident locations on electoral map
- Prioritize investigations by severity
- Track active fraud cases
- Monitor network security anomalies

---

## Error Responses

### 403 Forbidden - Missing or Invalid Role

**Status Code**: `403 Forbidden`

**Response Body**:
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "estado": 403,
  "error": "ACCESO_DENEGADO",
  "mensaje": "Encabezado X-Mock-Role requerido",
  "ruta": "/api/v1/dashboard/resumen"
}
```

**Possible Messages**:
- `"Encabezado X-Mock-Role requerido"` - Header is missing or empty
- `"Rol inválido: {ROL_PROVIDED}. Roles válidos: CANDIDATO, TESTIGO, AUDITOR, DELEGADO_CNE, FISCALIA"` - Invalid role provided

**Example Requests That Trigger This Error**:

```bash
# Missing header
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen"

# Empty header
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: "

# Invalid role
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: ROL_INVALIDO"
```

### 500 Internal Server Error

**Status Code**: `500 Internal Server Error`

**Response Body**:
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "estado": 500,
  "error": "ERROR_INTERNO",
  "mensaje": "Se produjo un error interno en el servidor. Contacte al administrador.",
  "ruta": "/api/v1/dashboard/resumen"
}
```

---

## Complete cURL Examples

### Example 1: Get Candidate Dashboard

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: CANDIDATO" \
  -H "Content-Type: application/json"
```

**Response**:
```json
{
  "votosPropiosPorMesa": {
    "Mesa 1 - Puesto Central": 450,
    "Mesa 2 - Puesto Sur": 320,
    "Mesa 3 - Puesto Norte": 510,
    "Mesa 4 - Puesto Occidente": 280,
    "Mesa 5 - Puesto Oriente": 395
  },
  "porcentajeMesasReportadas": 85.5,
  "estadoCandidatura": "ACEPTADA - ACTIVA",
  "alertasReclamacionesActivas": false
}
```

---

### Example 2: Get Witness Dashboard

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: TESTIGO" \
  -H "Content-Type: application/json"
```

---

### Example 3: Get Auditor Dashboard

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: AUDITOR" \
  -H "Content-Type: application/json"
```

---

### Example 4: Get CNE Delegate Dashboard

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: DELEGADO_CNE" \
  -H "Content-Type: application/json"
```

---

### Example 5: Get Prosecution Dashboard

```bash
curl -X GET "http://localhost:8082/api/v1/dashboard/resumen" \
  -H "X-Mock-Role: FISCALIA" \
  -H "Content-Type: application/json"
```

---

## JavaScript/Fetch Examples

### Fetch Candidate Dashboard

```javascript
async function getDashboard(role) {
  try {
    const response = await fetch('http://localhost:8082/api/v1/dashboard/resumen', {
      method: 'GET',
      headers: {
        'X-Mock-Role': role,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      const errorData = await response.json();
      console.error(`Error ${response.status}:`, errorData.mensaje);
      return null;
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Request failed:', error);
  }
}

// Usage
const candidatoDashboard = await getDashboard('CANDIDATO');
console.log('Candidato Dashboard:', candidatoDashboard);

const testivoDashboard = await getDashboard('TESTIGO');
console.log('Testigo Dashboard:', testivoDashboard);
```

---

## Python/Requests Examples

```python
import requests
import json

def get_dashboard(role):
    """Fetch dashboard data for a given role"""
    url = 'http://localhost:8082/api/v1/dashboard/resumen'
    headers = {
        'X-Mock-Role': role,
        'Content-Type': 'application/json'
    }
    
    try:
        response = requests.get(url, headers=headers)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.HTTPError as e:
        error_data = response.json()
        print(f"Error {response.status_code}: {error_data.get('mensaje')}")
        return None
    except Exception as e:
        print(f"Request failed: {e}")
        return None

# Usage
candidato_dashboard = get_dashboard('CANDIDATO')
print(json.dumps(candidato_dashboard, indent=2))

testigo_dashboard = get_dashboard('TESTIGO')
print(json.dumps(testigo_dashboard, indent=2))
```

---

## Swagger/OpenAPI Documentation

The service includes interactive API documentation via Swagger UI.

**Swagger UI URL**: `http://localhost:8082/swagger-ui.html`  
**OpenAPI Spec URL**: `http://localhost:8082/api/v1/docs`

---

## CORS Support

The service is configured to support CORS (Cross-Origin Resource Sharing) for browser-based clients.

**Allowed Origins**: All (`*`)  
**Allowed Methods**: GET, OPTIONS  
**Allowed Headers**: Content-Type, X-Mock-Role  
**Max Age**: 3600 seconds (1 hour)

---

## Performance & Caching

- **Response Time**: < 50ms (in-memory data)
- **Data Freshness**: Generated on each request (stateless)
- **Scalability**: Horizontal scaling supported (no state to replicate)
- **Browser Caching**: Clients can cache responses based on role (recommended: 5-10 minutes for role changes)

---

## Implementation Notes

- All data is generated in-memory; no database calls
- No authentication beyond role header validation
- Supports unlimited concurrent requests (stateless architecture)
- Spanish naming convention for business domain terms
- Production-ready error handling and logging

---

## Support & Contact

For issues, questions, or feature requests regarding this API, contact the SITE Dashboard Service development team.

**Service Version**: 1.0.0  
**Last Updated**: May 4, 2026  
**Status**: Production Ready
