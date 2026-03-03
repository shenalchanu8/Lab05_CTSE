# # SE4010 — Microservices Lab (2026)
## Complete Project Guide | Item · Order · Payment · API Gateway

---

## 📁 Project Structure

```
microservices-lab/
├── docker-compose.yml
├── item-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/lab/item/
│       │   ├── ItemServiceApplication.java
│       │   └── ItemController.java
│       └── resources/application.properties
├── order-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/lab/order/
│       │   ├── OrderServiceApplication.java
│       │   └── OrderController.java
│       └── resources/application.properties
├── payment-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/lab/payment/
│       │   ├── PaymentServiceApplication.java
│       │   └── PaymentController.java
│       └── resources/application.properties
└── api-gateway/
    ├── Dockerfile
    ├── pom.xml
    └── src/main/
        ├── java/com/lab/gateway/
        │   └── ApiGatewayApplication.java
        └── resources/application.yml
```

---

## ✅ Prerequisites

Make sure the following are installed on your machine:

| Tool | Version | Download |
|------|---------|----------|
| Docker Desktop | Latest | https://www.docker.com/products/docker-desktop |
| Git | Latest | https://git-scm.com |
| Postman | Latest | https://www.postman.com/downloads |

> **Note:** You do NOT need Java or Maven installed locally. The Dockerfiles use a multi-stage build that compiles inside the container.

---

## 🚀 Step-by-Step Setup Guide

### Step 1 — Clone / Copy the Project

If uploading to GitHub first, initialize the repo:
```bash
cd microservices-lab
git init
git add .
git commit -m "Initial microservices project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/microservices-lab.git
git push -u origin main
```

### Step 2 — Start Docker Desktop

Open Docker Desktop and wait until it shows **"Docker is running"** in the system tray.

### Step 3 — Build All Services

Open a terminal in the `microservices-lab` root folder and run:

```bash
docker-compose build
```

This will download base images and compile all 4 services. **First build takes 5–10 minutes.**

### Step 4 — Start All Containers

```bash
docker-compose up
```

You will see logs from all 4 services. Wait until you see lines like:
```
item-service    | Started ItemServiceApplication in X seconds
order-service   | Started OrderServiceApplication in X seconds
payment-service | Started PaymentServiceApplication in X seconds
api-gateway     | Started ApiGatewayApplication in X seconds
```

To run in background (detached mode):
```bash
docker-compose up -d
```

### Step 5 — Verify Containers Are Running

```bash
docker ps
```

You should see 4 containers:

| Container | Port | Status |
|-----------|------|--------|
| api-gateway | 8080 | Up |
| item-service | 8081 | Up |
| order-service | 8082 | Up |
| payment-service | 8083 | Up |

### Step 6 — Test in Browser (Quick Check)

Open your browser and visit:
- http://localhost:8080/items → Should return `[{"id":1,"name":"Book"}, ...]`
- http://localhost:8080/orders → Should return `[]`
- http://localhost:8080/payments → Should return `[]`

---

## 🔧 Useful Commands

```bash
# View logs for a specific service
docker-compose logs item-service
docker-compose logs api-gateway

# Follow logs in real-time
docker-compose logs -f item-service

# Stop all containers (keeps data)
docker-compose stop

# Stop AND remove containers
docker-compose down

# Rebuild after code changes
docker-compose build --no-cache
docker-compose up
```

---

## 📡 API Endpoints Reference

All requests go through the API Gateway on **port 8080**.

### Item Service

| Method | URL | Description | Body |
|--------|-----|-------------|------|
| GET | `http://localhost:8080/items` | Get all items | — |
| POST | `http://localhost:8080/items` | Add new item | `{"name": "Headphones"}` |
| GET | `http://localhost:8080/items/1` | Get item by ID | — |

### Order Service

