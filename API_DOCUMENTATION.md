## Khanan Data API - Complete Endpoint Documentation

### Base URL
```
/api/khanan-data
```

### Pagination Format
All paginated responses follow this format:
```json
{
  "content": [...],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 100,
  "totalPages": 10,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

### Query Parameters for Pagination
- `page` - Page number (0-indexed, default: 0)
- `size` - Records per page (default: 10)
- `sort` - Sort by field (default: id)
- `direction` - ASC or DESC (default: ASC)

**Example**: `/filter/district?district=Bangalore&page=0&size=10&sort=date&direction=DESC`

---

## CREATE ENDPOINTS

### 1. Save Single Record
- **Endpoint**: `POST /save`
- **Request Body**:
```json
{
  "district": "Bangalore",
  "consignerName": "John Doe",
  "date": "2025-01-20",
  "sourceType": "Type A",
  "consigneeName": "Jane Doe",
  "challanNo": "CHAL123",
  "mineralName": "Iron Ore",
  "mineralCategory": "Category A",
  "vehicleRegNo": "KA01AB1234",
  "destination": "Delhi",
  "transportedDate": "2025-01-21",
  "quantity": "100",
  "unit": "kg",
  "checkStatus": "Pending"
}
```
- **Response**: Single saved record with generated ID

### 2. Save Multiple Records
- **Endpoint**: `POST /save-all`
- **Request Body**: Array of KhananData objects
- **Response**: List of saved records

---

## READ ENDPOINTS

### 3. Get All Records
- **Endpoint**: `GET /all`
- **Response**: All records in the database

### 4. Get All Records (Paginated)
- **Endpoint**: `GET /paginated/all`
- **Query Parameters**: `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

---

## SINGLE FIELD FILTER ENDPOINTS

### 5. Filter by District
- **Endpoint**: `GET /filter/district?district=Bangalore`
- **Parameters**: `district` (String)

### 6. Filter by District (Paginated)
- **Endpoint**: `GET /paginated/filter/district?district=Bangalore&page=0&size=10`
- **Parameters**: `district`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 7. Filter by Consigner Name
- **Endpoint**: `GET /filter/consigner-name?consignerName=John Doe`
- **Parameters**: `consignerName` (String)

### 8. Filter by Consigner Name (Paginated)
- **Endpoint**: `GET /paginated/filter/consigner-name?consignerName=John Doe&page=0&size=10`
- **Parameters**: `consignerName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 9. Filter by Mineral Name
- **Endpoint**: `GET /filter/mineral-name?mineralName=Iron Ore`
- **Parameters**: `mineralName` (String)

### 10. Filter by Mineral Name (Paginated)
- **Endpoint**: `GET /paginated/filter/mineral-name?mineralName=Iron Ore&page=0&size=10`
- **Parameters**: `mineralName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 11. Filter by Mineral Category
- **Endpoint**: `GET /filter/mineral-category?mineralCategory=Category A`
- **Parameters**: `mineralCategory` (String)

### 12. Filter by Mineral Category (Paginated)
- **Endpoint**: `GET /paginated/filter/mineral-category?mineralCategory=Category A&page=0&size=10`
- **Parameters**: `mineralCategory`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 13. Filter by Check Status
- **Endpoint**: `GET /filter/check-status?checkStatus=Pending`
- **Parameters**: `checkStatus` (String)

### 14. Filter by Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/check-status?checkStatus=Pending&page=0&size=10`
- **Parameters**: `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 15. Filter by Source Type
- **Endpoint**: `GET /filter/source-type?sourceType=Type A`
- **Parameters**: `sourceType` (String)

### 16. Filter by Source Type (Paginated)
- **Endpoint**: `GET /paginated/filter/source-type?sourceType=Type A&page=0&size=10`
- **Parameters**: `sourceType`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 17. Filter by Consignee Name
- **Endpoint**: `GET /filter/consignee-name?consigneeName=Jane Doe`
- **Parameters**: `consigneeName` (String)

### 18. Filter by Consignee Name (Paginated)
- **Endpoint**: `GET /paginated/filter/consignee-name?consigneeName=Jane Doe&page=0&size=10`
- **Parameters**: `consigneeName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 19. Filter by Challan No
- **Endpoint**: `GET /filter/challan-no?challanNo=CHAL123`
- **Parameters**: `challanNo` (String)

