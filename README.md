# Adds a **_/flight_** command for survival players.

<a href="https://fabricmc.net/" target="_blank">
  <img src="https://docs.fabricmc.net/logo.png" width="100" height="100" alt="Compatible with Forge">
</a>
‎ ‎ 
<a href="https://neoforged.net/" target="_blank">
  <img src="https://neoforged.net/img/authors/neoforged.png" width="100" height="100" alt="Compatible with Forge">
</a>
‎ ‎ ‎
<a href="https://neoforged.net/" target="_blank">
  <img src="https://avatars.githubusercontent.com/u/1390178?s=280&v=4" width="100" height="100" alt="Compatible with Forge">
</a>

###

### Please report *any* bugs you may find <a href="https://github.com/Dillon8775/Survival-Fly/issues" target="_blank">here.</a>

---

## For servers (FABRIC ONLY): players do not need to install this mod for themselves on version 1.3 *(for mc26.1+)* or greater!
### For *mc1.21.11* and below, please scroll down to the bottom for information on how to setup your server.

---

# _/flight command!_
#### By default, _anyone_ can use this command.

### Command Usages:
#### /flight
#### /flight _**enable**_/_**disable**_ _*'target(s)'*_

#### Only operators can toggle the flight for other players.

#### (since: v1.0)

---

# _/flightstatus command!_
#### Shows whether the target player has flying abilities.

Command Usage: /flightstatus _*'target'*_
- Only operators can view the flight status of other players.

#### (since: v1.1)

---

# _/flightspeed command!_
### Changes the player's flight speed.
### You can also use _ALT + SCROLL_ to increase/decrease your flight speed, and press B to reset your flight speed (you must have the mod installed client-side to use this scrolling feature).

### Command Usages:
#### /flightspeed _set_ _**speed%**_ _*'target(s)'*_
#### /flightspeed _get_ _*'target'*_
#### /flightspeed _reset_ _*'target(s)'*_

### Only operators can change the flight speed of other players.

#### (since: v1.2)

---

# _Elytra Flight! (/elytraflight)_
#### Requires players to be wearing an elytra to use /flight.
### Elytra durability will wear as the player uses flight. The faster the player's flight speed, the more their elytra's durability wear down.

### OFF by default.

Command Usage: /elytraflight
- Only operators can execute this command.

#### (since: v1.3)

---

# _Flight Exhaustion! (/flightexhaustion)_
#### Makes players lose hunger when using flight.
#### The faster their flight speed, the more hunger they lose.

### OFF by default.

Command Usage: /flightexhaustion
- Only operators can execute this command.

#### (since: v1.3)

---

# _Friendly Flight! (/friendlyflight)_
#### Temporarily disables player flight abilities when going into combat (for 20 seconds).
#### This counts for both attackers and victims of attacks, and only disables if the attack is from a player.

### OFF by default.

Command Usage: /friendlyflight
- Only operators can execute this command.

#### (since: v1.3)

---

### /survivalfly command
Shows all of Survival Fly's commands to the player!
Anyone can run this command.

#### (since: v1.3)

---

## /permissions command
Sets the permission level in which players can use basic Survival Fly commands. These commands include:
- /flight
- /flightspeed
- /flightstatus

### Permission levels:
#### Regular (default option)
- Anyone can use commands.
#### Moderator 
- Players that bypass spawn protection can use commands.
#### Gamemaster
- Players that have higher authority, such as permission to use command blocks, change gamemode, change difficulty, etc. can use comands.
#### Admin
- Only players with operator can use commands.

Note: Permission level "Regular" means that players can only use Survival Fly commands on *themselves.* Only operators can use these commands on other players.

#### (since: v1.3)

---

# For older version players (*1.21.11* and below)

### Players *should* install this mod for themselves client-side, otherwise they will not be able to read messages when executing commands.

### Commands will still work, but messages will be unreadable. This issue has been *fixed* in version 1.3 for Minecraft 26.1 *and above.*

### To change permission level for commands, locate the "survivalfly-config.json" file, and set the permission level to one of the following strings:
- **"REGULAR"** *(now called "ANYONE" on mc26.1 and above)*
- **"MODERATOR"**
- **"GAMEMASTER"**
- **"ADMIN"**
- **"OWNER"** *(no longer exists in mc26.1 and above)*

---

Created by Dillon8775. All rights reserved unless explicitly stated.