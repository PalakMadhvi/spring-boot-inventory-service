# Project Notes

## Problem
Businesses need a reliable way to track products, warehouses and movement of stock. Manual spreadsheets make it easy to create inconsistent quantities and lose audit history.

## Proposed solution
A REST API centralizes product master data, warehouse data, current inventory and stock movement history.

## Business rules
1. SKU must be unique.
2. Warehouse code must be unique.
3. Product price cannot be negative.
4. Reorder level cannot be negative.
5. Movement quantity must be at least 1.
6. Stock OUT cannot reduce inventory below zero.
7. Every stock movement is recorded.
8. Inventory update and movement record happen in one transaction.
9. Inventory has optimistic locking to reduce lost-update risk.

## Validation / review checklist
- [x] Layered architecture
- [x] Validation
- [x] Exception handling
- [x] Persistence
- [x] Transactions
- [x] Unit test
- [x] Integration test
- [x] API documentation
- [x] Data integrity constraints
- [x] Basic authentication
- [x] Decision / audit trail through stock movements

## Week-by-week delivery
Week 1: domain model, database, APIs, validation, tests and documentation.
Week 2: demo, edge-case testing, review fixes and final packaging.