### 20. Filter by Challan No (Paginated)
- **Endpoint**: `GET /paginated/filter/challan-no?challanNo=CHAL123&page=0&size=10`
- **Parameters**: `challanNo`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 21. Filter by Destination
- **Endpoint**: `GET /filter/destination?destination=Delhi`
- **Parameters**: `destination` (String)

### 22. Filter by Destination (Paginated)
- **Endpoint**: `GET /paginated/filter/destination?destination=Delhi&page=0&size=10`
- **Parameters**: `destination`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 23. Filter by Quantity
- **Endpoint**: `GET /filter/quantity?quantity=100`
- **Parameters**: `quantity` (String)

### 24. Filter by Quantity (Paginated)
- **Endpoint**: `GET /paginated/filter/quantity?quantity=100&page=0&size=10`
- **Parameters**: `quantity`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 25. Filter by Unit
- **Endpoint**: `GET /filter/unit?unit=kg`
- **Parameters**: `unit` (String)

### 26. Filter by Unit (Paginated)
- **Endpoint**: `GET /paginated/filter/unit?unit=kg&page=0&size=10`
- **Parameters**: `unit`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 27. Filter by Date
- **Endpoint**: `GET /filter/date?date=2025-01-20`
- **Parameters**: `date` (String)

### 28. Filter by Date (Paginated)
- **Endpoint**: `GET /paginated/filter/date?date=2025-01-20&page=0&size=10`
- **Parameters**: `date`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 29. Filter by Transported Date
- **Endpoint**: `GET /filter/transported-date?transportedDate=2025-01-21`
- **Parameters**: `transportedDate` (String)

### 30. Filter by Transported Date (Paginated)
- **Endpoint**: `GET /paginated/filter/transported-date?transportedDate=2025-01-21&page=0&size=10`
- **Parameters**: `transportedDate`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 31. Filter by Vehicle Registration No
- **Endpoint**: `GET /filter/vehicle-reg-no?vehicleRegNo=KA01AB1234`
- **Parameters**: `vehicleRegNo` (String)

### 32. Filter by Vehicle Registration No (Paginated)
- **Endpoint**: `GET /paginated/filter/vehicle-reg-no?vehicleRegNo=KA01AB1234&page=0&size=10`
- **Parameters**: `vehicleRegNo`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

---

## CREATE ENDPOINTS

### 1. Save Single Record
- **Endpoint**: `POST /save`
- **Endpoint**: `GET /filter/district-consigner?district=Bangalore&consignerName=John Doe`
- **Parameters**: `district`, `consignerName`

### 34. Filter by District & Consigner Name (Paginated)
- **Endpoint**: `GET /paginated/filter/district-consigner?district=Bangalore&consignerName=John Doe&page=0&size=10`
- **Parameters**: `district`, `consignerName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 35. Filter by District & Mineral Name
- **Endpoint**: `GET /filter/district-mineral?district=Bangalore&mineralName=Iron Ore`
- **Parameters**: `district`, `mineralName`

### 36. Filter by District & Mineral Name (Paginated)
- **Endpoint**: `GET /paginated/filter/district-mineral?district=Bangalore&mineralName=Iron Ore&page=0&size=10`
- **Parameters**: `district`, `mineralName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 37. Filter by District & Check Status
- **Endpoint**: `GET /filter/district-status?district=Bangalore&checkStatus=Pending`
- **Parameters**: `district`, `checkStatus`

### 38. Filter by District & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/district-status?district=Bangalore&checkStatus=Pending&page=0&size=10`
- **Parameters**: `district`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 39. Filter by Mineral Name & Check Status
- **Endpoint**: `GET /filter/mineral-status?mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `mineralName`, `checkStatus`

### 40. Filter by Mineral Name & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/mineral-status?mineralName=Iron Ore&checkStatus=Pending&page=0&size=10`
- **Parameters**: `mineralName`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 41. Filter by Consigner Name & Mineral Name
- **Endpoint**: `GET /filter/consigner-mineral?consignerName=John Doe&mineralName=Iron Ore`
- **Parameters**: `consignerName`, `mineralName`

