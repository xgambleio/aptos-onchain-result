![Banner XGamble GitHub](https://github.com/user-attachments/assets/f6a420d7-bccb-463b-89f1-9f3726804f7f)

# XGamble: A Blockchain-Powered Gambling System

XGamble leverages the Aptos blockchain to ensure transparent and provably fair game outcomes. The system uses Aptos on-chain randomness to generate game results and manage in-game currency transactions, covering 100% of the game's operations. This approach guarantees true fairness for players by eliminating any central control or manipulation of game results, ensuring that every action within the game is securely recorded on the blockchain.

## Features

- Cross platform: Web, Telegram Mini App, Mobile Apps
- User Account connections: Telegram, Petra wallet, Google (Keyless login by Aptos Connect)
- 1000+ various games: slot, bet, crash, table, live, sport
- Payment: Aptos, BSC, fiat payment gateway

## Modules

- Account 0x7b5f16f0511939cf18670e8ca5003dbf974de66e6e2bf1fd649298df2d47a07a: Primary modules and functions used to generate game results based on Aptos' on-chain randomness function. Including:
   * bet_game: Used for card games, it generates a full deck of 52 cards.
   * slot_machine: Used for most slot games, it generates 5x3 reel results.
   * dragon: Used for a special slot game, it also generates 5x3 reel results, but this game logic is different.
   * slot_mini: Used for a few mini slot games, it generates 3x3 reel results.

- Account 0x5aab467624747605a9371def4d793a165f1c15f4025886b6f7f2d2995fb52ca5: A special module and set of functions used to generate rewards and track in-game currency transactions.
   * getRandom: Used for lucky spin, it generates the result for single spin.
   * getMultiRandom: Used for lucky spin, it generates multi results for multi spins at the same time.
   * bet_transfer: Tracks the amount of currency users place in bets.
   * reward_transfer: Tracks the rewards a user receives from special bonuses or game wins.
 
## Screenshots
![screenshot_TeleMiniApp](https://github.com/user-attachments/assets/5136d825-0e60-44c8-adac-e2a9d93d3cc1)







