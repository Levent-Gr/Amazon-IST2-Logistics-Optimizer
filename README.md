# Amazon-IST2-Logistics-Optimizer
Kotlin-based tool for optimizing vehicle capacity and package distribution in Amazon IST2


This project was developed to solve real-world vehicle capacity issues at Amazon IST2.

## Problem Statement
The manual calculation of vehicle capacity was time-consuming and prone to errors due to the variety of package sizes (LM1, DM4, EU2, etc.).

## Solution
A Kotlin-based algorithm that:
- Categorizes packages (Small, Medium, Large).
- Calculates total volume and estimates the number of shuttles.
- Distributes packages using a **Weighted Random Load Balancing** algorithm to ensure optimal fill rates.

## Impact
Increased operational efficiency and reduced decision-making time for ship clerks.

**Author:** Levent
**Development History:** Product of 5 months of iterative development (Started: October 2025).