### 42. Filter by Consigner Name & Mineral Name (Paginated)
- **Endpoint**: `GET /paginated/filter/consigner-mineral?consignerName=John Doe&mineralName=Iron Ore&page=0&size=10`
- **Parameters**: `consignerName`, `mineralName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 43. Filter by Vehicle Reg No & Check Status
- **Endpoint**: `GET /filter/vehicle-status?vehicleRegNo=KA01AB1234&checkStatus=Pending`
- **Parameters**: `vehicleRegNo`, `checkStatus`

### 44. Filter by Vehicle Reg No & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/vehicle-status?vehicleRegNo=KA01AB1234&checkStatus=Pending&page=0&size=10`
- **Parameters**: `vehicleRegNo`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 45. Filter by Vehicle Reg No & Destination
- **Endpoint**: `GET /filter/vehicle-destination?vehicleRegNo=KA01AB1234&destination=Delhi`
- **Parameters**: `vehicleRegNo`, `destination`

### 46. Filter by Vehicle Reg No & Destination (Paginated)
- **Endpoint**: `GET /paginated/filter/vehicle-destination?vehicleRegNo=KA01AB1234&destination=Delhi&page=0&size=10`
- **Parameters**: `vehicleRegNo`, `destination`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

---

## MULTIPLE FIELD FILTERS (3 FIELDS)

### 47. Filter by District, Mineral Name & Check Status
- **Endpoint**: `GET /filter/district-mineral-status?district=Bangalore&mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `district`, `mineralName`, `checkStatus`

### 48. Filter by District, Mineral Name & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/district-mineral-status?district=Bangalore&mineralName=Iron Ore&checkStatus=Pending&page=0&size=10`
- **Parameters**: `district`, `mineralName`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 49. Filter by District, Consigner Name & Mineral Name
- **Endpoint**: `GET /filter/district-consigner-mineral?district=Bangalore&consignerName=John Doe&mineralName=Iron Ore`
- **Parameters**: `district`, `consignerName`, `mineralName`

### 50. Filter by District, Consigner Name & Mineral Name (Paginated)
- **Endpoint**: `GET /paginated/filter/district-consigner-mineral?district=Bangalore&consignerName=John Doe&mineralName=Iron Ore&page=0&size=10`
- **Parameters**: `district`, `consignerName`, `mineralName`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 51. Filter by Consigner Name, Mineral Name & Check Status
- **Endpoint**: `GET /filter/consigner-mineral-status?consignerName=John Doe&mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `consignerName`, `mineralName`, `checkStatus`

### 52. Filter by Consigner Name, Mineral Name & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/consigner-mineral-status?consignerName=John Doe&mineralName=Iron Ore&checkStatus=Pending&page=0&size=10`
- **Parameters**: `consignerName`, `mineralName`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

### 53. Filter by Vehicle Reg No, Destination & Check Status
- **Endpoint**: `GET /filter/vehicle-destination-status?vehicleRegNo=KA01AB1234&destination=Delhi&checkStatus=Pending`
- **Parameters**: `vehicleRegNo`, `destination`, `checkStatus`