| Method | URL | Description | Body |
|--------|-----|-------------|------|
| GET | `http://localhost:8080/orders` | Get all orders | — |
| POST | `http://localhost:8080/orders` | Place new order | `{"item":"Laptop","quantity":2,"customerId":"C001"}` |
| GET | `http://localhost:8080/orders/1` | Get order by ID | — |

### Payment Service

| Method | URL | Description | Body |
|--------|-----|-------------|------|
| GET | `http://localhost:8080/payments` | Get all payments | — |
| POST | `http://localhost:8080/payments/process` | Process payment | `{"orderId":1,"amount":1299.99,"method":"CARD"}` |
| GET | `http://localhost:8080/payments/1` | Get payment by ID | — |

---

## 🧪 Postman Testing Guide

### Setup

1. Open Postman
2. Click **"New"** → **"Collection"**
3. Name it `Microservices Lab`

### Add Requests

For each request below:
1. Click **"Add request"** inside the collection
2. Set the method (GET/POST), paste the URL
3. For POST requests: click **Body** → **raw** → select **JSON**, paste the body
4. Click **Send**

### Request 1: GET All Items
- Method: `GET`
- URL: `http://localhost:8080/items`
- Expected: 200 OK with list of items

### Request 2: POST Add Item
- Method: `POST`
- URL: `http://localhost:8080/items`
- Body (JSON):
```json
{
    "name": "Headphones"
}
```
- Expected: 201 Created

### Request 3: GET Item by ID
- Method: `GET`
- URL: `http://localhost:8080/items/1`
- Expected: 200 OK with single item

### Request 4: GET All Orders
- Method: `GET`
- URL: `http://localhost:8080/orders`
- Expected: 200 OK (empty array initially)

### Request 5: POST Place Order
- Method: `POST`
- URL: `http://localhost:8080/orders`
- Body (JSON):
```json
{
    "item": "Laptop",
    "quantity": 2,
    "customerId": "C001"
}
```
- Expected: 201 Created with `"status": "PENDING"`

### Request 6: GET Order by ID
- Method: `GET`
- URL: `http://localhost:8080/orders/1`
- Expected: 200 OK with order details

### Request 7: GET All Payments
- Method: `GET`
- URL: `http://localhost:8080/payments`
- Expected: 200 OK

### Request 8: POST Process Payment
- Method: `POST`
- URL: `http://localhost:8080/payments/process`
- Body (JSON):
```json
{
    "orderId": 1,
    "amount": 1299.99,
    "method": "CARD"
}
```
- Expected: 201 Created with `"status": "SUCCESS"`

### Request 9: GET Payment by ID
- Method: `GET`
- URL: `http://localhost:8080/payments/1`
- Expected: 200 OK with payment details

---

## 🏗️ Architecture Overview

```
Client (Postman / Browser)
        ↓
  API Gateway :8080
  /items  →  item-service:8081
  /orders →  order-service:8082
  /payments → payment-service:8083

All services on Docker network: microservices-net
```

Each service is completely independent. They communicate only through the API Gateway.

---

## 📤 GitHub Submission

1. Create a public GitHub repository called `microservices-lab` (or similar)
2. Push all project files:
```bash
git init
git add .
git commit -m "SE4010 Microservices Lab - Complete implementation"
git remote add origin https://github.com/YOUR_USERNAME/microservices-lab.git
git push -u origin main
```
3. Take Postman screenshots showing all endpoints working
4. Add the GitHub repo link to your Word document submission

---

## ❗ Troubleshooting

**Port already in use?**
```bash
# Find and kill process using port 8080
netstat -ano | findstr :8080   # Windows
lsof -i :8080                  # Mac/Linux
```

**Container fails to start?**
```bash
docker-compose logs api-gateway
# Read the error and check application.yml
```

**Services can't find each other?**
- Make sure you're using service names (e.g. `item-service`) NOT `localhost` in application.yml
- All services must be on the same Docker network (`microservices-net`)

**Build fails?**
```bash
docker-compose build --no-cache
```
