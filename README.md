# Scratch Game Service Documentation

1. Description
   This service is a Scratch Game that simulates a matrix-based game with different symbols and rewards. The game
   generates a matrix of symbols based on predefined configurations, evaluates winning combinations, and calculates
   rewards accordingly. It supports bonus symbols that can impact the final reward.

2. Technical Description
   Stack: Java 21, Maven
   Dependencies: Jackson, Java Random, Custom Utilities
   Entry Point ScratchGameMain.java

3. Configuration: src/main/resources/config.json

4. To run the game, java -jar target/ScratchGame-1.0.jar --config config.json --betting-amount 100