### 54. Filter by Vehicle Reg No, Destination & Check Status (Paginated)
- **Endpoint**: `GET /paginated/filter/vehicle-destination-status?vehicleRegNo=KA01AB1234&destination=Delhi&checkStatus=Pending&page=0&size=10`
- **Parameters**: `vehicleRegNo`, `destination`, `checkStatus`, `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>

---

## ADVANCED FILTER ENDPOINTS

### 55. Filter by Multiple Fields (Dynamic)
- **Endpoint**: `POST /filter/multiple`
- **Description**: Apply filters on any combination of fields. Only provide non-null values for fields you want to filter by.
- **Request Body**:
```json
{
  "district": "Bangalore",
  "mineralName": "Iron Ore",
  "checkStatus": "Pending",
  "vehicleRegNo": "KA01AB1234"
}
```
- **Response**: Filtered records matching ALL provided criteria
- **Note**: Can filter on ANY or ALL of these fields:
  - id, district, consignerName, date, sourceType, consigneeName, challanNo, mineralName, mineralCategory, vehicleRegNo, destination, transportedDate, quantity, unit, checkStatus

### 56. Filter by Multiple Fields (Dynamic with Pagination)
- **Endpoint**: `POST /paginated/filter/multiple`
- **Description**: Apply filters on any combination of fields with pagination support. Only provide non-null values for fields you want to filter by.
- **Request Body**:
```json
{
  "district": "Bangalore",
  "mineralName": "Iron Ore",
  "checkStatus": "Pending"
}
```
- **Query Parameters**: `page`, `size`, `sort`, `direction`
- **Response**: PaginatedResponse<KhananData>
- **Note**: Can filter on ANY or ALL of these fields

---

## UPDATE ENDPOINTS (RETURNS UPDATED DATA)

### 57. Update Record by ID
- **Endpoint**: `PUT /update/{id}`
- **Path Parameter**: `id` (String)
- **Request Body**: Updated KhananData object
- **Response**: Updated record

### 58. Update Record with Filter Response
- **Endpoint**: `PUT /update/{id}/with-filter`
- **Path Parameter**: `id` (String)
- **Request Body**: Updated KhananData object
- **Response**: Updated record

### 59. Update and Filter by Multiple Criteria
- **Endpoint**: `PUT /filter-update-multiple`
- **Request Body**: FilterCriteria object
- **Response**: Filtered records matching the criteria

---

## DELETE ENDPOINTS

### 60. Delete Record by ID
- **Endpoint**: `DELETE /delete/{id}`
- **Path Parameter**: `id` (String)
- **Response**: Success message "Record deleted successfully"

---

## CHECK ENDPOINTS

### 61. Check if Vehicle Exists
- **Endpoint**: `GET /check/vehicle-exists/{vehicleRegNo}`
- **Path Parameter**: `vehicleRegNo` (String)
- **Response**: `true` or `false`

---

## Usage Examples

### Example 1: Filter by Single Field
```bash
curl -X GET "http://localhost:8080/api/khanan-data/filter/district?district=Bangalore"
```

### Example 2: Filter by Single Field (Paginated)
```bash
curl -X GET "http://localhost:8080/api/khanan-data/paginated/filter/district?district=Bangalore&page=0&size=10&sort=date&direction=DESC"
```

### Example 3: Filter by Multiple Fields
```bash
curl -X GET "http://localhost:8080/api/khanan-data/filter/district-mineral?district=Bangalore&mineralName=Iron Ore"
```

### Example 4: Filter by Multiple Fields (Paginated)
```bash
curl -X GET "http://localhost:8080/api/khanan-data/paginated/filter/district-mineral?district=Bangalore&mineralName=Iron Ore&page=0&size=15"
```

### Example 5: Advanced Filter (Multiple Criteria)
```bash
curl -X POST "http://localhost:8080/api/khanan-data/filter/multiple" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Bangalore",
    "mineralName": "Iron Ore",
    "checkStatus": "Pending"
  }'
```

### Example 6: Advanced Filter (Multiple Criteria with Pagination)
```bash
curl -X POST "http://localhost:8080/api/khanan-data/paginated/filter/multiple?page=0&size=20" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Bangalore",
    "mineralName": "Iron Ore",
    "checkStatus": "Pending"
  }'
```

### Example 7: Save a Record
```bash
curl -X POST "http://localhost:8080/api/khanan-data/save" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Bangalore",
    "consignerName": "John Doe",
    "mineralName": "Iron Ore",
    "checkStatus": "Pending"
  }'
```

### Example 8: Update a Record
```bash
curl -X PUT "http://localhost:8080/api/khanan-data/update/{id}" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Updated District",
    "consignerName": "Updated Name"
  }'
