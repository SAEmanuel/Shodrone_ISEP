# US243 - List drones in the inventory

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](svg/us243-domain-model-Drone Model and Drone Domain Model Section.svg)

### 2.2. Other Remarks

- The `DroneModel` aggregate is designed to encapsulate all data and behavior related to a drone model.
- The 1-to-1 composition with `DroneCode` and `Plugin` ensures that these entities are created as part of the `DroneModel` creation process, but their detailed attributes (e.g., programming language for `DroneCode`).
- The relationship with the `Drone` aggregate is not directly involved in US240 but is shown for context, as `DroneModel` instances will be referenced by `Drone` instances in US241 (Add Drone to Inventory).
- The domain model adheres to DDD principles by maintaining clear aggregate boundaries and ensuring that `DroneModel` and `Drone` are managed independently.
