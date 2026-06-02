# M426 Final Project

**Team:** Finn, Marvin, Lea, Gabriela, Fenia, Laura

**Title:** Incremental Shape Game

---

## Team Structure

```
├── Frontend Supervisor: Finn
│   └── Fenia, Lea
└── Backend Supervisor: Marvin
    └── Gabriela, Laura
```

---

## Project Idea

- Incremental (clicker-style) game
- Beginner friendly
- Use shapes to produce currency
- Upgrade shapes to add more vertices = more currency
- Starts in 2 dimensions
- Prestige system for more dimensions / reset
- Java
- Automated backend tests

---

## Core Mechanics

### Shape System
- You have a shape which produces currency
- The shape starts off as a single point
- There are upgrades you can purchase to improve your production

### Prestige Mechanics (Vertices)
- Reset your upgrades and currency etc. to gain prestige currency
- Spend prestige currency on a second set of upgrades which stay between resets
- There is an infinitely purchasable upgrade that increases your vertex count; vertex count acts as an immediate multiplier to your production
- Prestige currency gain is based on currency when you reset

### Game Loop
- Earn currency
- Upgrade shape when you have enough
- Watch vertices increase and production skyrocket
- Eventually prestige to start fresh with a boost
- Repeat endlessly with increasing bonuses

### Persistence
- Game saves to local JSON file
- Automatic saves on every state change
- Load on startup - seamless progression

---

## User Stories

Format: As a [role] I want [feature] because [reason].

- As a player, I want to perform prestiges because I want to increase my score.
- As a player, I want to make strategic decisions because it makes the game more interesting.
- As a player, I want to upgrade shapes because they get more vertices and produce more currency.
- As a beginner, I want a tutorial because I need to understand the game mechanics. (TEMP)
- As a player, I want to see my current currency because I want to track my progress.
- As a player, I want to see my upgrades to know my current status.

---

## Acceptance Criteria

### US1: Perform Prestige

- **Given:** Player has reached a preset threshold
- **When:** Player clicks the "Prestige" button
- **Then:**
  - Score is added to persisting statistics
  - All current resources/shapes are reset
  - Player receives a prestige bonus (e.g. +5% base currency production)
  - Progress is saved correctly

### US2: Strategic Decisions

- **Given:** Player is in an active game
- **When:** Player must choose between multiple upgrade options
- **Then:**
  - At least 2 different upgrade paths are available
  - Costs and benefits are clearly displayed
  - Decisions have long-term impact

### US3: Upgrade Shapes

- **Given:** Player has enough currency
- **When:** Player clicks "Upgrade" for a shape
- **Then:**
  - Vertices increase by 1
  - Upgrade costs scale progressively
  - Currency is deducted
  - New production rate is applied immediately
  - UI shows updated stats

### US4: Tutorial (TEMP)

- **Given:** New player starts the game
- **When:** Tutorial is activated
- **Then:**
  - All basic mechanics are explained
  - Interactive guides walk through first steps
  - Tutorial can be skipped
  - After tutorial, player understands: clicks, shapes, currency, upgrades

### US5: View Currency

- **Given:** Player is in the game
- **When:** Player looks at the HUD
- **Then:**
  - Current currency is prominently displayed
  - Production rate per second is visible
  - Value updates in real-time
  - Format is readable (e.g. 1.2M instead of 1200000)

### US6: View Upgrades

- **Given:** Player is in the game
- **When:** Player opens the "Upgrades" panel
- **Then:**
  - All available upgrades are listed
  - Status (bought/available/locked) is clear
  - Costs and benefits are shown
  - Current upgrade levels are visible
  - Descriptions are understandable