```

### Example 9: Get All Records (Paginated)
```bash
curl -X GET "http://localhost:8080/api/khanan-data/paginated/all?page=0&size=10"
```

---

## Response Format (Pagination)

### Successful Response
```json
{
  "content": [
    {
      "id": "507f1f77bcf86cd799439011",
      "district": "Bangalore",
      "consignerName": "John Doe",
      "date": "2025-01-20",
      "sourceType": "Type A",
      "consigneeName": "Jane Doe",
      "challanNo": "CHAL123",
      "mineralName": "Iron Ore",
      "mineralCategory": "Category A",
      "vehicleRegNo": "KA01AB1234",
      "destination": "Delhi",
      "transportedDate": "2025-01-21",
      "quantity": "100",
      "unit": "kg",
      "checkStatus": "Pending"
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 45,
  "totalPages": 5,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

---

## Pagination Query Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Page number (0-indexed) |
| `size` | Integer | 10 | Records per page |
| `sort` | String | id | Field to sort by |
| `direction` | String | ASC | Sort direction (ASC/DESC) |

---

## Filter Combinations Summary

✅ **Available Filters:**
- ✨ 14 single field filters (each with paginated version)
- ✨ 7 two-field combined filters (each with paginated version)
- ✨ 4 three-field combined filters (each with paginated version)
- ✨ Dynamic multi-field filter (with paginated version)
- ✨ CRUD operations with filter support
- ✨ Comprehensive pagination support

✅ **Response Format:**
- Non-paginated endpoints return: `List<KhananData>`
- Paginated endpoints return: `PaginatedResponse<KhananData>`
- Save endpoints return: `KhananData` or `List<KhananData>`
- Update endpoints return: Updated `KhananData` or `List<KhananData>`
- Status codes: 200 (OK), 201 (CREATED)

---

## Feature Summary

✨ **Complete Features:**
1. ✅ Create single and multiple records
2. ✅ Read all records (paginated & non-paginated)
3. ✅ Filter by any single field (paginated & non-paginated)
4. ✅ Filter by any combination of fields - 2-3 predefined + unlimited dynamic (paginated & non-paginated)
5. ✅ Update records with change tracking
6. ✅ Delete records
7. ✅ Check record existence
8. ✅ Dynamic multi-criteria filtering (paginated & non-paginated)
9. ✅ All endpoints return filtered/updated data
10. ✅ **COMPLETE PAGINATION SUPPORT ON ALL FILTER ENDPOINTS**

---

## CREATE ENDPOINTS

### 1. Save Single Record
- **Endpoint**: `POST /save`
- **Request Body**:
```json
{
  "district": "Bangalore",
  "consignerName": "John Doe",
  "date": "2025-01-20",
  "sourceType": "Type A",
  "consigneeName": "Jane Doe",
  "challanNo": "CHAL123",
  "mineralName": "Iron Ore",
  "mineralCategory": "Category A",
  "vehicleRegNo": "KA01AB1234",
  "destination": "Delhi",
  "transportedDate": "2025-01-21",
  "quantity": "100",
  "unit": "kg",
  "checkStatus": "Pending"
}
```
- **Response**: Single saved record with generated ID

### 2. Save Multiple Records
- **Endpoint**: `POST /save-all`
- **Request Body**: Array of KhananData objects
- **Response**: List of saved records

---

## READ ENDPOINTS

### 3. Get All Records
- **Endpoint**: `GET /all`
- **Response**: All records in the database

---

## SINGLE FIELD FILTER ENDPOINTS

### 4. Filter by District
- **Endpoint**: `GET /filter/district?district=Bangalore`
- **Parameters**: `district` (String)

### 5. Filter by Consigner Name
- **Endpoint**: `GET /filter/consigner-name?consignerName=John Doe`
- **Parameters**: `consignerName` (String)

### 6. Filter by Mineral Name
- **Endpoint**: `GET /filter/mineral-name?mineralName=Iron Ore`
- **Parameters**: `mineralName` (String)

### 7. Filter by Mineral Category
- **Endpoint**: `GET /filter/mineral-category?mineralCategory=Category A`
- **Parameters**: `mineralCategory` (String)

### 8. Filter by Check Status
- **Endpoint**: `GET /filter/check-status?checkStatus=Pending`
- **Parameters**: `checkStatus` (String)

### 9. Filter by Source Type
- **Endpoint**: `GET /filter/source-type?sourceType=Type A`
- **Parameters**: `sourceType` (String)

### 10. Filter by Consignee Name
- **Endpoint**: `GET /filter/consignee-name?consigneeName=Jane Doe`
- **Parameters**: `consigneeName` (String)

### 11. Filter by Challan No
- **Endpoint**: `GET /filter/challan-no?challanNo=CHAL123`
- **Parameters**: `challanNo` (String)

### 12. Filter by Destination
- **Endpoint**: `GET /filter/destination?destination=Delhi`
- **Parameters**: `destination` (String)

### 13. Filter by Quantity
- **Endpoint**: `GET /filter/quantity?quantity=100`
- **Parameters**: `quantity` (String)

### 14. Filter by Unit
- **Endpoint**: `GET /filter/unit?unit=kg`
- **Parameters**: `unit` (String)

### 15. Filter by Date
- **Endpoint**: `GET /filter/date?date=2025-01-20`
- **Parameters**: `date` (String)

### 16. Filter by Transported Date
- **Endpoint**: `GET /filter/transported-date?transportedDate=2025-01-21`
- **Parameters**: `transportedDate` (String)

### 17. Filter by Vehicle Registration No
- **Endpoint**: `GET /filter/vehicle-reg-no?vehicleRegNo=KA01AB1234`
- **Parameters**: `vehicleRegNo` (String)

---

## MULTIPLE FIELD FILTERS (2 FIELDS)

### 18. Filter by District & Consigner Name
- **Endpoint**: `GET /filter/district-consigner?district=Bangalore&consignerName=John Doe`
- **Parameters**: `district`, `consignerName`

### 19. Filter by District & Mineral Name
- **Endpoint**: `GET /filter/district-mineral?district=Bangalore&mineralName=Iron Ore`
- **Parameters**: `district`, `mineralName`

### 20. Filter by District & Check Status
- **Endpoint**: `GET /filter/district-status?district=Bangalore&checkStatus=Pending`
- **Parameters**: `district`, `checkStatus`

### 21. Filter by Mineral Name & Check Status
- **Endpoint**: `GET /filter/mineral-status?mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `mineralName`, `checkStatus`

### 22. Filter by Consigner Name & Mineral Name
- **Endpoint**: `GET /filter/consigner-mineral?consignerName=John Doe&mineralName=Iron Ore`
- **Parameters**: `consignerName`, `mineralName`

### 23. Filter by Vehicle Reg No & Check Status
- **Endpoint**: `GET /filter/vehicle-status?vehicleRegNo=KA01AB1234&checkStatus=Pending`
- **Parameters**: `vehicleRegNo`, `checkStatus`

### 24. Filter by Vehicle Reg No & Destination
- **Endpoint**: `GET /filter/vehicle-destination?vehicleRegNo=KA01AB1234&destination=Delhi`
- **Parameters**: `vehicleRegNo`, `destination`

---

## MULTIPLE FIELD FILTERS (3 FIELDS)

### 25. Filter by District, Mineral Name & Check Status
- **Endpoint**: `GET /filter/district-mineral-status?district=Bangalore&mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `district`, `mineralName`, `checkStatus`

### 26. Filter by District, Consigner Name & Mineral Name
- **Endpoint**: `GET /filter/district-consigner-mineral?district=Bangalore&consignerName=John Doe&mineralName=Iron Ore`
- **Parameters**: `district`, `consignerName`, `mineralName`

### 27. Filter by Consigner Name, Mineral Name & Check Status
- **Endpoint**: `GET /filter/consigner-mineral-status?consignerName=John Doe&mineralName=Iron Ore&checkStatus=Pending`
- **Parameters**: `consignerName`, `mineralName`, `checkStatus`

### 28. Filter by Vehicle Reg No, Destination & Check Status
- **Endpoint**: `GET /filter/vehicle-destination-status?vehicleRegNo=KA01AB1234&destination=Delhi&checkStatus=Pending`
- **Parameters**: `vehicleRegNo`, `destination`, `checkStatus`

---

## ADVANCED FILTER ENDPOINT (MULTIPLE CRITERIA)

### 29. Filter by Multiple Fields (Dynamic)
- **Endpoint**: `POST /filter/multiple`
- **Description**: Apply filters on any combination of fields. Only provide non-null values for fields you want to filter by.
- **Request Body**:
```json
{
  "district": "Bangalore",
  "mineralName": "Iron Ore",
  "checkStatus": "Pending",
  "vehicleRegNo": "KA01AB1234"
}
```
- **Response**: Filtered records matching ALL provided criteria
- **Note**: Can filter on ANY or ALL of these fields:
  - id, district, consignerName, date, sourceType, consigneeName, challanNo, mineralName, mineralCategory, vehicleRegNo, destination, transportedDate, quantity, unit, checkStatus

---

## UPDATE ENDPOINTS (RETURNS UPDATED DATA)

### 30. Update Record by ID
- **Endpoint**: `PUT /update/{id}`
- **Path Parameter**: `id` (String)
- **Request Body**: Updated KhananData object
- **Response**: Updated record

### 31. Update Record with Filter Response
- **Endpoint**: `PUT /update/{id}/with-filter`
- **Path Parameter**: `id` (String)
- **Request Body**: Updated KhananData object
- **Response**: Updated record

### 32. Update and Filter by Multiple Criteria
- **Endpoint**: `PUT /filter-update-multiple`
- **Request Body**: FilterCriteria object
- **Response**: Filtered records matching the criteria

---

## DELETE ENDPOINTS

### 33. Delete Record by ID
- **Endpoint**: `DELETE /delete/{id}`
- **Path Parameter**: `id` (String)
- **Response**: Success message "Record deleted successfully"

---

## CHECK ENDPOINTS

### 34. Check if Vehicle Exists
- **Endpoint**: `GET /check/vehicle-exists/{vehicleRegNo}`
- **Path Parameter**: `vehicleRegNo` (String)
- **Response**: `true` or `false`

---

## Usage Examples

### Example 1: Filter by Single Field
```bash
curl -X GET "http://localhost:8080/api/khanan-data/filter/district?district=Bangalore"
```

### Example 2: Filter by Multiple Fields
```bash
curl -X GET "http://localhost:8080/api/khanan-data/filter/district-mineral?district=Bangalore&mineralName=Iron Ore"
```

### Example 3: Advanced Filter (Multiple Criteria)
```bash
curl -X POST "http://localhost:8080/api/khanan-data/filter/multiple" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Bangalore",
    "mineralName": "Iron Ore",
    "checkStatus": "Pending"
  }'
```

### Example 4: Save a Record
```bash
curl -X POST "http://localhost:8080/api/khanan-data/save" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Bangalore",
    "consignerName": "John Doe",
    "mineralName": "Iron Ore",
    "checkStatus": "Pending"
  }'
```

### Example 5: Update a Record
```bash
curl -X PUT "http://localhost:8080/api/khanan-data/update/{id}" \
  -H "Content-Type: application/json" \
  -d '{
    "district": "Updated District",
    "consignerName": "Updated Name"
  }'
```

---

## Filter Combinations Summary

✅ **Available Filters:**
- Single field filters on 13+ fields
- Two-field combined filters (7 combinations)
- Three-field combined filters (4 combinations)
- Dynamic multi-field filter (supports all fields)
- CRUD operations with filter support

✅ **Response Format:**
- All filter endpoints return: `List<KhananData>`
- All save endpoints return: `KhananData` or `List<KhananData>`
- All update endpoints return: Updated `KhananData` or `List<KhananData>`
- Status codes: 200 (OK), 201 (CREATED)

---

## Feature Summary

✨ **Complete Features:**
1. ✅ Create single and multiple records
2. ✅ Read all records
3. ✅ Filter by any single field
4. ✅ Filter by any combination of fields (2-3 predefined + unlimited dynamic)
5. ✅ Update records with change tracking
6. ✅ Delete records
7. ✅ Check record existence
8. ✅ Dynamic multi-criteria filtering
9. ✅ All endpoints return filtered/updated data

---
