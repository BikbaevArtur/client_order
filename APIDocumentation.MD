

# API Documentation

## Endpoints

### Supplying

Creat new supplying
POST 
[*****/api/supplying**](/api/supplying)

```json
{
  "supplyingCreatDTO": {
    "supplyingCompanyID":  (int id supplying_company) 
  },
  "supplyingProductDTOS": [
    {
      "productID": (int id product)
      "quantity": (int quantity)
    }
]
}

```

Find all supplying
GET
[*****/api/supplying**](/api/supplying)

return :

```json

[
  {
    "id": 0,
    "supplyingCompanyID": 0,
    "deliveryDate": "2024-07-11",
    "deliveryAmount": 0,
    "productDTOS": [
      {
        "productID": 0,
        "quantity": 0
      }
    ]
  }
]