# 🚚 TransportProblem — Java AI Pathfinding for Parcel Delivery

**TransportProblem** is a Java console-based simulation where a truck must pick up and deliver parcels on a grid. The environment is read from a text file and the system uses search algorithms like **UCS** and **A\*** to determine optimal delivery paths.

---

## 🎯 Project Goal

Simulate intelligent truck movement in a city grid, picking up and delivering packages using cost-efficient paths, and compare different AI search strategies.

---

## 🧠 Algorithms Used

- **Uniform Cost Search (UCS)** — cost-based breadth-first search
- **A\*** — heuristic-based search with multiple heuristics:
  - `Heuristic 1`: Manhattan distance to start
  - `Heuristic 2`: Weighted distance based on parcel state
  - `Heuristic 3`: Max of all parcel heuristics

---

## 🗺️ Map Representation

- The map is loaded from a `.txt` file, where:
  - `.` → Walkable path
  - `#` → Building (not walkable)
  - `T` → Starting point for the truck
  - `P1`, `P2`, ... → Pickup locations for parcel ID 1, 2, ...
  - `D1`, `D2`, ... → Delivery destinations for parcel ID 1, 2, ...

---

## 📦 How It Works

1. The program loads the map from a file (e.g., `file.txt`)
2. The truck navigates from `T` to pickup `P` locations, then to `D`
3. It evaluates all possible next moves
4. The path is selected based on the chosen algorithm (UCS or A*)


## 🛻 Examples

> grid 1:

![Screenshot_2025-07-09-19-27-15-830_com google android apps docs](https://github.com/user-attachments/assets/338a7e0b-ec56-4b81-a58e-acd95206590f)

> grid 2:

![Screenshot_2025-07-09-19-27-41-032_com google android apps docs](https://github.com/user-attachments/assets/75b9b4b9-535a-48d5-932f-3a1cfd7078b3)

> grid 3:

![Screenshot_2025-07-09-19-28-04-847_com google android apps docs](https://github.com/user-attachments/assets/1e685fce-e811-4919-ae9d-0dcda9a2cf94)
